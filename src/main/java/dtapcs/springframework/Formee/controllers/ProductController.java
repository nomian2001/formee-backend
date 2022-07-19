package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.mapper.ProductMapper;
import dtapcs.springframework.Formee.dtos.mapper.ProductTypeMapper;
import dtapcs.springframework.Formee.dtos.model.DataResponse;
import dtapcs.springframework.Formee.dtos.model.ProductDTO;
import dtapcs.springframework.Formee.dtos.model.ProductTypeDTO;
import dtapcs.springframework.Formee.dtos.request.ProductSearchRequest;
import dtapcs.springframework.Formee.entities.Product;
import dtapcs.springframework.Formee.entities.ProductType;
import dtapcs.springframework.Formee.services.inf.ProductService;
import dtapcs.springframework.Formee.services.inf.ProductTypeService;
import dtapcs.springframework.Formee.services.inf.StorageService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("hasRole('USER')")
@CrossOrigin(origins = "#{'${formee.url}'}")
@RequestMapping(value = "/api/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController extends BaseController {
    @Autowired
    ProductService productService;

    @Autowired
    StorageService storageService;

    @Autowired
    ProductTypeService typeService;

    @PostMapping("/create")
    public ResponseEntity createProduct(@RequestBody Product product) {
        Product result = productService.createProduct(product);
        DataResponse response = DataResponse.ok()
                .withResult(result)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity findByUserId() {
        List<Product> result = productService.findAllByUser();
        DataResponse response = DataResponse.ok()
                .withResult(result)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/inventory")
    public ResponseEntity findAllInventoryByUserId() {
        List<Product> result = productService.findAllByUser();
        List<ProductDTO> dtos = result.stream()
                .map(ProductMapper.INSTANCE::productToProductDTO)
                .collect(Collectors.toList());
        DataResponse response = DataResponse.ok()
                .withResult(dtos)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/filter")
    public ResponseEntity findAllInventoryByUserId(@RequestBody ProductSearchRequest request) {
        Integer pageNumber = request.getPageNumber();
        Integer pageSize = request.getPageSize();
        List<Product> result = productService.filterProduct(request.getKeywords().toLowerCase(), request.getTypeId());
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

    @GetMapping("/type")
    public ResponseEntity findAllTypes() {
        List<ProductType> result = typeService.findAllTypes();
        List<ProductTypeDTO> dtos = result.stream()
                .map(ProductTypeMapper.INSTANCE::typeToTypeDTO)
                .collect(Collectors.toList());
        DataResponse response = DataResponse.ok()
                .withResult(dtos)
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/type/create")
    public ResponseEntity createOrUpdateType(@RequestBody ProductType type) {
        ProductType result = typeService.createOrUpdate(type);
        DataResponse response;
        if (result == null) {
            response = DataResponse.withCode(HttpStatus.ALREADY_REPORTED.value())
                    .withMessage(super.getMessage("message.product.type.duplicate.name"))
                    .build();
        }
        else {
            ProductTypeDTO dto = ProductTypeMapper.INSTANCE.typeToTypeDTO(result);
            response = DataResponse.ok()
                    .withResult(dto)
                    .withMessage(super.getMessage("message.common.success"))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/type/create-multiple")
    public ResponseEntity createMultipleTypes(@RequestBody List<String> types) {
        typeService.createMultipleTypes(types);
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/type/{typeId}")
    public ResponseEntity deleteTypeById(@PathVariable UUID typeId) {
        typeService.deleteById(typeId);
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .build();
        return ResponseEntity.ok(response);
    }
}
