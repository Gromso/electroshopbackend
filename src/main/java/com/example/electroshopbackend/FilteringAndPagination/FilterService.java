package com.example.electroshopbackend.FilteringAndPagination;

import com.example.electroshopbackend.FilteringAndPagination.DTO.SearchRequestDTO;
import com.example.electroshopbackend.FilteringAndPagination.ENUM.GlobalOperator;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FilterService<T> {

    public Specification<T> getSearchedSpecification(List<SearchRequestDTO> searchRequestDTO, GlobalOperator globalOperator){
        return (root, query, cb)-> {

            List<Predicate> predicates = new ArrayList<Predicate>();

            for(SearchRequestDTO requestDTO : searchRequestDTO){
                switch (requestDTO.getOperation()) {
                    case EQUAL -> {
                        Predicate equals = cb.equal(root.get(requestDTO.getColumn()), requestDTO.getValue());
                        predicates.add(equals);
                    }
                    case LIKE -> {
                        Predicate like = cb.like(root.get(requestDTO.getColumn()),
                                "%" + requestDTO.getValue() + "%");
                        predicates.add(like);
                    }
                    case IN -> {
                        String[] split = requestDTO.getValue().split(",");
                        Predicate in =  root.get(requestDTO.getColumn()).in(Arrays.asList(split));
                        predicates.add(in);
                    }
                    case GREATER_THAN -> {
                        Predicate greaterThan = cb.greaterThan(root.get(requestDTO.getColumn()), requestDTO.getValue());
                        predicates.add(greaterThan);
                    }
                    case LESS_THAN -> {
                        Predicate lessThan = cb.lessThan(root.get(requestDTO.getColumn()), requestDTO.getValue());
                        predicates.add(lessThan);
                    }
                    case BETWEEN -> {
                        String[] num = requestDTO.getValue().split(",");
                        Predicate between = cb.between(root.get(requestDTO.getColumn()), num[0], num[1]);
                        predicates.add(between);
                    }
                    case JOIN -> {
                        predicates.add(handleJoin(root, cb, requestDTO));
                    }
                    default -> throw new IllegalArgumentException("Unexpected value" + " ");
                }

            }
            if(globalOperator.equals(GlobalOperator.AND)) {
                return cb.and(predicates.toArray(new Predicate[0]));
            }else {
                return cb.or(predicates.toArray(new Predicate[0]));
            }
        };
    }


    private Predicate handleJoin(Root<T> root, CriteriaBuilder cb, SearchRequestDTO requestDTO) {
        Join<?, ?> join = handleMultipleJoins(root, requestDTO.getJoinTable());
        return cb.equal(join.get(requestDTO.getColumn()), requestDTO.getValue());
    }

    private Join<?, ?> handleMultipleJoins(Root<T> root, String joinTablePath) {
        String[] joinTableNames = joinTablePath.split("\\.");

        Join<?, ?> currentJoin = null;
        for (String joinTableName : joinTableNames) {
            if (currentJoin == null) {
                currentJoin = root.join(joinTableName);
            } else {
                currentJoin = currentJoin.join(joinTableName);
            }
        }

        return currentJoin;
    }

}
