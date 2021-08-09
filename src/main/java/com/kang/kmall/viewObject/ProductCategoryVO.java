package com.kang.kmall.viewObject;

import java.util.List;

/**
 * @author Kangshitao
 * @date 2021年7月31日 下午6:05
 */
//视图对象，用于页面显示三级分类，商品种类一二三级
//将ProductCategory对象封装成对应的VO类
public class ProductCategoryVO {
    private Integer id;
    private String name;
    private List<ProductCategoryVO> children;  //一级包括二级，二级包括三级
    private String bannerImg;
    private String topImg;
    private List<ProductVO> productVOList;

    public ProductCategoryVO() {
    }

    public ProductCategoryVO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductCategoryVO(Integer id, String name, List<ProductCategoryVO> children, String bannerImg, String topImg, List<ProductVO> productVOList) {
        this.id = id;
        this.name = name;
        this.children = children;
        this.bannerImg = bannerImg;
        this.topImg = topImg;
        this.productVOList = productVOList;
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

    public List<ProductCategoryVO> getChildren() {
        return children;
    }

    public void setChildren(List<ProductCategoryVO> children) {
        this.children = children;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public String getTopImg() {
        return topImg;
    }

    public void setTopImg(String topImg) {
        this.topImg = topImg;
    }

    public List<ProductVO> getProductVOList() {
        return productVOList;
    }

    public void setProductVOList(List<ProductVO> productVOList) {
        this.productVOList = productVOList;
    }
}
