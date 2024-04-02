package com.pharmacy.application.contracts.services;

import com.pharmacy.application.contracts.dtos.CreateProductDTO;
import com.pharmacy.application.contracts.dtos.ProductDTO;
import com.pharmacy.application.contracts.dtos.UpdateProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductAppService {
    public List<ProductDTO> getAllProducts();

    public List<ProductDTO> getProductsPage(int pageNumber, int pageSize, String sortBy, String sortDir);

    public ProductDTO getProductById(UUID uuid);

    public List<ProductDTO> getTopOrLeastFiveProductByPrice(String orderDir);

    public void deleteProduct(UUID id);

    public ProductDTO insertProduct(CreateProductDTO product);

    public ProductDTO updateProduct(UUID uuid, UpdateProductDTO product);
}
