package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.domain.FormResponseDetails;
import dtapcs.springframework.Formee.domain.FormResponseDetailsId;
import dtapcs.springframework.Formee.services.FormResponseDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/order-details/")
public class FormResponseDetailsController {
    private final FormResponseDetailService formResponseDetailService;

    public FormResponseDetailsController(FormResponseDetailService formResponseDetailService) {
        this.formResponseDetailService = formResponseDetailService;
    }
    @GetMapping
    public ResponseEntity<FormResponseDetails> getFormResponseDetail(FormResponseDetailsId id)
    {
        return new ResponseEntity<FormResponseDetails>(
                formResponseDetailService.getFormResponseDetail(id), HttpStatus.OK);
    }
    @PutMapping("update")
    public ResponseEntity<String> updateFormResponseDetail(FormResponseDetails details)
    {
        return new ResponseEntity<>(formResponseDetailService.updateFormResponseDetail(details),HttpStatus.OK);
    }
}
