package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Param;
import org.csu.mypetstore.domain.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {

    List<Category> getCategoryList();

    List<Category> searchCategoryList(String keyword);

    Category getCategory(String categoryId);

    void insertCategory(Category category);

    void insertBannerData(Category category);

    void removeCategory(String categoryId);

    void removeBannerData(String categoryId);

    void updateCategory(@Param("category") Category category, @Param("oldId") String oldId);

    void updateBannerData(@Param("category") Category category, @Param("oldId") String oldId);
}
