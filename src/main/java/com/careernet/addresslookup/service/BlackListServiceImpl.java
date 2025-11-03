package com.careernet.addresslookup.service;

import com.careernet.addresslookup.entity.BlacklistedPostcode;
import com.careernet.addresslookup.repository.AddressRepository;
import com.careernet.addresslookup.repository.BlackListRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class BlackListServiceImpl implements BlackListService {

    private final BlackListRepository blackListRepository;
    private final AddressRepository addressRepository;

    public BlackListServiceImpl(BlackListRepository blackListRepository, AddressRepository addressRepository) {
        this.blackListRepository = blackListRepository;
        this.addressRepository = addressRepository;
    }

    public boolean isBlacklisted(String postcode) {
        if (Objects.isNull(postcode)) return false;
        return blackListRepository.existsByPostcode(postcode);
    }

    public List<BlacklistedPostcode> getAllBlacklistedPostcodes() {
        return blackListRepository.findAll();
    }

    @Transactional
    public BlacklistedPostcode addPostcode(String postcode) {
        if (Objects.isNull(postcode) || postcode.isBlank()) {
            throw new IllegalArgumentException("Postcode cannot be null or empty");
        }
        boolean existsInAddress = addressRepository.existsByPostcode(postcode);
        if (!existsInAddress) {
            log.info("BlackListServiceImpl message = \"Data not found for\" postCode {}", postcode);
            throw new IllegalArgumentException("Cannot blacklist postcode '" + postcode + "' because it does not exist in Address table.");
        }

        if (blackListRepository.existsByPostcode(postcode)) {
            log.info("BlackListServiceImpl message = \"Postcode already blacklisted for\" postCode {}", postcode);
            throw new IllegalArgumentException("Postcode already blacklisted: " + postcode);
        }
        return blackListRepository.save(new BlacklistedPostcode(postcode));
    }

    @Transactional
    public void removePostcode(String postcode) {
        if (Objects.isNull(postcode) || postcode.isBlank()) {
            throw new IllegalArgumentException("Postcode cannot be null or empty");
        }
        if (!blackListRepository.existsByPostcode(postcode)) {
            throw new IllegalArgumentException("Postcode not found in blacklist: " + postcode);
        }
        blackListRepository.deleteByPostcode(postcode);
    }
}
