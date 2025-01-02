package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.OrganizationService;
import com.vscing.common.utils.MapstructUtils;
import com.vscing.model.dto.OrganizationListDto;
import com.vscing.model.entity.Organization;
import com.vscing.model.mapper.OrganizationMapper;
import com.vscing.model.vo.OrganizationTreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OrganizationServiceImpl
 *
 * @author vscing
 * @date 2024/12/23 23:04
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

  @Autowired
  private OrganizationMapper organizationMapper;

  @Override
  public List<OrganizationTreeVo> getAllList(OrganizationListDto record) {
    List<Organization> organizationList = organizationMapper.getList(record);
    Map<Long, OrganizationTreeVo> organizationMap = new HashMap<>();
    List<OrganizationTreeVo> tree = new ArrayList<>();

    // 将Organization实体转换为OrganizationTreeVo对象，并建立映射关系
    for (Organization organization : organizationList) {
      OrganizationTreeVo organizationVo = MapstructUtils.convert(organization, OrganizationTreeVo.class);
      organizationVo.setChildren(new ArrayList<>());
      organizationMap.put(organization.getId(), organizationVo);
    }

    // 构建树形结构
    for (Organization organization : organizationList) {
      if (organization.getParentId() == null || organization.getParentId() == 0) {
        // 根菜单
        tree.add(organizationMap.get(organization.getId()));
      } else {
        OrganizationTreeVo parent = organizationMap.get(organization.getParentId());
        // 将当前菜单添加到父菜单的children中
        if (parent != null) {
//          if(parent.getChildren() == null) {
//            parent.setChildren(new ArrayList<>());
//          }
          parent.getChildren().add(organizationMap.get(organization.getId()));
        }
      }
    }
    return tree;
  }


  @Override
  public List<Organization> getList(OrganizationListDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return organizationMapper.getList(record);
  }

  @Override
  public Organization getDetails(long id) {
    return organizationMapper.selectById(id);
  }

  @Override
  public long created(Organization organization) {
    organization.setId(IdUtil.getSnowflakeNextId());
    return organizationMapper.insert(organization);
  }

  @Override
  public long updated(Organization organization) {
    return organizationMapper.update(organization);
  }

  @Override
  public long deleted(long id, long deleterId) {
    return organizationMapper.softDeleteById(id, deleterId);
  }

}
