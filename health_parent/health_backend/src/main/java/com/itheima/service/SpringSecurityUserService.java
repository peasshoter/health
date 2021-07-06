package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SpringSecurityUserService implements UserDetailsService {
    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.itheima.pojo.User user = userService.findByUsername(username);
        if (user == null) {
            return null;
        }
        List<GrantedAuthority> list = new ArrayList();
        Set<Role> roles = user.getRoles();
        if (roles != null) {
            for (Role role : roles) {
                list.add(new SimpleGrantedAuthority(role.getKeyword()));
                Set<Permission> permissions = role.getPermissions();
                for (Permission permission : permissions) {
                    if (permission != null) {
                        list.add(new SimpleGrantedAuthority(permission.getKeyword()));

                    }
                }



            }

        }

        return new User(username, user.getPassword(), list);
    }
}
