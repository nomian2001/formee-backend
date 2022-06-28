package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.model.DataResponse;
import dtapcs.springframework.Formee.dtos.request.ProductSearchRequest;
import dtapcs.springframework.Formee.entities.Product;
import dtapcs.springframework.Formee.services.inf.ProductService;
import dtapcs.springframework.Formee.services.inf.StorageService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
@PreAuthorize("hasRole('USER')")
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

    @GetMapping("/{userId}")
    public ResponseEntity findByUserId(@PathVariable String userId) {
        List<Product> result = productService.findAllByUserId(userId);
        DataResponse response = DataResponse.ok()
                .withResult(result)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/inventory/{userId}")
    public ResponseEntity findAllInventoryByUserId(@PathVariable String userId) {
        List<Product> result = productService.findAllByUserId(userId);
        DataResponse response = DataResponse.ok()
                .withResult(result)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/filter")
    public ResponseEntity findAllInventoryByUserId(@RequestBody ProductSearchRequest request) {
        Integer pageNumber = request.getPageNumber();
        Integer pageSize = request.getPageSize();
        List<Product> result = productService.filterProduct(request.getKeywords(), request.getTypes());
        result = result.subList(pageNumber * pageSize, Math.min((pageNumber + 1) * pageSize, result.size()));
        Page<Product> page = new PageImpl<>(result, PageRequest.of(pageNumber, pageSize), result.size());
        DataResponse response = DataResponse.ok()
                .withResult(page)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity deleteById(@PathVariable UUID productId) {
        productService.deleteById(productId);
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{productId}")
    public ResponseEntity uploadImage(@RequestParam("files[]") MultipartFile[] files,
                                      RedirectAttributes redirectAttributes,
                                      @PathVariable UUID productId,
                                      HttpServletRequest request) {
        JSONArray imageList = new JSONArray();
        for (MultipartFile file : files) {
            UUID imageUUID = UUID.randomUUID();
            // random name
            System.out.println("tenfile " + file.getOriginalFilename());
            String imageName = imageUUID + file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4);
            imageList.put(imageName);
            storageService.store(file, imageName, request);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
        }
        productService.setImageName(imageList.getString(0), imageList.toString(), productId);
        DataResponse response = DataResponse.ok().withResult(imageList.toString()).build();
        return ResponseEntity.ok(response);
    }


}
