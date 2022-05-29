package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.entities.FormResponseDetails;
import dtapcs.springframework.Formee.services.inf.FormResponseDetailService;
import dtapcs.springframework.Formee.services.inf.FormResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order-details")
//@CrossOrigin(origins = "#{'${formee.url}'}")
public class FormResponseDetailsController extends BaseController {
    @Autowired
    private FormResponseDetailService formResponseDetailService;
    @Autowired
    private FormResponseService formResponseService;

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update")
    public ResponseEntity<String> updateFormResponseDetail(FormResponseDetails details) {
        //check user có giống user của ng tạo form k rồi send mail
        return new ResponseEntity<>(formResponseDetailService.updateFormResponseDetail(details), HttpStatus.OK);
    }
}
