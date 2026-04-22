package com.rachidy.sassgestionstockapp.repositories;

import com.rachidy.sassgestionstockapp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {

    Optional<Category> findCategoryByNameIgnoreCase(String s);

}
