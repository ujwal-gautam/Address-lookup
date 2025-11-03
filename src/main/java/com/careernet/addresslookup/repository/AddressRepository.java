package com.careernet.addresslookup.repository;

import com.careernet.addresslookup.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    public List<Address> findByPostcode(String Postcode);

    boolean existsByPostcode(String postcode);
}
