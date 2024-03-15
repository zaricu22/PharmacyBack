package com.pharmacy.application.services;

import com.pharmacy.application.contracts.dtos.ProductDTO;
import com.pharmacy.application.contracts.mappers.ProductMapper;
import com.pharmacy.application.contracts.services.ProductAppService;
import com.pharmacy.application.exceptions.*;
import com.pharmacy.domain.model.Product;
import com.pharmacy.domain.repositories.ManufacturerRepository;
import com.pharmacy.domain.repositories.ProductRepository;
import com.pharmacy.infrastructure.mappers.ProductMapperImpl;
import com.pharmacy.infrastructure.repositories.ManufacturerRepositoryImpl;
import com.pharmacy.infrastructure.repositories.ProductRepositoryImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductAppServiceImpl implements ProductAppService {

    private final ProductRepository productRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final ProductMapper productMapper;

    public ProductAppServiceImpl(
            ProductRepositoryImpl productRepositoryImpl,
            ManufacturerRepositoryImpl manufacturerRepositoryImpl,
            ProductMapperImpl productMapperImpl)
    {
        this.productRepository = productRepositoryImpl;
        this.manufacturerRepository = manufacturerRepositoryImpl;
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

    public ProductDTO insertProduct(ProductDTO productDTO) {
        String name = productDTO.name();
        Date expiryDate = productDTO.expiryDate();
        Date now = new Date();
        int countExistingProduct = productRepository.countByNameAndExpiryDate(name, expiryDate);
        boolean manufacturerExists =
                Objects.nonNull(productDTO.manufacturer().id())
                        && manufacturerRepository.existsById(productDTO.manufacturer().id());

        if (countExistingProduct > 0)
          throw new ProductExistsException(ErrorMessages.PRODUCT_EXISTS_EXCEPTION);
        else if (!manufacturerExists)
          throw new WrongManufacturerException(ErrorMessages.WRONG_MANUFACTURER_EXCEPTION);
        else if (expiryDate.before(now))
          throw new DateExpiredException(ErrorMessages.DATE_EXPIRED_EXCEPTION);
        else {
            Product unsavedProduct = productMapper.toEntity(productDTO);
            Product savedProduct = productRepository.save(unsavedProduct);
            return productMapper.toDto(savedProduct);
        }

    }

    public ProductDTO updateProduct(UUID uuid, ProductDTO productDTO) {
        Date expiryDate = productDTO.expiryDate();
        Date now = new Date();
        boolean productExists = productRepository.existsById(uuid);
        boolean manufacturerExists =
                manufacturerRepository.existsById(productDTO.manufacturer().id());

        if (!manufacturerExists)
          throw new WrongManufacturerException(ErrorMessages.WRONG_MANUFACTURER_EXCEPTION);
        else if (expiryDate.before(now))
          throw new DateExpiredException(ErrorMessages.DATE_EXPIRED_EXCEPTION);
        else if (!productExists)
          throw new ProductNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND_EXCEPTION);
        else {
            Product unsavedProduct = productMapper.toEntity(productDTO);
            Product savedProduct = productRepository.save(unsavedProduct);
            return productMapper.toDto(savedProduct);
        }
    }

}
