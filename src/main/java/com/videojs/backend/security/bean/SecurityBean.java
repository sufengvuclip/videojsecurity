package com.videojs.backend.security.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * Created by sufeng on 6/26/17.
 */
@Data
@JsonSerialize
public class SecurityBean {
    private String pid;
    private String uid;
    private String securityString;
    private long createTime;
    private long expireTime;

    public SecurityBean(String pid, String uid, String securityString, long createTime, long expireTime) {
        this.pid = pid;
        this.uid = uid;
        this.securityString = securityString;
        this.createTime = createTime;
        this.expireTime = expireTime;
    }
    public SecurityBean(){

    }
}
