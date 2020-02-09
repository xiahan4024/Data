package com.xiahan.security.distributed.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: xiahan
 * @Date: 2020/2/9 16:11
 * @Description:
 */
@RestController
public class TestController {

    @GetMapping("/r1")
    // 拥有p1权限方可访问
    @PreAuthorize("hasAnyAuthority('p2')")
    public String r1(){
        return "p1";
    }

}
