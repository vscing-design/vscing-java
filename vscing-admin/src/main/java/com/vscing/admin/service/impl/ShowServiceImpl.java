package com.vscing.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.vscing.admin.service.ShowService;
import com.vscing.common.exception.ServiceException;
import com.vscing.model.dto.ShowListDto;
import com.vscing.model.entity.Show;
import com.vscing.model.entity.ShowArea;
import com.vscing.model.mapper.ShowAreaMapper;
import com.vscing.model.mapper.ShowMapper;
import com.vscing.model.vo.ShowListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ShowServiceImpl
 *
 * @author vscing
 * @date 2024/12/28 19:22
 */
@Service
public class ShowServiceImpl implements ShowService {

  @Autowired
  private ShowMapper showMapper;

  @Autowired
  private ShowAreaMapper showAreaMapper;
  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public boolean initShow(Show show, List<ShowArea> showAreaList) {
    try {

      // 创建
      int rowsAffected = showMapper.insert(show);
      if (rowsAffected <= 0) {
        throw new ServiceException("新增失败");
      }
      // 增加机构
      rowsAffected = showAreaMapper.batchInsert(showAreaList);
      if (rowsAffected != showAreaList.size()) {
        throw new ServiceException("新增关联失败");
      }
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
    return true;
  }

  @Override
  public List<ShowListVo> getManageList(ShowListDto data, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return showMapper.getList(data);
  }

}
