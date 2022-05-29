package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.services.inf.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/excel")
//@CrossOrigin(origins = "#{'${formee.url}'}")
public class ExcelController extends BaseController {
    @Autowired
    ExcelService fileService;

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "formResponse.xlsx";
        InputStreamResource file = new InputStreamResource(fileService.load());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
