package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.model.DataResponse;
import dtapcs.springframework.Formee.dtos.request.CustomerSearchRequest;
import dtapcs.springframework.Formee.entities.Customer;
import dtapcs.springframework.Formee.services.inf.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PostMapping("/update")
    public ResponseEntity updateCustomer(@RequestBody Customer customer) {
        customerService.updateCustomer(customer);
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

    @PostMapping("/filter")
    public ResponseEntity filterCustomers(@RequestBody CustomerSearchRequest request) {
        Integer pageNumber = request.getPageNumber();
        Integer pageSize = request.getPageSize();
        List<Customer> result = customerService.filterCustomers(request.getKeywords().toLowerCase());
        result = result.subList(pageNumber * pageSize, Math.min((pageNumber + 1) * pageSize, result.size()));
        Page<Customer> page = new PageImpl<>(result, PageRequest.of(pageNumber, pageSize), result.size());
        DataResponse response = DataResponse.ok()
                .withResult(page)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity deleteById(@PathVariable UUID customerId) {
        customerService.deleteById(customerId);
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }
}
