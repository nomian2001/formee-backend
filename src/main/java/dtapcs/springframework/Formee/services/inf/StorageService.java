package dtapcs.springframework.Formee.services.inf;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface StorageService {
    void store(MultipartFile file, String imageName,HttpServletRequest request);
}
