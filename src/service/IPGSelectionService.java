package service;

import java.util.Map;

import enums.PaymentGatewayEnum;
import enums.PaymentModeEnum;

/**
 * Created by Nikhil on 23-Jun-2022
 */
public interface IPGSelectionService {
  public PaymentGatewayEnum select();

  public PaymentGatewayEnum select(PaymentModeEnum paymentModeEnum);

  public Map<PaymentGatewayEnum, Integer> getDistribution();

  public Map<PaymentModeEnum, Map<PaymentGatewayEnum, Integer>> getPaymentModewiseDistribution();

  public boolean reconfigureDistribution();

}
