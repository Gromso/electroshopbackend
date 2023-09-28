package com.example.electroshopbackend.Models.Category;

import com.example.electroshopbackend.FilteringAndPagination.DTO.PageRequestDTO;
import com.example.electroshopbackend.FilteringAndPagination.DTO.RequestDTO;
import com.example.electroshopbackend.FilteringAndPagination.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FilterService<Category> filterService;

    @GetMapping("/all")
    public ResponseEntity<List<Category>> cat(){
        List<Category> e = categoryRepository.findAll();

        return ResponseEntity.ok(e);
    }

    @PostMapping("/filters")
    public ResponseEntity<Page<Category>> filtersCategory(@RequestBody RequestDTO body){
        Specification<Category> specification = filterService.getSearchedSpecification(body.getSearchRequestDTOs(), body.getGlobalOperator());
        Pageable pageable = new PageRequestDTO().getPageable(body.getPage());
        Page<Category> category = categoryRepository.findAll(specification, pageable);
        try{
            return new ResponseEntity<>(category, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            throw new IllegalStateException("sortByColumn not found");
        }
    }
}
