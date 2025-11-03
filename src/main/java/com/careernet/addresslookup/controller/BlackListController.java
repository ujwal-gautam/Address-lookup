package com.careernet.addresslookup.controller;

import com.careernet.addresslookup.dto.ApiResponse;
import com.careernet.addresslookup.entity.BlacklistedPostcode;
import com.careernet.addresslookup.service.BlackListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/blacklist")
public class BlackListController {

    private final BlackListService blackListService;

    public BlackListController(BlackListService blackListService) {
        this.blackListService = blackListService;
    }

    // Get all blacklisted postcodes
    @GetMapping
    public ResponseEntity<ApiResponse<List<BlacklistedPostcode>>> getAll() {
        List<BlacklistedPostcode> list = blackListService.getAllBlacklistedPostcodes();
        return ResponseEntity.ok(new ApiResponse<>("Blacklisted postcodes retrieved", list, true));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<BlacklistedPostcode>> add(@RequestParam(required = false) String postcode) {
        if (Objects.isNull(postcode) || postcode.isBlank()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Postcode cannot be null or empty", null, false));
        }
        try {
            BlacklistedPostcode added = blackListService.addPostcode(postcode.trim());
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Added to blacklist", added, true));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(ex.getMessage(), null, false));
        }
    }

    @DeleteMapping("/remove/{postcode}")
    public ResponseEntity<ApiResponse<String>> remove(@PathVariable String postcode) {
        if (Objects.isNull(postcode) || postcode.isBlank()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Postcode cannot be null or empty", null, false));
        }
        try {
            blackListService.removePostcode(postcode.trim());
            return ResponseEntity.ok(new ApiResponse<>("Removed postcode: " + postcode.toUpperCase(), null, true));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(ex.getMessage(), null, false));
        }
    }
}
