package com.mjn.libs.comm.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/17.
 */

public class InviteFriends implements Serializable {
    private Integer inviteFriendsId;
    private String title;
    private String content;
    private String wapUrl;
    private Float awardPercent;

    public InviteFriends() {
    }

    public void setInviteFriendsId(Integer inviteFriendsId) {
        this.inviteFriendsId = inviteFriendsId;
    }

    public Integer getInviteFriendsId() {
        return this.inviteFriendsId;
    }

    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
        }
    }

    public String getTitle() {
        return this.title;
    }

    public void setContent(String content) {
        if (content != null) {
            this.content = content;
        }
    }

    public String getContent() {
        return this.content;
    }

    public void setWapUrl(String wapUrl) {
        if (wapUrl != null) {
            this.wapUrl = wapUrl;
        }
    }

    public String getWapUrl() {
        return this.wapUrl;
    }

    public void setAwardPercent(Float awardPercent) {
        this.awardPercent = awardPercent;
    }

    public Float getAwardPercent() {
        return this.awardPercent;
    }
}

