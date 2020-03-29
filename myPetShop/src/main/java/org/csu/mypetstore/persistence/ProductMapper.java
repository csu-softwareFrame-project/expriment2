package org.csu.mypetstore.persistence;
import org.csu.mypetstore.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {

    List<Product> getProductListByCategory(String categoryId);//使用商品id获取产品列表

    Product getProduct(String productId);//获取产品

    List<Product> searchProductList(String keywords);
}
