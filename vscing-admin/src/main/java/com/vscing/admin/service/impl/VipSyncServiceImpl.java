package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vscing.admin.service.UploadService;
import com.vscing.admin.service.VipSyncService;
import com.vscing.common.service.supplier.SupplierService;
import com.vscing.common.service.supplier.SupplierServiceFactory;
import com.vscing.common.utils.JsonUtils;
import com.vscing.model.entity.VipGoods;
import com.vscing.model.entity.VipGoodsAttach;
import com.vscing.model.entity.VipGroup;
import com.vscing.model.mapper.VipGoodsAttachMapper;
import com.vscing.model.mapper.VipGoodsMapper;
import com.vscing.model.mapper.VipGroupMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * VipSyncServiceImpl
 *
 * @author vscing
 * @date 2025/6/1 16:44
 */
@Slf4j
@Service
public class VipSyncServiceImpl implements VipSyncService {

  @Autowired
  private SupplierServiceFactory supplierServiceFactory;

  @Autowired
  private VipGroupMapper vipGroupMapper;

  @Autowired
  private VipGoodsMapper vipGoodsMapper;

  @Autowired
  private VipGoodsAttachMapper vipGoodsAttachMapper;

  @Autowired
  UploadService uploadService;

  private Long supplierId = 1929123419432062976L;

  @Async("threadPoolTaskExecutor")
  @Override
  public void queryGroup() {
    try {
      // 准备请求参数
      Map<String, String> params = new HashMap<>();

      SupplierService supplierService = supplierServiceFactory.getSupplierService("kky");
      // 发送请求并获取响应
      String responseBody = supplierService.sendRequest("/dockapiv3/goods/group", params);
      // 将 JSON 字符串解析为 JsonNode 对象
      ObjectMapper objectMapper = JsonUtils.getObjectMapper();
      // 解析 JSON 数据到 Map
      Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
      int code = (int) responseMap.get("code");
      List<Map<String, Object>> dataList = (List<Map<String, Object>>) responseMap.get("data");
      // 判断数据
      if (code != 1 || dataList == null || dataList.isEmpty()) {
        log.info("vip同步分组结果异常: {}", responseBody);
        return;
      }
      // 批量数据源
      List<VipGroup> vipGroupList = new ArrayList<>();
      // 循环遍历数据
      for (Map<String, Object> data : dataList) {
        // 判断数据是否存在
        VipGroup oldVipGroup = vipGroupMapper.selectByTpGroupId(objectMapper.convertValue(data.get("groupid"), Long.class));
        if(oldVipGroup != null) {
          continue;
        }
        VipGroup vipGroup = new VipGroup();
        vipGroup.setId(IdUtil.getSnowflakeNextId());
        vipGroup.setSupplierId(supplierId);
        // 转义三方数据
        vipGroup.setTpGroupId(objectMapper.convertValue(data.get("groupid"), Long.class));
        vipGroup.setGroupName(objectMapper.convertValue(data.get("groupname"), String.class));
        vipGroup.setGroupAlias(objectMapper.convertValue(data.get("groupaliasname"), String.class));
        String groupLogo = objectMapper.convertValue(data.get("groupimgurl"), String.class);
        if(groupLogo != null && !groupLogo.isEmpty()){
          try {
            groupLogo = uploadService.put(groupLogo, "kky");
          } catch (Exception e) {
            log.error("上传图片地址：{}，异常信息：{}", groupLogo, e.getMessage());
          }
        }
        vipGroup.setGroupLogo(groupLogo);
        vipGroup.setBrandId(objectMapper.convertValue(data.get("brandid"), Long.class));
        vipGroup.setBrandName(objectMapper.convertValue(data.get("brandname"), String.class));
        String brandLogo = objectMapper.convertValue(data.get("brandimgurl"), String.class);
        if(brandLogo != null && !brandLogo.isEmpty()){
          try {
            brandLogo = uploadService.put(brandLogo, "kky");
          } catch (Exception e) {
            log.error("上传图片地址：{}，异常信息：{}", brandLogo, e.getMessage());
          }
        }
        vipGroup.setBrandLogo(brandLogo);
        vipGroup.setCreatedBy(0L);
        vipGroupList.add(vipGroup);
      }
      vipGroupMapper.batchInsert(vipGroupList);
      log.info("vip同步分组结束");
    } catch (Exception e) {
      log.error("vip同步分组失败", e);
    }
  }

  @Override
  public void queryGoods(int page) {
    try {
      // 准备请求参数
      Map<String, String> params = new HashMap<>();
      params.put("page", String.valueOf(page));
      params.put("limit", String.valueOf(50));
      SupplierService supplierService = supplierServiceFactory.getSupplierService("kky");
      // 发送请求并获取响应
      String responseBody = supplierService.sendRequest("/dockapiv3/goods/all", params);
      // 将 JSON 字符串解析为 JsonNode 对象
      ObjectMapper objectMapper = JsonUtils.getObjectMapper();
      // 解析 JSON 数据到 Map
      Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
      int code = (int) responseMap.get("code");
      int maxPage = (int) responseMap.get("allpage");
      List<Map<String, Object>> dataList = (List<Map<String, Object>>) responseMap.get("data");
      // 判断数据
      if (code != 1 || dataList == null || dataList.isEmpty()) {
        log.info("vip同步商品结果异常: {}", responseBody);
        return;
      }
      // 批量数据源
      List<VipGoods> vipGoodsList = new ArrayList<>();
      List<VipGoodsAttach> vipGoodsAttachList = new ArrayList<>();
      // 循环遍历数据
      for (Map<String, Object> data : dataList) {
        VipGoods vipGoods = new VipGoods();
        // 获取分组信息
        Long tpGroupId = objectMapper.convertValue(data.get("groupid"), Long.class);
        VipGroup oldVipGroup = vipGroupMapper.selectByTpGroupId(tpGroupId);
        // 判断数据是否存在
        Long tpGoodsId = objectMapper.convertValue(data.get("goodsid"), Long.class);
        VipGoods oldVipGoods = vipGoodsMapper.selectByTpGoodsId(tpGoodsId);
        Long goodsId = IdUtil.getSnowflakeNextId();
        if(oldVipGoods == null) {
          vipGoods.setId(goodsId);
          String goodsLogo = objectMapper.convertValue(data.get("imgurl"), String.class);
          if(goodsLogo != null && !goodsLogo.isEmpty()){
            try {
              goodsLogo = uploadService.put(goodsLogo, "kky");
            } catch (Exception e) {
              log.error("上传图片地址：{}，异常信息：{}", goodsLogo, e.getMessage());
            }
          }
          vipGoods.setGoodsLogo(goodsLogo);
          // 属性
          TypeReference<List<Map<String, Object>>> typeReference = new TypeReference<List<Map<String, Object>>>() {};
          List<Map<String, Object>> attachList = objectMapper.convertValue(data.get("attach"), typeReference);
          for (Map<String, Object> attach : attachList) {
            VipGoodsAttach vipGoodsAttach = new VipGoodsAttach();
            vipGoodsAttach.setId(IdUtil.getSnowflakeNextId());
            vipGoodsAttach.setVipGoodsId(goodsId);
            vipGoodsAttach.setTitle(objectMapper.convertValue(attach.get("title"), String.class));
            vipGoodsAttach.setTip(objectMapper.convertValue(attach.get("tip"), String.class));
            vipGoodsAttachList.add(vipGoodsAttach);
          }
        } else {
          vipGoods.setId(oldVipGroup.getId());
          vipGoods.setOldGoodsPrice(oldVipGoods.getGoodsPrice());
          vipGoods.setOldMarketPrice(oldVipGoods.getMarketPrice());
          vipGoods.setGoodsLogo(oldVipGoods.getGoodsLogo());
        }
        vipGoods.setSupplierId(supplierId);
        vipGoods.setTpGoodsId(tpGoodsId);
        vipGoods.setTpGroupId(tpGroupId);
        vipGoods.setGroupId(oldVipGroup.getId());
        vipGoods.setGoodsName(objectMapper.convertValue(data.get("goodsname"), String.class));
        vipGoods.setStock(objectMapper.convertValue(data.get("stock"), Integer.class));
        vipGoods.setGoodsPrice(objectMapper.convertValue(data.get("goodsprice"), BigDecimal.class));
        vipGoods.setMarketPrice(objectMapper.convertValue(data.get("marketprice"), BigDecimal.class));
        Integer goodsType = objectMapper.convertValue(data.get("goodstype"), Integer.class);
        vipGoods.setGoodsType(goodsType == 1 ? 1 : 2);
        Integer goodsStatus = objectMapper.convertValue(data.get("goodsstatus"), Integer.class);
        vipGoods.setGoodsStatus(goodsStatus == 1 ? 1 : 2);
        vipGoodsList.add(vipGoods);
      }
      vipGoodsMapper.batchInsert(vipGoodsList);
      vipGoodsAttachMapper.batchInsert(vipGoodsAttachList);
      log.info("vip同步商品结束");
      Thread.sleep(4500);
      if(maxPage > page) {
        log.info("下一页vip同步商品");
        this.queryGoods(page + 1);
      }
    } catch (Exception e) {
      log.error("vip同步商品失败", e);
    }
  }
}
