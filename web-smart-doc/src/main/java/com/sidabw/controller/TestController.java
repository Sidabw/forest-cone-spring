package com.sidabw.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sidabw.common.StdPageResult;
import com.sidabw.common.StdResult;
import com.sidabw.vo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * smart-doc测试controller
 * @author shaogz
 * @since 2024/9/19 11:54
 */

@RestController
public class TestController {

    /**
     * 获取一个用户
     * @param id 项目id
     * @return com.sidabw.vo.User 用户信息范湖
     * @author shaogz 2024/9/19 11:56
     */
    @GetMapping(value = "/a/get/user")
    public User getUser(String id) {
        return new User();
    }

    /**
     * 创建用户接口
     * @param user 用户body
     * @author shaogz 2024/9/25 15:28
     */
    @PostMapping(value = "/b/post/user")
    public void postUser(@RequestBody User user) {

    }

    /**
     * pageQueryUser  分页查询
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.sidabw.vo.User>
     * @author shaogz 2024/9/25 16:01
     */
    @GetMapping(value = "/c/page/user-list")
    public Page<User> pageQueryUser() {
        return null;
    }

    /**
     * findUserDetail 查询用户详情
     * @param id 用户id
     * @return com.sidabw.common.StdResult<com.sidabw.vo.User>
     * @author shaogz 2024/9/25 16:04
     */
    @GetMapping(value = "/d/user-detail")
    public StdResult<User> findUserDetail(Long id) {
        return null;
    }

    /**
     * pageUserV2 分页查询用户信息-v2版本
     * @return com.sidabw.common.StdPageResult<com.sidabw.vo.User>
     * @author shaogz 2024/9/25 16:05
     */
    @GetMapping(value = "/e/page/user-list-v2")
    public StdPageResult<User> pageUserV2() {
        return null;
    }
}
