package com.vscing.model.http;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * HttpData
 *
 * @author vscing
 * @date 2024/12/25 20:42
 */
@Getter
@Setter
public class HttpData {

  private String cityName;
  private Long cityId;
  private String firstLetter;
  private Long isHot;
  private String provinceName;
  private List<HttpRegions> regions;
  private String cityCode;

}
