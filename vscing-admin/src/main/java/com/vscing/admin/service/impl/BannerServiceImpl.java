package com.vscing.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.BannerService;
import com.vscing.model.dto.BannerListDto;
import com.vscing.model.entity.Banner;
import com.vscing.model.mapper.BannerMapper;
import com.vscing.model.vo.BannerListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BannerService
 *
 * @author vscing
 * @date 2024/12/14 21:08
 */
@Slf4j
@Service
public class BannerServiceImpl implements BannerService {

  @Autowired
  private BannerMapper bannerMapper;

  @Override
  public List<BannerListVo> getList(BannerListDto record, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return bannerMapper.getList(record);
  }

  @Override
  public int created(Banner banner) {
    banner.setId(IdUtil.getSnowflakeNextId());
    return bannerMapper.insert(banner);
  }

  @Override
  public int updated(Banner banner) {
    return bannerMapper.update(banner);
  }
}
