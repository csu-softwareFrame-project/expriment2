package org.csu.mypetstore.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartItem implements Serializable {
    private static final long serialVersionUID = 6620528781626504362L;

    private Item item;  //商品
    private String itemId;  //商品id
    private int quantity;  //数量
    private boolean inStock;  //库存是否充足
    private BigDecimal total;  //总价

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        calculateTotal();
    }

    public int getQuantity() {
        return quantity;
    }

    public String getItemId()
    {
        return this.itemId;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotal();
    }

    public void incrementQuantity() {
        quantity++;
        calculateTotal();
    }

    private void calculateTotal() {
        if (item != null && item.getListPrice() != null) {
            total = item.getListPrice().multiply(new BigDecimal(quantity));
        } else {
            total = null;
        }
    }

    public String toString()
    {
        String info = new String();
        info = getItem().toString();
        info = info + '\n' + getItemId();
        info = info + '\n' + getQuantity();
        info = info + '\n' + getTotal();
        info = info + '\n' + isInStock();

        return info;
    }

}
