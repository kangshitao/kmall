package com.kang.kmall.viewObject;

/**
 * @author Kangshitao
 * @date 2021年8月2日 上午11:46
 */

/**
 * 购物车视图对象，包括商品的部分属性和购物车实体类的部分属性
 */
public class CartVO {
    private Integer id; //每个cart的id，或者说
    private Integer productId; //商品id
    private Float price;  //商品价格
    private String name; //商品名
    private String fileName;  //商品图片
    private Integer quantity; //个数
    private Float cost; //总花费
    private Integer stock;
    public CartVO() {
    }

    public CartVO(Integer id, Integer productId, Float price, String name, String fileName, Integer quantity, Float cost, Integer stock) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.fileName = fileName;
        this.quantity = quantity;
        this.cost = cost;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "CartVO{" +
                "id=" + id +
                ", productId=" + productId +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", fileName='" + fileName + '\'' +
                ", quantity=" + quantity +
                ", cost=" + cost +
                ", stock=" + stock +
                '}';
    }
}
