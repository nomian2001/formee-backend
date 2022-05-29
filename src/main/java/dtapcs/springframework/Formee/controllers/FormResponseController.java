package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.model.FormResponseListDTO;
import dtapcs.springframework.Formee.enums.OrderStatus;
import dtapcs.springframework.Formee.services.inf.FormResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@PreAuthorize("hasRole('USER')")
//@CrossOrigin(origins = "#{'${formee.url}'}")
@RequestMapping("/api/orders")
public class FormResponseController extends BaseController {
    @Autowired
    private FormResponseService formResponseService;

    @GetMapping("/{formID}")
    public ResponseEntity<FormResponseListDTO> getFormResponseList(@PathVariable UUID formID,
                                                                   @RequestParam List<OrderStatus> orderStatus,
                                                                   @RequestParam List<String> customerName,
                                                                   @RequestParam LocalDateTime startDate,
                                                                   @RequestParam LocalDateTime endDate,
                                                                   @RequestParam int pageNumber,
                                                                   @RequestParam int rowPerPage) {
        return new ResponseEntity<FormResponseListDTO>(
                new FormResponseListDTO(formResponseService.getFormResponseList(formID, orderStatus, customerName,
                        startDate, endDate, pageNumber, rowPerPage)), HttpStatus.OK);
    }

    @PostMapping("/export")
    public ResponseEntity<String> exportFile(List<UUID> responseId, int exportType) {
        return new ResponseEntity<>(formResponseService.exportFile(responseId, exportType), HttpStatus.OK);
    }

    @PostMapping("/import")
    public ResponseEntity<String> importFile(String formResponse) {
        return new ResponseEntity<>(formResponseService.importFile(formResponse), HttpStatus.OK);
    }
}
