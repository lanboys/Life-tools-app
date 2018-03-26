package com.mjn.libs.comm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @auther zhuwengang
 * @tiem 2017/11/29
 */

public class ICity implements Serializable {
    private Integer cityId;
    private String name;
    private Integer parentId;
    private String levelType;
    private List<ICity> list;

    public ICity() {
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCityId() {
        return this.cityId;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setLevelType(String levelType) {
        if (levelType != null) {
            this.levelType = levelType;
        }
    }

    public String getLevelType() {
        return this.levelType;
    }

    public void setList(List<ICity> list) {
        this.list = list;
    }

    public List<ICity> getList() {
        return this.list;
    }
}
