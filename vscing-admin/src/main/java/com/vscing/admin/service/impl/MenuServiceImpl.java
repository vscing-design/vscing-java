package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.MenuService;
import com.vscing.model.dto.MenuListDto;
import com.vscing.model.mapper.MenuMapper;
import com.vscing.model.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getList(MenuListDto record, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return menuMapper.getList(record);
    }

    @Override
    public Menu getDetails(long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public long created(Menu menu) {
        menu.setId(IdUtil.getSnowflakeNextId());
        return menuMapper.insert(menu);
    }

    @Override
    public long updated(Menu adminUser) {
        return menuMapper.update(adminUser);
    }

    @Override
    public long deleted(long id, long deleterId) {
        return menuMapper.softDeleteById(id, deleterId);
    }
    
}
