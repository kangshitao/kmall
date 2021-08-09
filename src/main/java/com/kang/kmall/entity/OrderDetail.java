package com.kang.kmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author kangshitao
 * @since 2021-07-31
 */
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 订单主键
     */
      private Integer orderId;

      /**
     * 商品主键
     */
      private Integer productId;

      /**
     * 数量
     */
      private Integer quantity;

      /**
     * 消费
     */
      private Float cost;

    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public Integer getOrderId() {
        return orderId;
    }

      public void setOrderId(Integer orderId) {
          this.orderId = orderId;
      }
    
    public Integer getProductId() {
        return productId;
    }

      public void setProductId(Integer productId) {
          this.productId = productId;
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

    @Override
    public String toString() {
        return "OrderDetail{" +
              "id=" + id +
                  ", orderId=" + orderId +
                  ", productId=" + productId +
                  ", quantity=" + quantity +
                  ", cost=" + cost +
              "}";
    }
}
