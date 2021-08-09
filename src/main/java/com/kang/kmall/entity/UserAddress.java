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
public class UserAddress implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 用户主键
     */
      private Integer userId;

      /**
     * 地址
     */
      private String address;

      /**
     * 备注
     */
      private String remark;

      /**
     * 是否是默认地址（1:是 0否）
     */
      private Integer isdefault;

      /**
     * 创建时间
     */
        @TableField(fill = FieldFill.INSERT)
      private LocalDateTime createTime;

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
    
    public String getAddress() {
        return address;
    }

      public void setAddress(String address) {
          this.address = address;
      }
    
    public String getRemark() {
        return remark;
    }

      public void setRemark(String remark) {
          this.remark = remark;
      }
    
    public Integer getIsdefault() {
        return isdefault;
    }

      public void setIsdefault(Integer isdefault) {
          this.isdefault = isdefault;
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
        return "UserAddress{" +
              "id=" + id +
                  ", userId=" + userId +
                  ", address=" + address +
                  ", remark=" + remark +
                  ", isdefault=" + isdefault +
                  ", createTime=" + createTime +
                  ", updateTime=" + updateTime +
              "}";
    }
}
