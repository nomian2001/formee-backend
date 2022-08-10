package dtapcs.springframework.Formee.helper;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import dtapcs.springframework.Formee.dtos.model.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

public class CommonHelper {
    private static final String FONTS_INTER_REGULAR = "/fonts/Inter-Regular.ttf";
    private static final String FONTS_INTER_MEDIUM = "/fonts/Inter-Medium.ttf";
    private static final String FONTS_INTER_BOLD = "/fonts/Inter-Bold.ttf";

    public static UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal.equals("anonymousUser")) {
            return new UserDetails("anonymousUser");
        }
        return (UserDetails) principal;
    }

    public static Font getFont(String type) throws DocumentException, IOException {
        switch (type) {
            case "REGULAR":
                return new Font(BaseFont.createFont(FONTS_INTER_REGULAR, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
            case "MEDIUM":
                return new Font(BaseFont.createFont(FONTS_INTER_MEDIUM, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
            case "BOLD":
                return new Font(BaseFont.createFont(FONTS_INTER_BOLD, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
            default:
                return null;
        }
    }
}
