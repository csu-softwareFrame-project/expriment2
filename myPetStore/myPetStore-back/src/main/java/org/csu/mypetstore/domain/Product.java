package org.csu.mypetstore.domain;

import org.junit.Test;

import java.io.Serializable;

public class Product implements Serializable {

  private static final long serialVersionUID = -7492639752670189553L;
  private String productId;
  private String categoryId;
  private String name;
  private String description;

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId.trim();
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String toString() {
    String info = "{";
    info += "\"productId\":"+"\""+getProductId()+"\",";
    info += "\"categoryId\":" +"\""+getCategoryId()+"\",";
    info += "\"name\":"+"\""+getName()+"\",";
    info += "\"description\":"+"\""+getDescription()+"\"}";
    return info;
  }

  public String toString2(){
    String info = ",";
//    info += "\"productId\":"+"\""+getProductId()+"\",";
    info += "\"categoryId\":" +"\""+getCategoryId()+"\",";
    info += "\"name\":"+"\""+getName()+"\",";
    info += "\"description\":"+"\""+getDescription()+"\",";
    return info;
  }

  @Test
  public void test(){
    productId = "1";
    categoryId = "2";
    name = "3";
    description = "sadfa";
    System.out.println(toString2());
  }

}
