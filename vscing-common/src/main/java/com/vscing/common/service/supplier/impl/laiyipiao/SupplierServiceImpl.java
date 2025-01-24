package com.vscing.common.service.supplier.impl.laiyipiao;

import com.vscing.common.service.supplier.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * SupplierServiceImpl
 *
 * @author vscing
 * @date 2025/1/7 16:04
 */
@Slf4j
@Service("laiyipiaoSupplierService")
public class SupplierServiceImpl implements SupplierService {

  @Override
  public String sendRequest(String url, Map<String, String> params) throws IOException {
    return null;
  }

}
