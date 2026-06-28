package com.pharmacy.application.services;

import com.pharmacy.application.contracts.dtos.CreateProductDTO;
import com.pharmacy.application.contracts.dtos.ProductDTO;
import com.pharmacy.application.contracts.dtos.UpdateProductDTO;
import com.pharmacy.application.contracts.mappers.ProductMapper;
import com.pharmacy.application.contracts.services.ProductAppService;
import com.pharmacy.domain.exceptions.ErrorMessages;
import com.pharmacy.domain.exceptions.ProductNotFoundException;
import com.pharmacy.domain.exceptions.WrongManufacturerException;
import com.pharmacy.domain.model.Product;
import com.pharmacy.domain.repositories.ProductRepository;
import com.pharmacy.domain.shared.services.ProductDomainService;
import com.pharmacy.persistence.mappers.ProductMapperImpl;
import com.pharmacy.persistence.repositories.ProductRepositoryImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductAppServiceImpl implements ProductAppService {

    private final ProductRepository productRepository;

    private final ProductDomainService productDomainService;

    private final ProductMapper productMapper;

    public ProductAppServiceImpl(
            ProductRepositoryImpl productRepositoryImpl,
            ProductDomainService productDomainService,
            ProductMapperImpl productMapperImpl)
    {
        this.productRepository = productRepositoryImpl;
        this.productDomainService = productDomainService;
        this.productMapper = productMapperImpl;
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productMapper.toDto(productList);
    }

    public List<ProductDTO> getProductsPage(
            int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort.Direction dir;
        Pageable pageable;
        if (Objects.nonNull(sortBy) && !sortBy.isEmpty()) {
            if (Objects.nonNull(sortDir) && !sortDir.isEmpty()) {
                dir = sortDir.toLowerCase().contains("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by(dir, sortBy));
            } else {
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
            }
        } else {
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        List<Product> productList = productRepository.findAll(pageable).getContent();
        return productMapper.toDto(productList);
    }

    public ProductDTO getProductById(UUID uuid) {

        Product product = productRepository
                .findById(uuid)
                .orElseThrow(() -> new ProductNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND_EXCEPTION));
        return productMapper.toDto(product);
    }

    public List<ProductDTO> getTopOrLeastFiveProductByPrice(String orderDir) {
        if (Objects.nonNull(orderDir) && orderDir.toLowerCase().contains("top-five")) {
            List<Product> productList = productRepository.findFirstFiveOrderByPriceDesc();
            return productMapper.toDto(productList);
        }
        else if (Objects.nonNull(orderDir) && orderDir.toLowerCase().contains("least-five")) {
            List<Product> productList = productRepository.findFirstFiveOrderByPriceAsc();
            return productMapper.toDto(productList);
        }
        else return new ArrayList<>();
    }

    public void deleteProduct(UUID id) {
        boolean prodExists = productRepository.existsById(id);
        if (prodExists) productRepository.deleteById(id);
        else throw new ProductNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND_EXCEPTION);
    }

    public ProductDTO insertProduct(CreateProductDTO productDTO) {
        if(Objects.isNull(productDTO))
            throw new ProductNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND_EXCEPTION);
        if(Objects.isNull(productDTO.manufacturer().id()))
            throw new WrongManufacturerException(ErrorMessages.WRONG_MANUFACTURER_EXCEPTION);

        Product unsavedProduct = productDomainService.createByNameAndExpiryDate(
                productDTO.name(),
                productDTO.manufacturer().id(),
                productDTO.price(),
                productDTO.expiryDate()
        );

        Product savedProduct = productRepository.save(unsavedProduct);
        return productMapper.toDto(savedProduct);
    }

    public ProductDTO updateProduct(UUID uuid, UpdateProductDTO productDTO) {
        if(Objects.isNull(productDTO))
            throw new ProductNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND_EXCEPTION);
        if(Objects.isNull(productDTO.manufacturer().id()))
            throw new WrongManufacturerException(ErrorMessages.WRONG_MANUFACTURER_EXCEPTION);

        Product editedProduct = productDomainService.changeExist(
                uuid,
                productDTO.name(),
                productDTO.manufacturer().id(),
                productDTO.price(),
                productDTO.expiryDate()
        );
        Product savedProduct = productRepository.save(editedProduct);
        return productMapper.toDto(savedProduct);
    }

}
