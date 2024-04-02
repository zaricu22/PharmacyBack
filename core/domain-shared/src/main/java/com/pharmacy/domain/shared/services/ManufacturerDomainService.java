package com.pharmacy.domain.shared.services;

import com.pharmacy.persistence.repositories.ManufacturerRepositoryImpl;
import com.pharmacy.domain.exceptions.ErrorMessages;
import com.pharmacy.domain.exceptions.WrongManufacturerException;
import com.pharmacy.domain.model.Manufacturer;
import com.pharmacy.domain.repositories.ManufacturerRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ManufacturerDomainService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerDomainService(ManufacturerRepositoryImpl manufacturerRepositoryImpl) {
        this.manufacturerRepository = manufacturerRepositoryImpl;
    }

    public Manufacturer createExistById(UUID manufacturerId){
        if(Objects.isNull(manufacturerId))
            throw new WrongManufacturerException(ErrorMessages.WRONG_MANUFACTURER_EXCEPTION);

        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(manufacturerId);
        if (!manufacturer.isPresent())
            throw new WrongManufacturerException(ErrorMessages.WRONG_MANUFACTURER_EXCEPTION);
        else {
            return manufacturer.get();
        }
    }
}
