package com.vscing.admin.service;

/**
 * TaskService
 *
 * @author vscing
 * @date 2024/12/26 23:38
 */
public interface TaskService {

  void syncTable();

  void syncAddress();

  void syncCinema();

  void syncMovie();

  void syncShow();

  void syncPendingPaymentOrder();

  void syncPendingTicketOrder();

}
