package com.fit2cloud.gateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fit2cloud.base.service.IBaseUserRoleService;
import com.fit2cloud.common.constants.OrganizationConstants;
import com.fit2cloud.common.constants.RoleConstants;
import com.fit2cloud.common.constants.WorkspaceConstants;
import com.fit2cloud.common.utils.JwtTokenUtils;
import com.fit2cloud.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Order(1)
public class TwtTokenAuthFilter  implements WebFilter {

    @Resource
    private IBaseUserRoleService userRoleService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final static String CE_SOURCE_TOKEN = "CE_SOURCE";

    /**
     * 针对网关webflux逻辑有些区别
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getPath().value();
        //针对网关，只有部分接口需要认证，其余都交给子模块
        if (path.startsWith("/api/")) {
            UserDto userDtoFromToken = null;
            String token = request.getHeaders().getFirst(JwtTokenUtils.TOKEN_NAME);
            RoleConstants.ROLE role = RoleConstants.ROLE.ANONYMOUS;
            try {
                role = RoleConstants.ROLE.valueOf(request.getHeaders().getFirst(RoleConstants.ROLE_TOKEN));
            } catch (Exception ignore) {
            }
            if (StringUtils.isNotBlank(token)) {
                try {
                    userDtoFromToken = JwtTokenUtils.parseJwtToken(token);
                } catch (Exception ignore) {
                }
            }
            if (userDtoFromToken == null) {
                return writeErrorMessage(request, response, HttpStatus.UNAUTHORIZED, null);
            } else {
                //获取角色
                userDtoFromToken.setCurrentRole(role);

                //信任jwt，直接从jwt内取授权的角色
                //List<UserRoleDto> userRoleDtos = userDtoFromToken.getRoleMap().getOrDefault(userDtoFromToken.getCurrentRole(), new ArrayList<>());

                //为了防止用户编辑后与token中角色不同，从redis读取授权的角色
                userDtoFromToken.setRoleMap(userRoleService.getCachedUserRoleMap(userDtoFromToken.getId()));
                //List<UserRoleDto> userRoleDtos = userDtoFromToken.getRoleMap().getOrDefault(userDtoFromToken.getCurrentRole(), new ArrayList<>());

                String source = null;
                if (RoleConstants.ROLE.USER.equals(userDtoFromToken.getCurrentRole())) {
                    source = request.getHeaders().getFirst(WorkspaceConstants.WORKSPACE_TOKEN);
                } else if (RoleConstants.ROLE.ORGADMIN.equals(userDtoFromToken.getCurrentRole())) {
                    source = request.getHeaders().getFirst(OrganizationConstants.ORGANIZATION_TOKEN);
                }
                if (StringUtils.isBlank(source)) {
                    source = request.getHeaders().getFirst(CE_SOURCE_TOKEN);
                }
                userDtoFromToken.setCurrentSource(source);

                //todo 过滤？


                //将token放到上下文中
                try {
                    exchange.getAttributes().put("user", userDtoFromToken);
                } catch (Exception e) {
                    return this.writeErrorMessage(request, response, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
                }

                //todo token续期？

                response.getHeaders().add(JwtTokenUtils.TOKEN_NAME, token);
            }

        }

        //放行
        return chain.filter(exchange);
    }

    private Mono<Void> writeErrorMessage(ServerHttpRequest request, ServerHttpResponse response, HttpStatus status, String message) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(status);

        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("timestamp", new Date());
        errorAttributes.put("path", request.getPath().value());
        errorAttributes.put("status", status.value());
        errorAttributes.put("error", status.getReasonPhrase());
        errorAttributes.put("message", message);

        String body;
        try {
            body = objectMapper.writeValueAsString(errorAttributes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        DataBuffer dataBuffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(dataBuffer));
    }
}
