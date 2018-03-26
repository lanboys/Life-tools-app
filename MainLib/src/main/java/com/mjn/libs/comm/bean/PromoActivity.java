package com.mjn.libs.comm.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/10/16.
 */

public class PromoActivity implements Serializable {

    /**
     * activityId : 2
     * productId : -1
     * name : 注册就送888元红包啊
     * description : 包公有财隆重上线，现在注册就送888元红包，赶紧来瞅瞅吧~
     * effectiveDate : 2016-10-25 00:00:00
     * expireDate : 2018-10-31 00:00:00
     * imgUrl : http://ofbumd4go.bkt.clouddn.com/zhucejisong.jpg
     * jumpUrl : http://192.168.10.57:8081/api/activity/register_achieve_money
     * showPosition : 3
     * awardsType : 7
     */

    private int productId;
    private Integer activityId;
    private String name;
    private String description;
    private Date effectiveDate;
    private Date expireDate;
    private String imgUrl;
    private String jumpUrl;
    private int showPosition;
    private int awardsType;
    private Integer awardsBatchId;


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public PromoActivity() {
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getActivityId() {
        return this.activityId;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getExpireDate() {
        return this.expireDate;
    }

    public void setImgUrl(String imgUrl) {
        if (imgUrl != null) {
            this.imgUrl = imgUrl;
        }
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        if (jumpUrl != null) {
            this.jumpUrl = jumpUrl;
        }
    }

    public String getJumpUrl() {
        return this.jumpUrl;
    }

    public void setShowPosition(int showPosition) {
        this.showPosition = showPosition;
    }

    public int getShowPosition() {
        return this.showPosition;
    }

    public void setAwardsType(int awardsType) {
        this.awardsType = awardsType;
    }

    public int getAwardsType() {
        return this.awardsType;
    }

    public void setAwardsBatchId(Integer awardsBatchId) {
        this.awardsBatchId = awardsBatchId;
    }

    public Integer getAwardsBatchId() {
        return this.awardsBatchId;
    }

}
