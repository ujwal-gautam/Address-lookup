package com.careernet.addresslookup.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private T data;
    private boolean success;

}
