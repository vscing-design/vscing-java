package com.vscing.admin.service.impl;

import com.vscing.admin.service.AddressService;
import com.vscing.model.vo.ProvinceVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * AddressServiceImpl
 *
 * @author vscing
 * @date 2024/12/23 00:34
 */
@Service
public class AddressServiceImpl implements AddressService {

  @Override
  public List<ProvinceVo> getList() {
    List<ProvinceVo> list = new ArrayList<>();
    return list;
  }

}
