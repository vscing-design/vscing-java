package com.vscing.api.service.impl;

import com.vscing.api.service.TestService;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.supplier.SupplierService;
import com.vscing.common.service.supplier.SupplierServiceFactory;
import com.vscing.model.entity.Show;
import com.vscing.model.mapper.ShowMapper;
import com.vscing.model.request.ShowSeatRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/** 
 * @author vscing (vscing@foxmail.com)
 * @date 2025-01-20 21:18:35
*/
@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private SupplierServiceFactory supplierServiceFactory;

    @Autowired
    private ShowMapper showMapper;

    @Override
    public String getSeat(ShowSeatRequest showSeat) {
        // 获取场次Id参数
        Long showId = showSeat.getShowId();
        Show show = showMapper.selectById(showId);
        if(show == null) {
            return "";
        }
        try {
            // 准备请求参数
            Map<String, String> params = new HashMap<>();
            params.put("showId", show.getTpShowId());
            params.put("addFlag", String.valueOf(showSeat.getAddFlag()));
            SupplierService supplierService = supplierServiceFactory.getSupplierService("jfshou");
            // 发送请求并获取响应
            String responseBody = supplierService.sendRequest("/seat/query", params);
            log.info("responseBody: {}", responseBody);

            return responseBody;

        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
