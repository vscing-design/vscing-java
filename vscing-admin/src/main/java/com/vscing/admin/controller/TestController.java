package com.vscing.admin.controller;

import com.vscing.admin.vo.UserVo;
import com.vscing.common.api.CommonResult;
import com.vscing.common.util.SignatureGenerator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * TestController
 *
 * @date 2024/12/11 00:51
 * @auth vscing(vscing @ foxmail.com)
 */

@RestController
@RequestMapping("/v1/test")
public class TestController {

    // https://docs.qq.com/doc/DWXVlWmtHbnpLUXdT
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<Object> listAll() {
        // 请求参数
        Map<String, String> params = new LinkedHashMap<>();
        params.put("userNo", "18888888888");
        params.put("cityId", "2");
        params.put("timestamp", "1633017600000");

        // 秘钥
        String key = "afdaffda1232";

        // 生成签名
        String sign = SignatureGenerator.generateSignature(params, key);

        // 构建最终URL
        String url = "https://ot.jfshou.cn/ticket/ticket_api/cinema/query?" + SignatureGenerator.buildQuery(params) + "&sign=" + sign;
        return CommonResult.success(url);
    }
}
