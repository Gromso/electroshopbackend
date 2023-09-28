package com.example.electroshopbackend.FilteringAndPagination.DTO;


import com.example.electroshopbackend.FilteringAndPagination.ENUM.Operation;
import lombok.Getter;


@Getter
public class SearchRequestDTO {


    private String column;
    private String value;
    private Operation operation;
    private String joinTable;
}
