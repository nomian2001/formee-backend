package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.model.DataResponse;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.entities.Product;
import dtapcs.springframework.Formee.services.inf.ProductService;
import dtapcs.springframework.Formee.services.inf.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
//@PreAuthorize("hasRole('USER')")
@CrossOrigin(origins = "#{'${formee.url}'}")
@RequestMapping(value = "/api/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController extends BaseController {
    @Autowired
    ProductService productService;
    @Autowired
    StorageService storageService;
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
    @PostMapping("/{productId}")
    public ResponseEntity uploadImage(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes , @PathVariable UUID productId, HttpServletRequest request){
        UUID imageUUID = UUID.randomUUID();
        //random name
        System.out.println("tenfile " + file.getOriginalFilename());
        String imageName = imageUUID.toString() + file.getOriginalFilename().substring(file.getOriginalFilename().length()-4);
        productService.setImageName(imageName,productId);
        storageService.store(file, imageName,request);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        DataResponse response = DataResponse.ok().build();
        return ResponseEntity.ok(response);
    }
}
