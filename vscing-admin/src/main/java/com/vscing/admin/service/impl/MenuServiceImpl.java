package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.MenuService;
import com.vscing.common.utils.MapstructUtils;
import com.vscing.model.dto.MenuListDto;
import com.vscing.model.mapper.MenuMapper;
import com.vscing.model.entity.Menu;
import com.vscing.model.vo.MenuTreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

  @Autowired
  private MenuMapper menuMapper;

  @Override
  public List<MenuTreeVo> getAllList(MenuListDto record) {
    List<Menu> menuList = menuMapper.getList(record);
    Map<Long, MenuTreeVo> menuMap = new HashMap<>();
    List<MenuTreeVo> tree = new ArrayList<>();

    // 将Menu实体转换为MenuTreeVo对象，并建立映射关系
    for (Menu menu : menuList) {
      MenuTreeVo menuVo = MapstructUtils.convert(menu, MenuTreeVo.class);
      menuVo.setChildren(new ArrayList<>());
      menuMap.put(menu.getId(), menuVo);
    }

    // 构建树形结构
    for (Menu menu : menuList) {
      if (menu.getParentId() == null || menu.getParentId() == 0) {
        // 根菜单
        tree.add(menuMap.get(menu.getId()));
      } else {
        MenuTreeVo parent = menuMap.get(menu.getParentId());
        if (parent != null) {
//          if(parent.getChildren() == null) {
//            parent.setChildren(new ArrayList<>());
//          }
          // 将当前菜单添加到父菜单的children中
          parent.getChildren().add(menuMap.get(menu.getId()));
        }
      }
    }
    return tree;
  }

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
  public long updated(Menu menu) {
    return menuMapper.update(menu);
  }

  @Override
  public long deleted(long id, long deleterId) {
    return menuMapper.softDeleteById(id, deleterId);
  }

}
