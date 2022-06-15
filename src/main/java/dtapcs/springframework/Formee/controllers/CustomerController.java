package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.mapper.CustomerMapper;
import dtapcs.springframework.Formee.dtos.model.CustomerDTO;
import dtapcs.springframework.Formee.dtos.model.DataResponse;
import dtapcs.springframework.Formee.entities.Customer;
import dtapcs.springframework.Formee.services.inf.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order/customer")
@CrossOrigin(origins = "#{'${formee.url}'}")
public class CustomerController extends BaseController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity createCustomer(@RequestBody Customer customer) {
        customerService.createCustomer(customer);
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity findAllByUserId(@PathVariable String userId) {
        List<Customer> result = customerService.findAllByUserId(userId);
        DataResponse response = DataResponse.ok()
                .withResult(result)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }
}
