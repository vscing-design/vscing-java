package com.vscing.admin.service;

import com.vscing.model.dto.UserListDto;
import com.vscing.model.entity.User;
import com.vscing.model.vo.UserAmountVo;
import com.vscing.model.vo.UserListVo;

import java.util.List;

public interface UserService {

    /**
     * 管理端列表
     */
    List<UserListVo> getList(UserListDto data, Integer pageSize, Integer pageNum);

    /**
     * 管理端统计金额
     */
    UserAmountVo getUserAmount(UserListDto data);

    /**
     * 详情
     */
    User getDetails(long id);

    /**
     * 新增
     */
    long created(User user);

    /**
     * 删除
     */
    long deleted(long id, long deleterId);
}
