package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.api.model.FormResponseListDTO;
import dtapcs.springframework.Formee.enums.OrderStatus;
import dtapcs.springframework.Formee.services.FormResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api/orders/")
public class FormResponseController {
    private final FormResponseService formResponseService;

    public FormResponseController(FormResponseService formResponseService) {
        this.formResponseService = formResponseService;
    }
    @GetMapping("{formID}")
    public ResponseEntity<FormResponseListDTO> getFormResponseList(@PathVariable UUID formID, @RequestParam List<OrderStatus> orderStatus, @RequestParam List<String> customerName, @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate, @RequestParam int pageNumber, @RequestParam int rowPerPage)
    {
        return new ResponseEntity<FormResponseListDTO>(
                new FormResponseListDTO(formResponseService.getFormResponseList(formID ,orderStatus,customerName,startDate,endDate,pageNumber,rowPerPage)), HttpStatus.OK);
    }
    @PostMapping("export")
    public ResponseEntity<String> exportFile(List<UUID> responseId, int exportType)
    {
        return new ResponseEntity<>(formResponseService.exportFile(responseId,exportType),HttpStatus.OK);
    }

    @PostMapping("import")
    public ResponseEntity<String> importFile(String formResponse)
    {
        return new ResponseEntity<>(formResponseService.importFile(formResponse),HttpStatus.OK);
    }
}
