package model;

import java.util.UUID;

import enums.PaymentModeEnum;

/**
 * Created by Nikhil on 18-Jun-2022
 */
public class Transaction {
  private String id;
  private PaymentModeEnum paymentMode;
  private Double amount;
  private Boolean status;

  /**
   *
   */
  public Transaction(PaymentModeEnum paymentModeEnum, Double amount) {
    paymentMode = paymentModeEnum;
    this.amount = amount;
    status = null;
    id = UUID.randomUUID().toString();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public PaymentModeEnum getPaymentMode() {
    return paymentMode;
  }

  public void setPaymentMode(PaymentModeEnum paymentMode) {
    this.paymentMode = paymentMode;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public boolean getStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Transaction [id=" + id + ", paymentMode=" + paymentMode + ", amount=" + amount
        + ", status=" + status + "]";
  }



}
