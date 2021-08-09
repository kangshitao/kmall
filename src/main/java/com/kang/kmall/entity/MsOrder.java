package com.kang.kmall.entity;

import java.util.Date;


public class MsOrder {
    private Integer id;
    private Integer sid;
    private String name;
    private Date createTime;

    public MsOrder() {
    }

    public MsOrder(Integer id, Integer sid, String name, Date createTime) {
        this.id = id;
        this.sid = sid;
        this.name = name;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "MsOrder{" +
                "id=" + id +
                ", sid=" + sid +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
