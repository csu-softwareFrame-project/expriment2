package org.csu.mypetstore.domain;

import org.junit.Test;

import java.io.Serializable;

public class Category implements Serializable {

  private static final long serialVersionUID = 3992469837058393712L;

  private String categoryId;
  private String name;
  private String description;

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId.trim();
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
    info += "\"categoryId\":"+"\""+getCategoryId()+"\",";
    info += "\"name\":"+"\""+getName()+"\",";
    info += "\"description\":" + "\""+getDescription()+"\"}";
    return info;
  }

  @Test
  public void test(){
    categoryId = "a";
    name = "b";
    description = "c";
    System.out.println(toString());
  }
}
