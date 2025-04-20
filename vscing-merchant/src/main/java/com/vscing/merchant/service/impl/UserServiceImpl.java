package com.vscing.merchant.service.impl;

import com.vscing.auth.service.VscingUserDetails;
import com.vscing.auth.util.JwtTokenUtil;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.RedisService;
import com.vscing.common.utils.MapstructUtils;
import com.vscing.common.utils.RequestUtil;
import com.vscing.merchant.po.impl.MerchantDetailsImpl;
import com.vscing.merchant.service.UserService;
import com.vscing.model.entity.Merchant;
import com.vscing.model.mapper.MerchantMapper;
import com.vscing.model.vo.MerchantDetailVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * UserServiceImpl
 *
 * @author vscing
 * @date 2025/4/20 21:37
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

  @Value("${jwt.expiration}")
  private Long expiration;

  @Value("${jwt.cachePrefix}")
  private String cachePrefix;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private RedisService redisService;

  @Autowired
  private MerchantMapper merchantMapper;

  @Override
  public String login(String username, String password, HttpServletRequest request) {
    String token = null;
    //密码需要客户端加密后传递
    try {
      // 获取用户信息
      Merchant merchant = merchantMapper.selectByMerchantName(username);
      if (merchant == null) {
        throw new ServiceException("商户不存在");
      }
      if (!passwordEncoder.matches(password, merchant.getPassword())){
        throw new ServiceException("密码不正确");
      }
      // 获取用户信息
      MerchantDetailVo merchantDetailVo = MapstructUtils.convert(merchant, MerchantDetailVo.class);
      VscingUserDetails userDetails = new MerchantDetailsImpl(merchantDetailVo);
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      token = jwtTokenUtil.generateToken(userDetails.getUserId());

      // 更新最后登录信息
      merchant.setLastIp(RequestUtil.getRequestIp(request));
      merchant.setLoginAt(LocalDateTime.now());

      // 记录到redis
      redisService.set(cachePrefix + merchant.getId() + ":" + token, merchant, expiration);
      // 更新表数据
      merchantMapper.update(merchant);
    } catch (AuthenticationException e) {
      log.warn("登录异常:{}", e.getMessage());
    }
    return token;
  }

  @Override
  public boolean logout(MerchantDetailVo merchantDetailVo, String authToken) {
    // 删除缓存
    String redisKey = cachePrefix + merchantDetailVo.getId() + ":" + authToken;
    return redisService.del(redisKey);
  }

  @Override
  public int updated(Merchant merchant) {
    return merchantMapper.update(merchant);
  }


}
