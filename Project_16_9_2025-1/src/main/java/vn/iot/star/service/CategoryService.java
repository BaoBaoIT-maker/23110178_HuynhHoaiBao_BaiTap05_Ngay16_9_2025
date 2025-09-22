package vn.iot.star.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.iot.star.entity.Category;

public interface CategoryService {
    List<Category> findAll();
    public Category findById(int id);
    Category save(Category category);
    void deleteById(int id);
    List<Category> findByCategoryNameContaining(String name);
    Page<Category> findAll(Pageable pageable);
    Page<Category> findByCategoryNameContaining(String name, Pageable pageable);
}
