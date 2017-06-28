package com.videojs.backend.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.videojs.backend.security.bean.SecurityBean;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Iterator;

/**
 * Created by sufeng on 6/26/17.
 */
@Component
public class SecurityService {
    public String SECURITY_KEY = "security";
    public static String JWT_ENCRYPTION_KEY = "Yosemite2017@USA";
    public static ObjectMapper mapper = new ObjectMapper();
    public String createToken(String pid,String uid,long expiredSeconds) throws Exception {

        Key key = new AesKey(JWT_ENCRYPTION_KEY.getBytes());
        JsonWebEncryption jwe = new JsonWebEncryption();

        SecurityBean sb = new SecurityBean(pid,uid,SECURITY_KEY,System.currentTimeMillis(),(System.currentTimeMillis() + (expiredSeconds>0?expiredSeconds*1000:3600000L)));

        jwe.setPayload(mapper.writeValueAsString(sb));
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        jwe.setKey(key);
        String jwt = jwe.getCompactSerialization();
        return jwt;
    }

    public SecurityBean decryptToken(String token) throws Exception {
        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setKey(new AesKey(JWT_ENCRYPTION_KEY.getBytes()));
        jwe.setCompactSerialization(token);
        String payLoad = jwe.getPayload();
        SecurityBean ret = null;
        if (payLoad != null && payLoad.trim().length() > 0) {
            System.out.println("payload:"+payLoad);
            ret = mapper.readValue(payLoad,SecurityBean.class);
        }
        return ret;
    }

    public String verifySecurityBean(SecurityBean sb){
        String ret = "success";
        if(sb==null)
            ret = "token is not available";
        else if(!SECURITY_KEY.equals(sb.getSecurityString())){
            ret = "security key is wrong";
        }else if(System.currentTimeMillis()>sb.getExpireTime()){
            ret = "token is expired";
        }
        return ret;
    }


}
