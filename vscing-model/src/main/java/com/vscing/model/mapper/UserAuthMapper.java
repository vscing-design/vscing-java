package com.vscing.model.mapper;

import com.vscing.model.entity.UserAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * UserAuthMapper
 *
 * @author vscing
 * @date 2025/1/10 13:32
 */
@Mapper
public interface UserAuthMapper {

  UserAuth selectUserIdByOpenidAndUuid(@Param("platform") Integer platform,
                                       @Param("openid") String openid,
                                       @Param("uuid") String uuid);

  int updateLoginInfoById(@Param("id") Long id,
                          @Param("lastIp") String lastIp,
                          @Param("loginAt") LocalDateTime loginAt);

  int updateUserId(@Param("newUserId") Long newUserId, @Param("oldUserId") Long oldUserId);

  int insert(UserAuth record);

}
