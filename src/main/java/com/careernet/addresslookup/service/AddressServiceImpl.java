package com.careernet.addresslookup.service;

import com.careernet.addresslookup.entity.Address;
import com.careernet.addresslookup.repository.AddressRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final BlackListService blackListService;

    public AddressServiceImpl(BlackListService blackListService, AddressRepository addressRepository) {
        this.blackListService = blackListService;
        this.addressRepository = addressRepository;
    }

    @Override
    public Address saveAddress(Address address) {
        log.info("AddressServiceImpl message = save address details");
        return addressRepository.save(address);
    }

    @Override
    public List<Address> findAddressesByPostcode(String postcode, boolean filterBlacklisted) {
        log.info("AddressServiceImpl message = find address data by post code");
        if (Objects.isNull(postcode) || postcode.isBlank()) {
            return List.of();
        }
        // If filtering is enabled and the postcode itself is blacklisted -> return empty list
        if (filterBlacklisted && blackListService.isBlacklisted(postcode)) {
            return List.of();
        }

        return addressRepository.findByPostcode(postcode);
    }
}
