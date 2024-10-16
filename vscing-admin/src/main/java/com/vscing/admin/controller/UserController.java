package com.vscing.admin.controller;

import com.vscing.admin.service.UserService;
import com.vscing.admin.vo.UserVo;
import com.vscing.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public CommonResult<List<UserVo>> listAll() {
        List<UserVo> list = userService.getList();
        return new CommonResult<List<UserVo>>(200, "成功", list);
    }

    @RequestMapping(value = "/addInfo", method = RequestMethod.POST)
    public CommonResult<Object> addInfo(@RequestBody UserDto userInfo) {
        return new CommonResult<Object>(200, "成功", userInfo);
    }

//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<CommonPage<UserVo>> list(UserQueryParam queryParam,
//                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
//                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
//        List<UserVo> orderList = userService.list(queryParam, pageSize, pageNum);
//        return CommonResult.success(CommonPage.restPage(orderList));
//    }
}
