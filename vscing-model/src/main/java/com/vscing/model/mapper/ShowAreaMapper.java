package com.vscing.model.mapper;

import com.vscing.model.dto.MovieShowAreaListDto;
import com.vscing.model.entity.ShowArea;
import com.vscing.model.vo.MinPriceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MovieShowAreaMapper
 *
 * @author vscing
 * @date 2024/12/27 22:28
 */
@Mapper
public interface ShowAreaMapper {

  List<ShowArea> getList(MovieShowAreaListDto record);

  ShowArea selectById(long id);

  int insert(ShowArea record);

  int batchInsert(@Param("list") List<ShowArea> list);

  /**
   * 批量新增或更新。INSERT ... ON DUPLICATE KEY UPDATE 语句来实现 UPSERT 操作
   */
  int batchUpsert(@Param("list") List<ShowArea> list);

  int softDeleteById(@Param("id") long id, @Param("deleterId") long deleterId);

  int update(ShowArea record);

  List<ShowArea> selectByShowIds(@Param("list") List<Long> showIds);

  List<ShowArea> selectByShowIdAreas(@Param("showId") Long showId, @Param("areas") List<String> areas);

  List<MinPriceVo> getMinPriceByMovieIds(@Param("list") List<Long> movieIds);

  List<MinPriceVo> getMinPriceByCinemaIds(@Param("list") List<Long> cinemaIds);

  void truncateTable();

}
