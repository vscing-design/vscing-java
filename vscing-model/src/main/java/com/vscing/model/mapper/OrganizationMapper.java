package com.vscing.model.mapper;

import com.vscing.model.dto.OrganizationListDto;
import com.vscing.model.entity.Organization;
import com.vscing.model.vo.AdminUserOrganizationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrganizationMapper {

    List<Organization> getList(OrganizationListDto record);

    Organization selectById(long id);

    int insert(Organization record);

    int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

    int update(Organization record);

    List<Organization> getOrganizationsByUserId(long id);

    List<AdminUserOrganizationVo> getOrganizationsByUserIds(List<Long> adminUserIds);

    int deleteOrganizationsByUserId(long id);

    int insertOrganizationsBatch(@Param("id") long id, @Param("organizationIds") List<Long> organizationIds);

}
