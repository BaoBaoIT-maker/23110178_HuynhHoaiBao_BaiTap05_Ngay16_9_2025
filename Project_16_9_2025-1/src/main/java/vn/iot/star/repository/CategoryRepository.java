package vn.iot.star.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iot.star.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer > {

    List<Category> findByNameContaining(String name);

    Page<Category> findByNameContaining(String name, Pageable pageable);
    
}
