package com.example.pharmacyback.controller;

import com.example.pharmacyback.model.Product;
import com.example.pharmacyback.service.ProductService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping(value = "/products")
  public ResponseEntity<List<Product>> getAllProducts() {
    List<Product> allProducts = productService.getAllProducts();
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
    List<Product> allProducts = productService.getProductsPage(page, size, sortBy, sortDir);
    return ResponseEntity.status(HttpStatus.OK).body(allProducts);
  }

  @GetMapping(
      value = "/products",
      params = {"orderDir"})
  public ResponseEntity<List<Product>> getFiveProductsOrderByPrice(
      @RequestParam("orderDir") String orderDir) {
    List<Product> fiveProductsOrderByPrice =
        productService.getTopOrLeastFiveProductByPrice(orderDir);
    return ResponseEntity.status(HttpStatus.OK).body(fiveProductsOrderByPrice);
  }

  @GetMapping(value = "/products/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable("id") UUID uuid) {
    Product productById = productService.getProductById(uuid);
    return ResponseEntity.status(HttpStatus.OK).body(productById);
  }

  @PostMapping(value = "/products")
  public ResponseEntity<Product> insertProduct(@RequestBody @Validated Product product) {
    Product prod = productService.insertProduct(product);
    return ResponseEntity.status(HttpStatus.CREATED).body(prod);
  }

  @PutMapping(value = "/products/{id}")
  public ResponseEntity<?> updateProduct(
      @PathVariable("id") UUID uuid, @RequestBody @Validated Product product) {
    product.setId(uuid);
    Product prod = productService.updateProduct(product);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
  }

  @DeleteMapping(value = "/products/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable("id") UUID id) {
    productService.deleteProduct(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
  }
}
