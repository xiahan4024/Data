package com.xiahan.security.distributed.order.controller;

import com.xiahan.security.distributed.order.model.UserDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: xiahan
 * @Date: 2020/2/9 16:11
 * @Description:
 */
@RestController
public class TestController {

    @GetMapping("/r1/test")
    // 拥有p1权限方可访问
    @PreAuthorize("hasAnyAuthority('p1', 'p2')")
    public String r2() {
        return "p1";
    }

    @GetMapping(value = "/r1")
    @PreAuthorize("hasAuthority('p1')")//拥有p1权限方可访问此url
    public String r1() {
        //获取用户身份信息
        UserDTO userDTO = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDTO.getFullname() + "访问资源1";
    }

}
