package com.vscing.admin.controller.v1;

import com.vscing.admin.service.UserService;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import com.vscing.model.dto.UserListDto;
import com.vscing.model.vo.UserListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/user")
@Tag(name = "平台用户接口", description = "平台用户接口")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    @Operation(summary = "列表")
    public CommonResult<CommonPage<UserListVo>> lists(@ParameterObject UserListDto queryParam,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<UserListVo> list = userService.getList(queryParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(list));
    }
}
