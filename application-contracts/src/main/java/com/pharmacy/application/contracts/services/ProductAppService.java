package com.pharmacy.application.contracts.services;

import com.pharmacy.domain.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductAppService {
    public List<Product> getAllProducts();

    public List<Product> getProductsPage(int pageNumber, int pageSize, String sortBy, String sortDir);

    public Product getProductById(UUID uuid);

    public List<Product> getTopOrLeastFiveProductByPrice(String orderDir);

    public void deleteProduct(UUID id);

    public Product insertProduct(Product product);

    public Product updateProduct(UUID uuid, Product product);
}
