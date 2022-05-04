package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.api.model.FormResponseListDTO;
import dtapcs.springframework.Formee.services.FormResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api/orders/")
public class FormResponseController {
    private final FormResponseService formResponseService;

    public FormResponseController(FormResponseService formResponseService) {
        this.formResponseService = formResponseService;
    }
    @GetMapping
    public ResponseEntity<FormResponseListDTO> getFormResponseList(int filterValue, int pageNumber, int rowPerPage)
    {
        return new ResponseEntity<FormResponseListDTO>(
                new FormResponseListDTO(formResponseService.getFormResponseList(filterValue,pageNumber,rowPerPage)), HttpStatus.OK);
    }
    @PostMapping("export/{exportType}")
    public ResponseEntity<String> exportFile(List<UUID> responseId, @PathVariable int exportType)
    {
        return new ResponseEntity<>(formResponseService.exportFile(responseId,exportType),HttpStatus.OK);
    }

    @PostMapping("import")
    public ResponseEntity<String> importFile(String formResponse)
    {
        return new ResponseEntity<>(formResponseService.importFile(formResponse),HttpStatus.OK);
    }
}
