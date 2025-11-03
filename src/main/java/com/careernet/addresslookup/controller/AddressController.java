package com.careernet.addresslookup.controller;


import com.careernet.addresslookup.dto.ApiResponse;
import com.careernet.addresslookup.entity.Address;
import com.careernet.addresslookup.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/byPostCode")
    public ResponseEntity<ApiResponse<List<Address>>> getAddressesByPostcode(
            @RequestParam String postcode,
            @RequestParam(defaultValue = "false") boolean filter
    ) {
        if (Objects.isNull(postcode) || postcode.isBlank()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Postcode cannot be empty", List.of(), false));
        }

        List<Address> result = addressService.findAddressesByPostcode(postcode, filter);
        if (result.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>("No addresses found or blackListed for postcode: " + postcode, List.of(), true));
        }
        return ResponseEntity.ok(new ApiResponse<>("Addresses retrieved", result, true));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Address>> addAddress(@RequestBody Address address) {
        if (Objects.isNull(address.getLine1()) || Objects.isNull(address.getCity()) || Objects.isNull(address.getPostcode())) {
            throw new IllegalArgumentException("All address fields (line1, city, postcode) are required");
        }
        Address saved = addressService.saveAddress(address);
        return ResponseEntity.ok(new ApiResponse<>("Address saved successfully", saved, true));
    }
}
