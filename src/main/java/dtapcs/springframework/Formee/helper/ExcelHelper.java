package dtapcs.springframework.Formee.helper;

import dtapcs.springframework.Formee.entities.FormOrder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"ID", "Tên đơn hàng", "Số điện thoại", "Tên người mua", "Ngày tạo",
            "Trạng thái", "Tổng đơn hàng", "Giảm giá (%)", "Tổng tiền"};
    static String SHEET = "Đơn hàng";

    public static ByteArrayInputStream FormResponseToExcel(List<FormOrder> formResponseList, String formName, String startDate, String endDate) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            // title
            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setWrapText(true);

            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, HEADERs.length - 1));
            Row titleRow = sheet.createRow(0);
            Cell headerCell = titleRow.createCell(0, CellType.STRING);
            headerCell.setCellStyle(titleStyle);
            headerCell.setCellValue(String.join(" ", "Danh sách đơn hàng", formName));

            // reporting period
            String reportingPeriod = "";
            if (StringUtils.hasText(startDate) && StringUtils.hasText(endDate)) {
                reportingPeriod = "Từ ngày " + startDate.substring(0, 10) + " đến ngày " + endDate.substring(0, 10);
            } else if (!StringUtils.hasText(startDate) && StringUtils.hasText(endDate)) {
                reportingPeriod = "" + " Đến ngày " + endDate.substring(0, 10);
            } else if (StringUtils.hasText(startDate) && !StringUtils.hasText(endDate)) {
                reportingPeriod = "Từ ngày " + startDate.substring(0, 10) + "";
            }

            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, HEADERs.length - 1));
            Row periodRow = sheet.createRow(1);
            Cell periodCell = periodRow.createCell(0, CellType.STRING);
            periodCell.setCellStyle(titleStyle);
            periodCell.setCellValue(reportingPeriod);

            // blank
            sheet.createRow(2);

            // Header
            Row headerRow = sheet.createRow(3);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col, CellType.STRING);
                cell.setCellValue(HEADERs[col]);
            }
            int rowIdx = 4;
            for (FormOrder order : formResponseList) {
                Row row = sheet.createRow(rowIdx++);
                JSONArray response = new JSONArray(order.getResponse());
                row.createCell(0, CellType.STRING).setCellValue(order.getUuid().toString());
                row.createCell(1, CellType.STRING).setCellValue(order.getOrderName()); // tên đơn hàng
                row.createCell(2, CellType.STRING).setCellValue(response.getString(0)); // số điện thoại
                row.createCell(3, CellType.STRING).setCellValue(response.getString(1)); // tên người mua
                row.createCell(4, CellType.STRING).setCellValue(order.getCreatedDate().toString()); // ngày tạo
                row.createCell(5, CellType.STRING).setCellValue(order.getStatus().getName()); // trạng thái

                int total = 0;
                JSONArray products = new JSONArray(response.get(4).toString()); // actual response
                for (int i = 0; i < products.length(); ++i) {
                    JSONObject obj = products.getJSONObject(i);
                    total += obj.getInt("productPrice") * obj.getInt("quantity");
                }

                row.createCell(6, CellType.STRING).setCellValue(total); // tổng đơn hàng
                row.createCell(7, CellType.STRING).setCellValue(order.getDiscount()); // giảm giá
                row.createCell(8, CellType.STRING).setCellValue(total * (100 - order.getDiscount()) / 100); // tổng tiền
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to Excel file: " + e.getMessage());
        }
    }
}
