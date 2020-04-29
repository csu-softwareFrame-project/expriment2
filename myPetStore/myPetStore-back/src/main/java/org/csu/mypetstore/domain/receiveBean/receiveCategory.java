package org.csu.mypetstore.domain.receiveBean;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class receiveCategory implements Serializable {
    private static final long serialVersionUID = -2159121673445254631L;

    private String categoryId;
    private String categoryName;
    private String oldCategoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getOldCategoryId() {
        return oldCategoryId;
    }

    public void setOldCategoryId(String oldCategoryId) {
        this.oldCategoryId = oldCategoryId;
    }

    @Override
    public String toString() {
        return "receiveCategory{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", oldCategoryId='" + oldCategoryId + '\'' +
                '}';
    }
}
