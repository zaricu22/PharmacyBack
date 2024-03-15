package com.pharmacy.presentation.controllers;

import com.pharmacy.application.contracts.dtos.ProductDTO;
import com.pharmacy.application.contracts.services.ProductAppService;
import com.pharmacy.application.services.ProductAppServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class ProductController {

    @Autowired
    private final ProductAppService productAppService;

    public ProductController(ProductAppServiceImpl productServiceImpl) {
        this.productAppService = productServiceImpl;
    }

    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> allProducts = productAppService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(allProducts);
    }

    @GetMapping(
            value = "/products",
            params = {"page", "size"})
    public ResponseEntity<List<ProductDTO>> getProductsPage(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "sortDir", required = false) String sortDir) {
        List<ProductDTO> allProducts = productAppService.getProductsPage(page, size, sortBy, sortDir);
        return ResponseEntity.status(HttpStatus.OK).body(allProducts);
    }

    @GetMapping(
            value = "/products",
            params = {"orderDir"})
    public ResponseEntity<List<ProductDTO>> getFiveProductsOrderByPrice(
            @RequestParam("orderDir") String orderDir) {
        List<ProductDTO> fiveProductsOrderByPrice =
                productAppService.getTopOrLeastFiveProductByPrice(orderDir);
        return ResponseEntity.status(HttpStatus.OK).body(fiveProductsOrderByPrice);
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") UUID uuid) {
        ProductDTO productById = productAppService.getProductById(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(productById);
    }

    @PostMapping(value = "/products")
    public ResponseEntity<ProductDTO> insertProduct(@RequestBody @Validated ProductDTO productDTO) {
        ProductDTO prodDTO = productAppService.insertProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(prodDTO);
    }

    @PutMapping(value = "/products/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable("id") UUID uuid, @RequestBody @Validated ProductDTO productDTO) {
        ProductDTO prod = productAppService.updateProduct(uuid, productDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") UUID id) {
        productAppService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
