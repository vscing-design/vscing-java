package com.vscing.admin.service;

import com.vscing.model.dto.MenuListDto;
import com.vscing.model.entity.Menu;

import java.util.List;

public interface MenuService {

    /**
     * 列表
     */
    List<Menu> getAllList(MenuListDto record);

    /**
     * 列表
     */
    List<Menu> getList(MenuListDto record, Integer pageSize, Integer pageNum);

    /**
     * 详情
     */
    Menu getDetails(long id);

    /**
     * 新增
     */
    long created(Menu Menu);

    /**
     * 编辑
     */
    long updated(Menu Menu);

    /**
     * 删除
     */
    long deleted(long id, long deleterId);


}
