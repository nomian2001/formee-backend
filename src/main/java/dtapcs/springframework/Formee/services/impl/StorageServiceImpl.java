package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.services.inf.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Service
public class StorageServiceImpl implements StorageService {
    @Override
    public void store(MultipartFile file,String imageName ,HttpServletRequest request) {
        if (!file.isEmpty()) {
            try {
                String realPathtoUploads = "/web/images/";
                if(! new File(realPathtoUploads).exists())
                {
                    new File(realPathtoUploads).mkdir();
                }
                String filePath = realPathtoUploads + imageName;
                File dest = new File(filePath);
                file.transferTo(dest);
                System.out.println("transfer complete "+filePath);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
