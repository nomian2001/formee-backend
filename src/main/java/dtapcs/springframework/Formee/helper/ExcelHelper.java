package dtapcs.springframework.Formee.helper;

import dtapcs.springframework.Formee.entities.FormResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "Id", "Customer Name", "Submit Status", "Order Status", "Payment Status", "Discount Percentage" };
    static String SHEET = "FormResponseSheet";
    public static ByteArrayInputStream FormResponseToExcel(List<FormResponse> formResponseList) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }
            int rowIdx = 1;
            for (FormResponse response : formResponseList) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue((RichTextString) response.getFormId());
                row.createCell(1).setCellValue(response.getCustomerName()); //customer name
                row.createCell(2).setCellValue(response.getSubmitStatus().toString()); //Submit Status
                row.createCell(3).setCellValue(response.getOrderStatus().toString()); //Order Status
                row.createCell(4).setCellValue(response.getPaymentStatus().toString()); //Payment Status
                row.createCell(5).setCellValue(response.getDiscountPercentage()); //Discount Percentage
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
