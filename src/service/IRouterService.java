package service;

import java.util.Map;
import java.util.Set;

import enums.PaymentGatewayEnum;
import enums.PaymentModeEnum;
import model.Client;
import model.Transaction;

/**
 * Created by Nikhil on 18-Jun-2022
 */
public interface IRouterService extends IClientService {
  public boolean hasClient();

  public Set<PaymentModeEnum> listSupportedPaymodes();

  public boolean addSupportForPaymode(PaymentModeEnum paymentModeEnum);

  public boolean addClient(Client client, Set<PaymentModeEnum> paymentModeEnums);

  public Map<PaymentGatewayEnum, Integer> showDistribution();

  public Map<PaymentModeEnum, Map<PaymentGatewayEnum, Integer>> showPaymentModewiseDistribution();

  public boolean reconfigureDistribution();

  public boolean transact(Transaction transaction, Client client);

  public boolean changeRoutingConfig(Map<PaymentGatewayEnum, Integer> routingConfigMap);

  /**
   *
   */
  void showTransactionMetrics();

}
