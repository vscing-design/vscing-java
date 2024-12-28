package com.vscing.admin.service;

import com.vscing.model.entity.Show;
import com.vscing.model.entity.ShowArea;

import java.util.List;

/**
 * ShowService
 *
 * @author vscing
 * @date 2024/12/28 19:21
 */
public interface ShowService {

  boolean initShow(Show show, List<ShowArea> showAreaList);

}
