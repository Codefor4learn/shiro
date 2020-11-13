package com.hausen.shiro.service;

public interface UserService {

    void checkLogin(String userName,String userPwd) throws Exception;
}
