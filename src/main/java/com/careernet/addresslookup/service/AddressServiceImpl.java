package com.careernet.addresslookup.service;

import com.careernet.addresslookup.entity.Address;
import com.careernet.addresslookup.entity.BlacklistedPostcode;
import com.careernet.addresslookup.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final BlackListService blackListService;

    public AddressServiceImpl(BlackListService blackListService, AddressRepository addressRepository) {
        this.blackListService = blackListService;
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> getAllAddresses(boolean filterBlacklisted) {
        List<Address> addresses = addressRepository.findAll();

        if (!filterBlacklisted) {
            return addresses;
        }

        List<BlacklistedPostcode> blacklisted = blackListService.getAllBlacklistedPostcodes();
        return addresses.stream()
                .filter(a -> !blacklisted.contains(a.getPostcode()))
                .collect(Collectors.toList());
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> findAddressesByPostcode(String postcode, boolean filterBlacklisted) {
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
