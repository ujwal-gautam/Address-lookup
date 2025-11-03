package com.careernet.addresslookup.repository;

import com.careernet.addresslookup.entity.BlacklistedPostcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlackListRepository extends JpaRepository<BlacklistedPostcode, Long> {

    Optional<BlacklistedPostcode> findByPostcode(String postcode);
    boolean existsByPostcode(String postcode);
    void deleteByPostcode(String postcode);
}
