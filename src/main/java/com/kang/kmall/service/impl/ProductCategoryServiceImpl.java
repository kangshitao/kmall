package com.kang.kmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kang.kmall.entity.Product;
import com.kang.kmall.entity.ProductCategory;
import com.kang.kmall.mapper.ProductCategoryMapper;
import com.kang.kmall.mapper.ProductMapper;
import com.kang.kmall.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.kmall.service.ProductService;
import com.kang.kmall.viewObject.ProductCategoryVO;
import com.kang.kmall.viewObject.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kangshitao
 * @since 2021-07-31
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductMapper productMapper;

    //手动实现，将ProductCategory转换为ProductCategoryVO类
    @Override
    public List<ProductCategoryVO> getProductCategoryVO() {
        //将实体类转成VO
        //一级分类
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("type", 1);  //条件是type等于1
        List<ProductCategory> levelOne = productCategoryMapper.selectList(wrapper);
        /*
        List<ProductCategoryVO> levelOneVO = new ArrayList<>();
        for (ProductCategory productCategory : levelOne) {
            ProductCategoryVO productCategoryVO = new ProductCategoryVO();
            //使用BeanUtils工具类一次性复制所有属性
            BeanUtils.copyProperties(productCategory,productCategoryVO);
            levelOneVO.add(productCategoryVO);
        }*/
        //以上for循环，可以写成下面的形式。
        //将一级分类所有的ProductCategory都转换成ProductCategoryVO
        List<ProductCategoryVO> levelOneVO = levelOne.stream().map(e -> new ProductCategoryVO(e.getId(), e.getName())).
                collect(Collectors.toList());
        //设置图片和商品信息
        for (int i = 0; i < levelOneVO.size(); i++) {
            ProductCategoryVO productCategoryVO = levelOneVO.get(i);
            productCategoryVO.setBannerImg("banner" + i + ".png");
            productCategoryVO.setTopImg("top" + i + ".png");
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("categorylevelone_id",productCategoryVO.getId());
            List<Product> productList = productMapper.selectList(queryWrapper);
            List<ProductVO> productVOList = productList.stream().map(e->new ProductVO(
                            e.getId(),
                            e.getName(),
                            e.getPrice(),
                            e.getFileName())).collect(Collectors.toList());
            productCategoryVO.setProductVOList(productVOList);
        }
        //对于每个一级分类的VO对象，查找到其children并设置。
        for (ProductCategoryVO categoryVOOne : levelOneVO) {
            wrapper = new QueryWrapper();
            wrapper.eq("type", 2);  //查找type是2，并且parent_id是当前Type的所有行
            wrapper.eq("parent_id", categoryVOOne.getId());
            //二级分类
            List<ProductCategory> levelTwo = productCategoryMapper.selectList(wrapper);
            List<ProductCategoryVO> levelTwoVO = levelTwo.stream().map(e -> new ProductCategoryVO(e.getId(), e.getName())).
                    collect(Collectors.toList());
            categoryVOOne.setChildren(levelTwoVO);
            //对于每个二级分类，查找到其children，即三级分类
            for (ProductCategoryVO categoryVOTwo : levelTwoVO) {
                wrapper = new QueryWrapper();
                wrapper.eq("type", 3);
                wrapper.eq("parent_id", categoryVOTwo.getId());
                List<ProductCategory> levelThree = productCategoryMapper.selectList(wrapper);
                List<ProductCategoryVO> levelThreeVO = levelThree.stream().map(e -> new ProductCategoryVO(e.getId(), e.getName())).collect(Collectors.toList());
                categoryVOTwo.setChildren(levelThreeVO);
            }
        }

        return levelOneVO;
    }
}
