package com.vscing.common.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JwtTokenUtil
 * @author vscing (vscing@foxmail.com)
 * @date 2024-12-14 18:11:47
*/
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();
    private static final String CLAIM_KEY_USERID = "vscingId";
    private static final String CLAIM_KEY_CREATED = "vscingCreated";

    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

//    public static void main(String[] args) {
//        JwtTokenUtil util = new JwtTokenUtil();
//
//        String jwtToken = util.generateToken(1L);
//
//        System.out.println(jwtToken);
//
//
//        Boolean isVerify = util.validateToken(jwtToken, 1L);
//        System.out.println(isVerify);
//    }

    /**
     * 根据负责生成JWT的token
     */
    private String generateToken(Map<String, Object> claims) {
        Date now = new Date();
        return Jwts.builder()
            .issuer("vscing")
            .issuedAt(now)
            .claims(claims)
            .expiration(generateExpirationDate())
            .signWith(secretKey)
            .id(UUID.randomUUID().toString())
            .compact();
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (Exception e) {
            LOGGER.info("JWT格式验证失败:{}", token);
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从token中获取登录用户ID
     */
    public Long getUserIdFromToken(String token) {
        long userId;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = claims.get(CLAIM_KEY_USERID, Long.class);
        } catch (Exception e) {
            userId = 0;
        }
        return userId;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param id          从数据库中查询出来的用户id
     */
    public boolean validateToken(String token, Long id) {
        Long userId = getUserIdFromToken(token);
        return userId.equals(id) && !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(Long id) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(CLAIM_KEY_USERID, id);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 当原来的token没过期时是可以刷新的
     *
     * @param oldToken 带tokenHead的token
     */
    public String refreshHeadToken(String oldToken) {
        if(StrUtil.isEmpty(oldToken)){
            return null;
        }
        String token = oldToken.substring(tokenHead.length());
        if(StrUtil.isEmpty(token)){
            return null;
        }
        //token校验不通过
        Claims claims = getClaimsFromToken(token);
        if(claims==null){
            return null;
        }
        //如果token已经过期，不支持刷新
        if(isTokenExpired(token)){
            return null;
        }
        //如果token在30分钟之内刚刷新过，返回原token
        if(tokenRefreshJustBefore(token,30*60)){
            return token;
        }else{
            claims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(claims);
        }
    }

    /**
     * 判断token在指定时间内是否刚刚刷新过
     * @param token 原token
     * @param time 指定时间（秒）
     */
    private boolean tokenRefreshJustBefore(String token, int time) {
        Claims claims = getClaimsFromToken(token);
        Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
        Date refreshDate = new Date();
        //刷新时间在创建时间的指定时间内
        if(refreshDate.after(created)&&refreshDate.before(DateUtil.offsetSecond(created,time))){
            return true;
        }
        return false;
    }
}
