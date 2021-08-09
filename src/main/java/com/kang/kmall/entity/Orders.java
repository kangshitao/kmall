package com.kang.kmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author kangshitao
 * @since 2021-07-31
 */
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 用户主键
     */
      private Integer userId;

      /**
     * 用户名
     */
      private String loginName;

      /**
     * 用户地址
     */
      private String userAddress;

      /**
     * 总金额
     */
      private Float cost;

    /**
     * 订单状态，0表示未付款，1表示已付款
     */
    private int status;

      /**
     * 订单号
     */
      private String serialnumber;

      /**
     * 创建时间
     */
        @TableField(fill = FieldFill.INSERT)
      private LocalDateTime createTime;

      /**
     * 更新时间
     */
        @TableField(fill = FieldFill.INSERT_UPDATE)
      private LocalDateTime updateTime;

    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public Integer getUserId() {
        return userId;
    }

      public void setUserId(Integer userId) {
          this.userId = userId;
      }
    
    public String getLoginName() {
        return loginName;
    }

      public void setLoginName(String loginName) {
          this.loginName = loginName;
      }
    
    public String getUserAddress() {
        return userAddress;
    }

      public void setUserAddress(String userAddress) {
          this.userAddress = userAddress;
      }
    
    public Float getCost() {
        return cost;
    }

      public void setCost(Float cost) {
          this.cost = cost;
      }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

      public void setSerialnumber(String serialnumber) {
          this.serialnumber = serialnumber;
      }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }

      public void setCreateTime(LocalDateTime createTime) {
          this.createTime = createTime;
      }
    
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

      public void setUpdateTime(LocalDateTime updateTime) {
          this.updateTime = updateTime;
      }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", userId=" + userId +
                ", loginName='" + loginName + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", cost=" + cost +
                ", status=" + status +
                ", serialnumber='" + serialnumber + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
