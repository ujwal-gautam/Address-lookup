package com.careernet.addresslookup.service;

import com.careernet.addresslookup.entity.Address;

import java.util.List;

public interface AddressService {

    public List<Address> getAllAddresses(boolean filterBlacklisted);
    public Address saveAddress(Address address);
    public List<Address> findAddressesByPostcode(String postcode, boolean filterBlacklisted);
}
