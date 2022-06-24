package service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import database.Database;
import enums.PaymentGatewayEnum;
import enums.PaymentModeEnum;

/**
 * Created by Nikhil on 23-Jun-2022
 */
public class PGSelectionService implements IPGSelectionService {

  /**
   *
   */
  public PGSelectionService() {

    Database.paymentModeRoutingConfigMap = new HashMap<>();
    Database.pgRoutingConfigMap = new HashMap<>();
    Random rand = new Random();
    for (PaymentModeEnum paymentModeEnum : PaymentModeEnum.values()) {
      Map<PaymentGatewayEnum, Integer> paymentGatewayRoutingMap = new HashMap<>();
      int paymentModecount = 100;
      for (int i = 0; i < (PaymentGatewayEnum.values().length - 1); i++) {
        int percentage = rand.nextInt(paymentModecount + 1);
        PaymentGatewayEnum paymentGatewayEnum = PaymentGatewayEnum.values()[i];
        paymentGatewayRoutingMap.put(paymentGatewayEnum, percentage);
        paymentModecount -= percentage;
        int pgRoutingPercentage = Database.pgRoutingConfigMap.getOrDefault(paymentGatewayEnum, 0);
        if (pgRoutingPercentage == 0) {
          pgRoutingPercentage = percentage;
        } else {
          pgRoutingPercentage = (pgRoutingPercentage + percentage) / 2;
        }
        Database.pgRoutingConfigMap.put(paymentGatewayEnum, pgRoutingPercentage);
      }
      PaymentGatewayEnum paymentGatewayEnum =
          PaymentGatewayEnum.values()[PaymentGatewayEnum.values().length - 1];
      paymentGatewayRoutingMap.put(paymentGatewayEnum, paymentModecount);
      int pgRoutingPercentage = Database.pgRoutingConfigMap.getOrDefault(paymentGatewayEnum, 0);
      if (pgRoutingPercentage == 0) {
        pgRoutingPercentage = paymentModecount;
      } else {
        pgRoutingPercentage = ((pgRoutingPercentage + paymentModecount) % 2) == 0
            ? (pgRoutingPercentage + paymentModecount) / 2
            : ((pgRoutingPercentage + paymentModecount) / 2) + 1;
      }
      Database.pgRoutingConfigMap.put(paymentGatewayEnum, pgRoutingPercentage);
      Database.paymentModeRoutingConfigMap.put(paymentModeEnum, paymentGatewayRoutingMap);
    }


    // Database.pgRoutingConfigMap = new HashMap<>();
    // Database.pgRoutingConfigMap.put(PaymentGatewayEnum.RAZORPAY, 30);
    // Database.pgRoutingConfigMap.put(PaymentGatewayEnum.PHONEPE, 30);
    // Database.pgRoutingConfigMap.put(PaymentGatewayEnum.PAYTM, 40);


  }

  @Override
  public PaymentGatewayEnum select() {
    Random rand = new Random();
    Integer random = rand.nextInt(100);
    PaymentGatewayEnum selectedPaymentGateway = null;
    int mindiff = Integer.MAX_VALUE;
    for (Map.Entry<PaymentGatewayEnum, Integer> entry : Database.pgRoutingConfigMap.entrySet()) {
      if (Math.abs(entry.getValue() - random) < mindiff) {
        mindiff = Math.abs(entry.getValue() - random);
        selectedPaymentGateway = entry.getKey();
      } else if (Math.abs(entry.getValue() - random) == mindiff) {
        mindiff = Math.abs(entry.getValue() - random);
        int x = rand.nextInt(100);
        if ((x % 2) == 0) {
          selectedPaymentGateway = entry.getKey();
        }
      }
    }
    return selectedPaymentGateway;
  }

  @Override
  public PaymentGatewayEnum select(PaymentModeEnum paymentModeEnum) {
    Random rand = new Random();
    Integer random = rand.nextInt(100);
    PaymentGatewayEnum selectedPaymentGateway = null;
    int mindiff = Integer.MAX_VALUE;
    Map<PaymentGatewayEnum, Integer> pgRoutingConfigMap =
        Database.paymentModeRoutingConfigMap.get(paymentModeEnum);
    for (Map.Entry<PaymentGatewayEnum, Integer> entry : pgRoutingConfigMap.entrySet()) {
      if (Math.abs(entry.getValue() - random) < mindiff) {
        mindiff = Math.abs(entry.getValue() - random);
        selectedPaymentGateway = entry.getKey();
      } else if (Math.abs(entry.getValue() - random) == mindiff) {
        mindiff = Math.abs(entry.getValue() - random);
        int x = rand.nextInt(100);
        if ((x % 2) == 0) {
          selectedPaymentGateway = entry.getKey();
        }
      }
    }
    return selectedPaymentGateway;
  }

  @Override
  public Map<PaymentGatewayEnum, Integer> getDistribution() {
    return Database.pgRoutingConfigMap;
  }

  @Override
  public Map<PaymentModeEnum, Map<PaymentGatewayEnum, Integer>> getPaymentModewiseDistribution() {
    return Database.paymentModeRoutingConfigMap;
  }

  @Override
  public boolean reconfigureDistribution() {
    Random rand = new Random();
    for (PaymentModeEnum paymentModeEnum : PaymentModeEnum.values()) {
      Map<PaymentGatewayEnum, Integer> paymentGatewayRoutingMap = new HashMap<>();
      int paymentModecount = 100;
      for (int i = 0; i < (PaymentGatewayEnum.values().length - 1); i++) {
        int percentage = rand.nextInt(paymentModecount + 1);
        PaymentGatewayEnum paymentGatewayEnum = PaymentGatewayEnum.values()[i];
        paymentGatewayRoutingMap.put(paymentGatewayEnum, percentage);
        paymentModecount -= percentage;
        int pgRoutingPercentage = Database.pgRoutingConfigMap.getOrDefault(paymentGatewayEnum, 0);
        if (pgRoutingPercentage == 0) {
          pgRoutingPercentage = percentage;
        } else {
          pgRoutingPercentage = (pgRoutingPercentage + percentage) / 2;
        }
        Database.pgRoutingConfigMap.put(paymentGatewayEnum, pgRoutingPercentage);
      }
      PaymentGatewayEnum paymentGatewayEnum =
          PaymentGatewayEnum.values()[PaymentGatewayEnum.values().length - 1];
      paymentGatewayRoutingMap.put(paymentGatewayEnum, paymentModecount);
      int pgRoutingPercentage = Database.pgRoutingConfigMap.getOrDefault(paymentGatewayEnum, 0);
      if (pgRoutingPercentage == 0) {
        pgRoutingPercentage = paymentModecount;
      } else {
        pgRoutingPercentage = ((pgRoutingPercentage + paymentModecount) % 2) == 0
            ? (pgRoutingPercentage + paymentModecount) / 2
            : ((pgRoutingPercentage + paymentModecount) / 2) + 1;
      }
      Database.pgRoutingConfigMap.put(paymentGatewayEnum, pgRoutingPercentage);
      Database.paymentModeRoutingConfigMap.put(paymentModeEnum, paymentGatewayRoutingMap);
    }
    return true;
  }

}
