package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Param;
import org.csu.mypetstore.domain.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {

    List<Category> getCategoryList();

    Category getCategory(String categoryId);

    void insertCategory(Category category);

    void removeCategory(String categoryId);

    void updateCategory(@Param("category") Category category, @Param("oldId") String oldId);
}
