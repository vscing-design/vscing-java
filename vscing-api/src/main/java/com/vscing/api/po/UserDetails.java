package com.vscing.api.po;

import com.vscing.auth.service.VscingUserDetails;
import com.vscing.model.vo.UserDetailVo;

/**
 * UserDetails
 *
 * @author vscing
 * @date 2025/1/6 11:52
 */
public interface UserDetails extends VscingUserDetails {

  UserDetailVo getUser();

}
