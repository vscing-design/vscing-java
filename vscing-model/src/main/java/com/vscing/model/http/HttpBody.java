package com.vscing.model.http;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Body
 *
 * @author vscing
 * @date 2024/12/25 20:41
 */
@Getter
@Setter
public class HttpBody {

  private Long code;
  private String message;
  private List<HttpData> data;

}
