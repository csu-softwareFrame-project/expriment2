package org.csu.mypetstore.domain.receiveBean;

import java.io.Serializable;

public class receiveProduct implements Serializable {
    private static final long serialVersionUID = -2159121673445254631L;
    private String productId;
    private String productName;
    private String oldProductId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOldProductId() {
        return oldProductId;
    }

    public void setOldProductId(String oldProductId) {
        this.oldProductId = oldProductId;
    }

    @Override
    public String toString() {
        return "receiveProduct{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", oldProductId='" + oldProductId + '\'' +
                '}';
    }
}
