package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.model.DataResponse;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.entities.Product;
import dtapcs.springframework.Formee.services.inf.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('USER')")
@CrossOrigin(origins = "#{'${formee.url}'}")
@RequestMapping(value = "/api/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController extends BaseController {
    @Autowired
    ProductService productService;

    @PostMapping("/create")
    public ResponseEntity createProduct(@RequestBody Product product) {
        Product result = productService.createProduct(product);
        DataResponse response = DataResponse.ok()
                .withResult(result)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{formId}")
    public ResponseEntity findAllByFormId(@PathVariable String formId) {
        List<Product> result = productService.findAllByFormId(formId);
        DataResponse response = DataResponse.ok()
                .withResult(result)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

}
