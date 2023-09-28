package com.example.electroshopbackend.Models.Article;

import com.example.electroshopbackend.FilteringAndPagination.DTO.PageRequestDTO;
import com.example.electroshopbackend.FilteringAndPagination.DTO.RequestDTO;
import com.example.electroshopbackend.FilteringAndPagination.FilterService;
import com.example.electroshopbackend.Models.Category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {


    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private FilterService<Article> filterService;


    @GetMapping("/all")
    public ResponseEntity<List<Article>> a (){
        List<Article> ar = articleRepository.findAll();
        return ResponseEntity.ok(ar);
    }

    @PostMapping("/filters")
    public ResponseEntity<Page<Article>> filtersCategory(@RequestBody RequestDTO body){
        Specification<Article> specification = filterService.getSearchedSpecification(body.getSearchRequestDTOs(), body.getGlobalOperator());
        Pageable pageable = new PageRequestDTO().getPageable(body.getPage());
        Page<Article> Article = articleRepository.findAll(specification, pageable);
        try{
            return new ResponseEntity<>(Article, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            throw new IllegalStateException("sortByColumn not found");
        }
    }
}
