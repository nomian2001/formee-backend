package dtapcs.springframework.Formee.helper;

import dtapcs.springframework.Formee.entities.FormOrder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"ID", "Tên đơn hàng", "Số điện thoại", "Tên người mua", "Ngày tạo",
                                "Trạng thái", "Tổng đơn hàng", "Giảm giá (%)", "Tổng tiền"};
    static String SHEET = "Đơn hàng";

    public static ByteArrayInputStream FormResponseToExcel(List<FormOrder> formResponseList) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }
            int rowIdx = 1;
            for (FormOrder order : formResponseList) {
                Row row = sheet.createRow(rowIdx++);
                JSONArray response = new JSONArray(order.getResponse());
                row.createCell(0).setCellValue(order.getUuid().toString());
                row.createCell(1).setCellValue(order.getOrderName()); // tên đơn hàng
                row.createCell(2).setCellValue(response.getString(0)); // số điện thoại
                row.createCell(3).setCellValue(response.getString(1)); // tên người mua
                row.createCell(4).setCellValue(order.getCreatedDate().toString()); // ngày tạo
                row.createCell(5).setCellValue(order.getStatus().getName()); // trạng thái

                int total = 0;
                JSONArray products = new JSONArray(response.get(4).toString()); // actual response
                for (int i = 0; i < products.length(); ++i) {
                    JSONObject obj = products.getJSONObject(i);
                    total += obj.getInt("productPrice") * obj.getInt("quantity");
                }

                row.createCell(6).setCellValue(total); // tổng đơn hàng
                row.createCell(7).setCellValue(order.getDiscount()); // giảm giá
                row.createCell(8).setCellValue(total * (100 - order.getDiscount()) / 100); // tổng tiền
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
