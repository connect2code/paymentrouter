package database;

import java.util.Map;
import java.util.Set;

import enums.PaymentGatewayEnum;
import enums.PaymentModeEnum;
import model.Client;
import model.Transaction;
import pg.IPGService;

/**
 * Created by Nikhil on 18-Jun-2022
 */
public class Database {
  public static Set<PaymentModeEnum> paymentModeMaster;
  public static Map<PaymentGatewayEnum, IPGService> pgServiceMap;
  public static Map<Client, Set<PaymentModeEnum>> clientPaymentModeMap;
  public static Map<PaymentGatewayEnum, Integer> pgRoutingConfigMap;
  public static Map<PaymentModeEnum, Map<PaymentGatewayEnum, Integer>> paymentModeRoutingConfigMap;
  public static Map<Transaction, Client> txnClientMap;
  public static Map<Transaction, PaymentModeEnum> txnPaymentModeEnum;
  public static Map<Transaction, PaymentGatewayEnum> txnPGMap;
  public static Map<PaymentGatewayEnum, Set<Transaction>> pgTxnMap;
  public static Map<PaymentGatewayEnum, Set<Transaction>> pgTxnSuccessMap;
  public static Map<PaymentGatewayEnum, Set<Transaction>> pgTxnFailureMap;
  public static Map<Client, Set<Transaction>> clientTxnMap;
  public static Map<PaymentModeEnum, Set<Transaction>> paymentModeTxnMap;

}
