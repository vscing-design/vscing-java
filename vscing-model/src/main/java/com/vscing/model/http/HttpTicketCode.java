package com.vscing.model.http;

import lombok.Data;
import java.util.List;

/**
 * HttpTicketCode
 *
 * @author vscing
 * @date 2025/1/2 19:10
 */
@Data
public class HttpTicketCode {

  private List<HttpCodeItem> code;

  private String machineInfo;

}
