package com.vscing.model.struct;

import com.vscing.model.entity.AdminUser;
import com.vscing.model.vo.AdminUserDetailVo;
import com.vscing.model.vo.AdminUserListVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * AdminUserMapper
 *
 * @author vscing
 * @date 2024/12/19 22:30
 */
@Mapper
public interface AdminUserMapper {

  AdminUserMapper INSTANCE = Mappers.getMapper(AdminUserMapper.class);

//  @Mapping(source = "id", target = "id")
//  @Mapping(source = "loginAt", target = "loginAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
  AdminUserListVo adminUserToAdminUserListVo(AdminUser adminUser);

  List<AdminUserListVo> adminUserToAdminUserListVos(List<AdminUser> adminUsers);

  @Mapping(target = "relatedRole", ignore = true)
  @Mapping(target = "relatedMenu", ignore = true)
  AdminUserDetailVo adminUserToAdminUserDetailVo(AdminUser adminUser);


}
