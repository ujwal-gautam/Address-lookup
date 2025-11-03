package com.careernet.addresslookup.service;

import com.careernet.addresslookup.entity.BlacklistedPostcode;

import java.util.List;

public interface BlackListService {

    public boolean isBlacklisted(String postcode);

    public List<BlacklistedPostcode> getAllBlacklistedPostcodes();

    public BlacklistedPostcode addPostcode(String postcode);

    public void removePostcode(String postcode);
}
