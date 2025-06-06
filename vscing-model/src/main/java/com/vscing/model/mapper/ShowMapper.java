package com.vscing.model.mapper;

import com.vscing.model.dto.CinemaApiListDto;
import com.vscing.model.dto.MovieApiCinemaDto;
import com.vscing.model.dto.MovieApiListDto;
import com.vscing.model.dto.ShowAllDto;
import com.vscing.model.dto.ShowListDto;
import com.vscing.model.entity.Show;
import com.vscing.model.platform.QueryShow;
import com.vscing.model.platform.QueryShowDto;
import com.vscing.model.vo.CinemaApiDetailsShowVo;
import com.vscing.model.vo.CinemaApiVo;
import com.vscing.model.vo.MovieApiVo;
import com.vscing.model.vo.OrderApiConfirmDetailsVo;
import com.vscing.model.vo.ShowListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MovieShowMapper
 *
 * @author vscing
 * @date 2024/12/27 22:17
 */
@Mapper
public interface ShowMapper {

  List<CinemaApiVo> selectByMovieApiCinema(MovieApiCinemaDto record);

  List<CinemaApiVo> selectByCinemaApiList(CinemaApiListDto record);

  List<MovieApiVo> selectByMovieApiList(MovieApiListDto record);

  List<ShowListVo> getListByCinemaIdAndSupplierId(ShowAllDto record);

  List<CinemaApiDetailsShowVo> selectByCinemaId(@Param("cinemaId") long cinemaId);

  List<ShowListVo> getList(ShowListDto record);

  Show selectById(long id);

  Show selectByTpShowId(String tpShowId);

  OrderApiConfirmDetailsVo selectByOrderDetails(long id);

  int insert(Show record);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(Show record);

  void truncateTable();

  /**
   * 批量新增或更新。INSERT ... ON DUPLICATE KEY UPDATE 语句来实现 UPSERT 操作
   */
  int batchUpsert(@Param("list") List<Show> list);

  /**
   * 开放平台查询列表
   */
  List<QueryShow> getPlatformList(QueryShowDto record);

  /**
   * 开放平台查询详情
   */
  Show getPlatformInfo(long id);

}
