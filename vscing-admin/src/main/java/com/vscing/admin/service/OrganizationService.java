package com.vscing.admin.service;

import com.vscing.model.dto.OrganizationListDto;
import com.vscing.model.entity.Organization;
import com.vscing.model.vo.OrganizationTreeVo;

import java.util.List;

/**
 * OrganizationService
 *
 * @author vscing
 * @date 2024/12/23 23:02
 */
public interface OrganizationService {

  /**
   * 列表
   */
  List<OrganizationTreeVo> getAllList(OrganizationListDto record);

  /**
   * 列表
   */
  List<Organization> getList(OrganizationListDto record, Integer pageSize, Integer pageNum);

  /**
   * 详情
   */
  Organization getDetails(long id);

  /**
   * 新增
   */
  long created(Organization Organization);

  /**
   * 编辑
   */
  long updated(Organization Organization);

  /**
   * 删除
   */
  long deleted(long id, long deleterId);
  
}
