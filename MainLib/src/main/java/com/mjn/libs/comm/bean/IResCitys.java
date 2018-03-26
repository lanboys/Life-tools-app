package com.mjn.libs.comm.bean;


import java.io.Serializable;
import java.util.List;

/**
 * @auther zhuwengang
 * @tiem 2017/11/29
 */

public class IResCitys implements Serializable {
    private String version;
    private List<ICity> list;

    public IResCitys() {
    }

    public void setVersion(String version) {
        if (version != null) {
            this.version = version;
        }
    }

    public String getVersion() {
        return this.version;
    }

    public void setList(List<ICity> list) {
        this.list = list;
    }

    public List<ICity> getList() {
        return this.list;
    }
}

