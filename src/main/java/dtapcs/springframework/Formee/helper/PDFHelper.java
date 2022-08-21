package dtapcs.springframework.Formee.helper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import dtapcs.springframework.Formee.entities.FormOrder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PDFHelper {

    public static byte[] exportInvoice(FormOrder order, String encodedId) throws DocumentException, IOException, URISyntaxException {
        String trackingLink = "https://formee.website/tracking/" + encodedId;
        Document pdf = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(pdf, out);
        pdf.open();

        Font fontRegular = CommonHelper.getFont("REGULAR");
        fontRegular.setSize(13);
        Font fontMedium = CommonHelper.getFont("MEDIUM");
        fontMedium.setSize(13);
        Font fontBold = CommonHelper.getFont("BOLD");
        fontBold.setSize(13);
        Font fontTitle = CommonHelper.getFont("BOLD");
        fontTitle.setSize(16);

        // logo
//        PdfPTable headerTable = new PdfPTable(1);
//        headerTable.setSpacingAfter(10f);
//        headerTable.setWidthPercentage(100f);
//        headerTable.setWidths(new int[]{1});
//        PdfPCell headerCell = new PdfPCell();
//        headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        headerCell.setFixedHeight(30);

//        Path logoPath = Paths.get(ClassLoader.getSystemResource("\\formee-logo.png").toURI());
//        Path logoPath = Paths.get(new ClassPathResource("\\formee-logo.png").getURI());
//        Image logo = Image.getInstance(logoPath.toAbsolutePath().toString());
//        logo.scaleAbsolute(138,30);
//        headerCell.setImage(logo);
//        headerCell.setBorderWidth(Rectangle.NO_BORDER);
//        headerTable.addCell(headerCell);
//        pdf.add(headerTable);

        Paragraph title = new Paragraph("ĐƠN ĐẶT HÀNG - " + order.getOrderName(), fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(10f);
        pdf.add(title);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        String formattedDate = order.getCreatedDate().format(formatter);
        Paragraph createdDate = new Paragraph("Thời gian đặt hàng: " + formattedDate, fontRegular);
        createdDate.setAlignment(Element.ALIGN_CENTER);
        pdf.add(createdDate);

        pdf.add(new Paragraph(" "));

        JSONArray response = new JSONArray(order.getResponse());
        Paragraph name = new Paragraph();
        name.add(new Chunk("Tên khách hàng: ", fontBold));
        name.add(new Chunk(response.getString(1), fontRegular));
        name.setAlignment(Element.ALIGN_LEFT);
        name.setSpacingAfter(10f);
        pdf.add(name);

        Paragraph phone = new Paragraph();
        phone.add(new Chunk("Số điện thoại: ", fontBold));
        phone.add(new Chunk(response.getString(0), fontRegular));
        phone.setAlignment(Element.ALIGN_LEFT);
        phone.setSpacingAfter(10f);
        pdf.add(phone);

        Paragraph address = new Paragraph();
        address.add(new Chunk("Địa chỉ giao hàng: ", fontBold));
        JSONArray addressArray = new JSONArray(response.get(2).toString());
        List<String> addressList = new ArrayList<>();
        addressList.add(addressArray.getString(0));
        for (int i = 1; i < addressArray.length(); ++i) {
            addressList.add(new JSONObject(addressArray.get(i).toString()).getString("name_"));
        }
        address.add(new Chunk(String.join(", ", addressList), fontRegular));
        address.setAlignment(Element.ALIGN_LEFT);
        address.setSpacingAfter(10f);
        pdf.add(address);

        Paragraph note = new Paragraph();
        note.add(new Chunk("Ghi chú: ", fontBold));
        note.add(new Chunk(response.get(3).toString(), fontRegular));
        note.setAlignment(Element.ALIGN_LEFT);
        note.setSpacingAfter(10f);
        pdf.add(note);

        pdf.add(new Paragraph(" "));

        PdfPTable orderTable = new PdfPTable(5);
        orderTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        orderTable.setWidthPercentage(100f);
        orderTable.setSpacingAfter(10f);
        orderTable.setWidths(new int[]{1, 5, 2, 2, 2});

        Stream.of("STT", "Tên sản phẩm", "Giá", "Số lượng", "Tổng cộng")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(new BaseColor(238, 239, 255));
                    header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setPaddingTop(4);
                    header.setPaddingBottom(8);
                    header.setPhrase(new Phrase(columnTitle, fontBold));
                    orderTable.addCell(header);
                });

        int total = 0;
        JSONArray products = new JSONArray(response.get(4).toString()); // actual response
        for (int i = 0; i < products.length(); ++i) {
            JSONObject obj = products.getJSONObject(i);
            String productName = obj.getString("name");
            int productPrice = obj.getInt("productPrice");
            int quantity = obj.getInt("quantity");
            total += productPrice * quantity;

            PdfPCell cell = new PdfPCell();
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPaddingTop(4);
            cell.setPaddingBottom(8);
            cell.setLeading(2, 1);

            cell.setPhrase(new Phrase(String.valueOf(i + 1), fontRegular));
            orderTable.addCell(cell);
            cell.setPhrase(new Phrase(productName, fontRegular));
            orderTable.addCell(cell);
            cell.setPhrase(new Phrase(String.format("%,d", productPrice), fontRegular));
            orderTable.addCell(cell);
            cell.setPhrase(new Phrase(String.valueOf(quantity), fontRegular));
            orderTable.addCell(cell);
            cell.setPhrase(new Phrase(String.format("%,d", productPrice * quantity), fontRegular));
            orderTable.addCell(cell);

            if (i == products.length() - 1) {
                cell.setPhrase(new Phrase("Tổng đơn hàng", fontBold));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(10);
                cell.setColspan(4);
                cell.setBorderWidth(Rectangle.NO_BORDER);
                orderTable.addCell(cell);

                cell.setPhrase(new Phrase(String.format("%,d", total), fontRegular));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                orderTable.addCell(cell);

                cell.setPhrase(new Phrase("Giảm giá", fontBold));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(10);
                cell.setColspan(4);
                orderTable.addCell(cell);

                cell.setPhrase(new Phrase(order.getDiscount() + "%", fontRegular));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                orderTable.addCell(cell);

                cell.setPhrase(new Phrase("Tổng tiền", fontBold));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(10);
                cell.setColspan(4);
                orderTable.addCell(cell);

                cell.setPhrase(new Phrase(String.format("%,d", total * (100 - order.getDiscount()) / 100), fontBold));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                orderTable.addCell(cell);
            }
        }
        pdf.add(orderTable);

        LineSeparator line = new LineSeparator();
        pdf.add(new Chunk(line));

        PdfPTable qrTable = new PdfPTable(1);
        qrTable.setWidthPercentage(100f);
        qrTable.setWidths(new int[]{1});
        PdfPCell qrCell = new PdfPCell();
        qrCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        qrCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        qrCell.setBorderWidth(Rectangle.NO_BORDER);
        qrCell.setFixedHeight(120);

        BarcodeQRCode qrCode = new BarcodeQRCode(trackingLink, 2, 2, null);
        Image qrCodeImage = qrCode.getImage();
        qrCodeImage.scaleAbsolute(50,50);
        qrCell.setImage(qrCodeImage);
        qrTable.addCell(qrCell);
        pdf.add(qrTable);

        Paragraph link = new Paragraph();
        link.add(new Phrase("Theo dõi đơn hàng tại: \n", fontRegular));
        link.setAlignment(Element.ALIGN_CENTER);
        link.setSpacingAfter(10f);
        Anchor anchor = new Anchor(trackingLink);
        anchor.setReference(trackingLink);
        link.add(anchor);
        pdf.add(link);

        pdf.close();
        return out.toByteArray();
    }
}
