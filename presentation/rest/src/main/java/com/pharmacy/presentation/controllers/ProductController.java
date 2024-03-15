package com.pharmacy.presentation.controllers;

import com.pharmacy.application.contracts.services.ProductAppService;
import com.pharmacy.application.services.ProductAppServiceImpl;
import com.pharmacy.domain.model.Product;
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
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> allProducts = productAppService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(allProducts);
    }

    @GetMapping(
            value = "/products",
            params = {"page", "size"})
    public ResponseEntity<List<Product>> getProductsPage(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "sortDir", required = false) String sortDir) {
        List<Product> allProducts = productAppService.getProductsPage(page, size, sortBy, sortDir);
        return ResponseEntity.status(HttpStatus.OK).body(allProducts);
    }

    @GetMapping(
            value = "/products",
            params = {"orderDir"})
    public ResponseEntity<List<Product>> getFiveProductsOrderByPrice(
            @RequestParam("orderDir") String orderDir) {
        List<Product> fiveProductsOrderByPrice =
                productAppService.getTopOrLeastFiveProductByPrice(orderDir);
        return ResponseEntity.status(HttpStatus.OK).body(fiveProductsOrderByPrice);
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") UUID uuid) {
        Product productById = productAppService.getProductById(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(productById);
    }

    @PostMapping(value = "/products")
    public ResponseEntity<Product> insertProduct(@RequestBody @Validated Product product) {
        Product prod = productAppService.insertProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(prod);
    }

    @PutMapping(value = "/products/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable("id") UUID uuid, @RequestBody @Validated Product product) {
        Product prod = productAppService.updateProduct(uuid, product);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") UUID id) {
        productAppService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
