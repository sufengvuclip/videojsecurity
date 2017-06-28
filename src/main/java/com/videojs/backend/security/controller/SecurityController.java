package com.videojs.backend.security.controller;

import com.videojs.backend.security.bean.SecurityBean;
import com.videojs.backend.security.service.SecurityService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by sufeng on 6/26/17.
 */
@Controller
@RequestMapping("/security")
public class SecurityController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(SecurityController.class);
    @Autowired
    SecurityService securityService;

    @RequestMapping("/getToken")
    public ResponseEntity<?> search(@RequestParam(value = "pid",required = true) String pid,
                                    @RequestParam(value = "uid",required = true) String uid,
                                    @RequestParam(value = "exp",required = false,defaultValue = "-1") long expiredSeconds
                                    ) {
        String ret = null;
        try {
            ret = securityService.createToken(pid,uid,expiredSeconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>(ret, HttpStatus.OK);
    }

    @RequestMapping("/testApi")
    public ResponseEntity<?> search(@RequestParam(value = "t",required = true) String token,
                                    @RequestParam(value = "n",required = true) String name,
                                    HttpServletRequest request, HttpServletResponse response
                                    ) {
        SecurityBean sb = null;
        String ret = "";
        try {
            sb = securityService.decryptToken(token);
            ret = securityService.verifySecurityBean(sb);
        } catch (Exception e) {
            e.printStackTrace();
            ret = "decrypt token error!";
        }
        if("success".equalsIgnoreCase(ret)){
            ret = "Welcome, "+name;
        }
        response.setHeader("Access-Control-Allow-Origin","http://staging.vuclip.com:9092");
        return new ResponseEntity<String>(ret, HttpStatus.OK);
    }
}
