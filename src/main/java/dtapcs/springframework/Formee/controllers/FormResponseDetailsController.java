package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.entities.FormResponse;
import dtapcs.springframework.Formee.entities.FormResponseDetails;
import dtapcs.springframework.Formee.entities.FormResponseDetailsId;
import dtapcs.springframework.Formee.services.inf.FormResponseDetailService;
import dtapcs.springframework.Formee.services.inf.FormResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/order-details")
public class FormResponseDetailsController {
    @Autowired
    private FormResponseDetailService formResponseDetailService;
    @Autowired
    private FormResponseService formResponseService;

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update")
    public ResponseEntity<String> updateFormResponseDetail(FormResponseDetails details) {
        return new ResponseEntity<>(formResponseDetailService.updateFormResponseDetail(details), HttpStatus.OK);
    }
}
