package com.sch.owl.constant;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 通用常量信息
 * 
 * @author ruoyi
 */
public interface Constants

{

    Integer TIME_CONVERT = 60;

    /**
     * UTF-8 字符集
     */
    String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    String GBK = "GBK";

    /**
     * www主域
     */
    String WWW = "www.";

    /**
     * http请求
     */
    String HTTP = "http://";

    /**
     * https请求
     */
    String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    String FAIL = "1";

    /**
     * 登录成功
     */
    String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    String LOGOUT = "Logout";

    /**
     * 注册
     */
    String REGISTER = "Register";

    /**
     * 登录失败
     */
    String LOGIN_FAIL = "Error";

    /**
     * 所有权限标识
     */
    String ALL_PERMISSION = "*:*:*";

    /**
     * 管理员角色权限标识
     */
    String SUPER_ADMIN = "admin";

    /**
     * 角色权限分隔符
     */
    String ROLE_DELIMETER = ",";

    /**
     * 权限标识分隔符
     */
    String PERMISSION_DELIMETER = ",";

    /**
     * 验证码有效期（分钟）
     */
    Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    String TOKEN = "token";

    /**
     * 令牌前缀
     */
    String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    String JWT_USERNAME = Claims.SUBJECT;

    /**
     * 用户头像
     */
    String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    String JWT_AUTHORITIES = "authorities";

    /**
     * 资源映射路径 前缀
     */
    String RESOURCE_PREFIX = "/profile";

    /**
     * RMI 远程方法调用
     */
    String LOOKUP_RMI = "rmi:";

    /**
     * LDAP 远程方法调用
     */
    String LOOKUP_LDAP = "ldap:";

    /**
     * LDAPS 远程方法调用
     */
    String LOOKUP_LDAPS = "ldaps:";

    /**
     * 自动识别json对象白名单配置（仅允许解析的包名，范围越小越安全）
     */
    String[] JSON_WHITELIST_STR = { "org.springframework", "com.rdrk" };

    // 下划线
    String UNDERLINE = "_";

    /**
     * 星号
     */
    String ASTERISK = "*";

    /**
     * #号
     */
    String SIGN_NUMBER = "#";

    Integer ZERO = 0;

    Long ZERO_LONG = 0L;

    @Getter
    enum YesAndNoEnum{
        /**
         *  标识是否的标识
         */
        YES(1),
        NO(0);

        private int value;

        private String strValue;

        YesAndNoEnum(int value) {
            this.value = value;
            this.strValue = String.valueOf(value);
        }

        public static Boolean isYes(int value){
            return Objects.equals(value, YES.getValue());
        }

        public static Boolean isYes(String value){
            return StringUtils.equals(value, YES.getStrValue());
        }

        public static Boolean isNoStr(String value){
            return StringUtils.equals(value, NO.getStrValue());
        }
    }

    @Getter
    enum SexEnum {
        // 女
        MALE("1"),
        FEMALE("0");

        SexEnum(String value) {
            this.value = value;
        }

        private String value;

    }
}
