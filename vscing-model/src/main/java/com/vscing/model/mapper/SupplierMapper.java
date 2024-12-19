package com.vscing.model.mapper;

import com.vscing.model.dto.SupplierListDto;
import com.vscing.model.entity.Supplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SupplierMapper
 *
 * @author vscing
 * @date 2024/12/20 01:10
 */
@Mapper
public interface SupplierMapper {

  List<Supplier> getList(SupplierListDto record);

  Supplier selectById(long id);

  int insert(Supplier record);

  int update(Supplier record);

  int softDeleteById(long id);

}
