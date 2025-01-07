package com.vscing.model.http;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * HttpOrder
 *
 * @author vscing
 * @date 2025/1/2 18:39
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HttpOrder {

  private String orderNo;

  private String tradeNo;

  private String orderTime;

  private String phoneNumber;

  private Integer amount;

  private String seatInfos;

  private String cinemaName;

  private String cinemaAddress;

  private String hallName;

  private String movieName;

  private String showVersionType;

  private String showTime;

  private Integer duration;

  private String orderStatus;

  private String posterUrl;

  private List<HttpTicketCode> ticketCode;

}
