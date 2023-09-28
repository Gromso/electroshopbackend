package com.example.electroshopbackend.FilteringAndPagination.DTO;

import com.example.electroshopbackend.FilteringAndPagination.ENUM.GlobalOperator;
import lombok.Getter;


import java.util.List;


@Getter
public class RequestDTO {

    private GlobalOperator globalOperator;
    private List<SearchRequestDTO> searchRequestDTOs;
    private PageRequestDTO page;



}
