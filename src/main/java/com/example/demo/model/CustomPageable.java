package com.example.demo.model;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class CustomPageable<T> {
    private List<T> items;
    private int size;
    private int page;
    private int pageSize;
    private long totalSize;
    private int totalPage;

    public CustomPageable(final Page<T> pageable){
        items = pageable.toList();
        size = items.size();
        page = pageable.getNumber() + 1;
        pageSize = pageable.getSize();
        totalSize = pageable.getTotalElements();
        totalPage = pageable.getTotalPages();
    }
}
