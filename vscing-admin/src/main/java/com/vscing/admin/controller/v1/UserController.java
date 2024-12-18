package com.vscing.admin.controller.v1;

import com.vscing.model.dto.UserListDto;
import com.vscing.model.dto.UserSaveDto;
import com.vscing.admin.service.UserService;
import com.vscing.model.vo.UserVo;
import com.vscing.model.dto.UserDto;
import com.vscing.common.api.CommonPage;
import com.vscing.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public CommonResult<List<UserVo>> listAll() {
        List<UserVo> userList = userService.getAllList();
        return CommonResult.success(userList);
    }

    @RequestMapping(value = "/addInfo", method = RequestMethod.POST)
    public CommonResult<Object> addInfo(@Validated @RequestBody UserDto userInfo, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            // 获取第一个错误信息，如果需要所有错误信息
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return CommonResult.validateFailed(errorMessage);
        }
        int id = userService.addInfo(userInfo);
        boolean isSuccess = id != 0;
        if (isSuccess) {
            return CommonResult.success("新增成功", id);
        } else {
            return CommonResult.failed("新增失败");
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<UserVo>> list(UserListDto queryParam,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<UserVo> userList = userService.getList(queryParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(userList));
    }

    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    public CommonResult<UserVo> getInfo(Integer id) {
        if(id == null) {
            return CommonResult.validateFailed("参数错误");
        }
        UserVo result = userService.getInfo(id);
        if (result == null) {
            return CommonResult.failed("获取信息失败：用户不存在");
        }
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    public CommonResult<Object> updateInfo(@Validated @RequestBody UserSaveDto userInfo, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            // 获取第一个错误信息，如果需要所有错误信息
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return CommonResult.validateFailed(errorMessage);
        }
        int id = userService.updateInfo(userInfo);
        boolean isSuccess = id != 0;
        if (isSuccess) {
            return CommonResult.success("修改成功", id);
        } else {
            return CommonResult.failed("修改失败");
        }
    }

    @RequestMapping(value = "/deleteInfo/{id}", method = RequestMethod.GET)
    public CommonResult<Object> deleteInfo(@PathVariable("id") Integer id) {
        if(id == null) {
            return CommonResult.validateFailed("参数错误");
        }
        int result = userService.deleteInfo(id);
        boolean isSuccess = result != 0;
        if (isSuccess) {
            return CommonResult.success("删除成功", result);
        } else {
            return CommonResult.failed("删除失败");
        }
    }
}
