package com.vscing.common.service.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * PaymentServiceFactory
 *
 * @author vscing
 * @date 2024/12/26 23:01
 */
@Component
public class SupplierServiceFactory {

  public static final String JFSHOU = "jfshou";

  public static final String LAIYIPIAO = "laiyipiao";

  private final Map<String, SupplierService> supplierServices;

  @Autowired
  public SupplierServiceFactory(Map<String, SupplierService> supplierServices) {
    this.supplierServices = supplierServices;
  }

  public SupplierService getSupplierService(String type) {
    if (JFSHOU.equalsIgnoreCase(type)) {
      return this.supplierServices.get("jfshouSupplierService");
    } else if (LAIYIPIAO.equalsIgnoreCase(type)) {
      return this.supplierServices.get("laiyipiaoSupplierService");
    } else {
      throw new IllegalArgumentException("Unknown payment type: " + type);
    }
  }

}


