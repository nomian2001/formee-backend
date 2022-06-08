package dtapcs.springframework.Formee.services.inf;

import java.io.ByteArrayInputStream;

public interface ExcelService {
    public ByteArrayInputStream load(String formId);
}
