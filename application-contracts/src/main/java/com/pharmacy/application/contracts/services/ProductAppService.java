package com.pharmacy.application.contracts.services;

import com.pharmacy.application.contracts.dtos.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductAppService {
    public List<ProductDTO> getAllProducts();

    public List<ProductDTO> getProductsPage(int pageNumber, int pageSize, String sortBy, String sortDir);

    public ProductDTO getProductById(UUID uuid);

    public List<ProductDTO> getTopOrLeastFiveProductByPrice(String orderDir);

    public void deleteProduct(UUID id);

    public ProductDTO insertProduct(ProductDTO product);

    public ProductDTO updateProduct(UUID uuid, ProductDTO product);
}
