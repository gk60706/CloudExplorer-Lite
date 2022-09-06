package com.fit2cloud.security;

import com.fit2cloud.base.service.IBaseUserRoleService;
import com.fit2cloud.security.filter.JwtTokenAuthFilter;
import com.fit2cloud.security.filter.UserLoginFilter;
import com.fit2cloud.service.PermissionService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class SecurityDSL extends AbstractHttpConfigurer<SecurityDSL, HttpSecurity> {

    private static PermissionService permissionService;

    private static IBaseUserRoleService userRoleService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //只能在这获取AuthenticationManager
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilter(new UserLoginFilter(authenticationManager));
        http.addFilter(new JwtTokenAuthFilter(authenticationManager, permissionService, userRoleService));
    }

    public static SecurityDSL securityDSL(PermissionService permissionService, IBaseUserRoleService userRoleService) {
        SecurityDSL dsl = new SecurityDSL();
        SecurityDSL.permissionService = permissionService;
        SecurityDSL.userRoleService = userRoleService;
        return dsl;
    }

}
