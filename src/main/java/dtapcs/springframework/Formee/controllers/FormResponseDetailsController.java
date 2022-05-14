package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.entities.FormResponseDetails;
import dtapcs.springframework.Formee.entities.FormResponseDetailsId;
import dtapcs.springframework.Formee.services.inf.FormResponseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order-details")
public class FormResponseDetailsController {
    @Autowired
    private FormResponseDetailService formResponseDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<FormResponseDetails> getFormResponseDetail(@PathVariable FormResponseDetailsId id) {
        return new ResponseEntity<FormResponseDetails>(
                formResponseDetailService.getFormResponseDetail(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateFormResponseDetail(FormResponseDetails details) {
        return new ResponseEntity<>(formResponseDetailService.updateFormResponseDetail(details), HttpStatus.OK);
    }
}
