package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.model.DataResponse;
import dtapcs.springframework.Formee.entities.AddressCommons;
import dtapcs.springframework.Formee.entities.Customer;
import dtapcs.springframework.Formee.services.inf.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/address")
@CrossOrigin(origins = "#{'${formee.url}'}")
public class AddressController extends BaseController {
    @Autowired
    AddressService addressService;

    @GetMapping("")
    public ResponseEntity findAddressCommons(@RequestParam(name = "parentCode", defaultValue = "") String parentCode) {
        List<AddressCommons> result = addressService.findAddressCommons(parentCode);
        result.sort(Comparator.comparing(AddressCommons::getName_));
        DataResponse response = DataResponse.ok()
                .withResult(result)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }
}
