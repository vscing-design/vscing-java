package com.vscing.common.service.supplier;

import java.io.IOException;
import java.util.Map;

/**
 * Supplier
 * 电影票供应商封装
 * @date 2024/12/10 23:52
 * @auth vscing(vscing @ foxmail.com)
 */
public interface SupplierService {

  String sendRequest(String url, Map<String, String> params) throws IOException;

}
