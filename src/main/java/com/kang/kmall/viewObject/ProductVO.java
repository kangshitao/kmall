package com.kang.kmall.viewObject;

/**
 * @author Kangshitao
 * @date 2021年8月1日 下午12:13
 */
public class ProductVO {
    private Integer id;
    private String name;
    private Float price;
    private String fileName;

    public ProductVO() {
    }


    public ProductVO(Integer id, String name, Float price, String fileName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.fileName = fileName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "ProductVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
