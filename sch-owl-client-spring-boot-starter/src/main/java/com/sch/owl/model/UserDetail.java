package com.sch.owl.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * 登陆之后的用户信息
 *
 * @author Ydh
 */
public class UserDetail implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 登陆的用户信息
     */
    @Getter
    @Setter
    private LoginUser loginUser;

    /**
     * 过期时间
     */
    @Getter
    @Setter
    private Long expireTime;

    /**
     * 登录时间
     */
    @Setter
    @Getter
    private Long loginTime;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return loginUser.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
