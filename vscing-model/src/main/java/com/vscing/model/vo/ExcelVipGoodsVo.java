package com.vscing.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * ExcelVipGoodsVo
 *
 * @author vscing
 * @date 2025/7/1 23:09
 */
@Data
public class ExcelVipGoodsVo {

//  增加导出功能，可导出所有的会员商品信息（根据搜索条件导出所有页的数据；如无搜索条件，则默认导出全部）；
//  导出的表头为 商品、商品名称、库存、供货价-最新、供货价-上一次、市场价格、商品状态、商品类型、商品详情

  @ExcelProperty("自定义商品ID")
  @Schema(description = "自定义商品ID")
  private Long id;

  @ExcelProperty("商品名称")
  @Schema(description = "商品名称")
  private String goodsName;

  @ExcelProperty("库存")
  @Schema(description = "库存")
  private Integer stock;

  @ExcelProperty("商品单价")
  @Schema(description = "商品单价 元")
  private BigDecimal goodsPrice;

  @ExcelProperty("旧商品单价")
  @Schema(description = "旧商品单价 元")
  private BigDecimal oldGoodsPrice;

  @ExcelProperty("市场价格")
  @Schema(description = "市场价格 元")
  private BigDecimal marketPrice;

  @ExcelProperty("商品状态 1 出售 2 下架")
  @Schema(description = "商品状态 1 出售 2 下架")
  private Integer goodsStatus;

  @ExcelProperty("商品类型 1 卡密 2 直冲")
  @Schema(description = "商品类型 1 卡密 2 直冲")
  private Integer goodsType;

  @ExcelProperty("商品详情")
  @Schema(description = "商品详情")
  private String details;

}
