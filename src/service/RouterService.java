package service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import database.Database;
import enums.PaymentGatewayEnum;
import enums.PaymentModeEnum;
import model.Client;
import model.Transaction;
import pg.IPGService;
import pg.PaytmPG;
import pg.PhonePePG;
import pg.RazorPayPG;

/**
 * Created by Nikhil on 18-Jun-2022
 */
public class RouterService implements IRouterService {

  /**
   *
   */
  private IClientService clientService;

  private IPGSelectionService pgSelectionService;


  public RouterService(Set<PaymentModeEnum> initialSupportedPaymentModes) {
    clientService = new ClientService();
    pgSelectionService = new PGSelectionService();
    Database.pgServiceMap = new HashMap<>();
    Database.pgServiceMap.put(PaymentGatewayEnum.RAZORPAY, new RazorPayPG());
    Database.pgServiceMap.put(PaymentGatewayEnum.PHONEPE, new PhonePePG());
    Database.pgServiceMap.put(PaymentGatewayEnum.PAYTM, new PaytmPG());
    Database.paymentModeMaster = initialSupportedPaymentModes;
    Database.clientPaymentModeMap = new HashMap<>();

    Database.txnClientMap = new HashMap<>();
    Database.txnPaymentModeEnum = new HashMap<>();
    Database.txnPGMap = new HashMap<>();
    Database.pgTxnMap = new HashMap<>();
    Database.pgTxnSuccessMap = new HashMap<>();
    Database.pgTxnFailureMap = new HashMap<>();
    Database.clientTxnMap = new HashMap<>();
    Database.paymentModeTxnMap = new HashMap<>();


  }


  @Override
  public boolean hasClient() {
    return !Database.clientPaymentModeMap.isEmpty();
  }


  @Override
  public Set<PaymentModeEnum> listSupportedPaymodes() {
    return Database.paymentModeMaster;
  }


  @Override
  public boolean addSupportForPaymode(PaymentModeEnum paymentModeEnum) {
    Set<PaymentModeEnum> existingPaymentModes = Database.paymentModeMaster;
    existingPaymentModes.add(paymentModeEnum);
    return true;
  }


  @Override
  public Map<PaymentGatewayEnum, Integer> showDistribution() {
    return pgSelectionService.getDistribution();
  }

  @Override
  public Map<PaymentModeEnum, Map<PaymentGatewayEnum, Integer>> showPaymentModewiseDistribution() {
    return pgSelectionService.getPaymentModewiseDistribution();
  }

  @Override
  public boolean reconfigureDistribution() {
    return pgSelectionService.reconfigureDistribution();
  }



  @Override
  public boolean transact(Transaction transaction, Client client) {
    if (!Database.clientPaymentModeMap.containsKey(client)) {
      System.out.println("Invalid Client");
      return false;
    }
    if (!Database.clientPaymentModeMap.get(client).contains(transaction.getPaymentMode())) {
      System.out.println(
          "Payment mode: " + transaction.getPaymentMode() + " not supported for client: " + client);
      return false;
    }

    PaymentGatewayEnum selectedPaymentGateway = pgSelectionService.select();

    IPGService pgService = Database.pgServiceMap.get(selectedPaymentGateway);

    System.out.println("Transaction via: " + selectedPaymentGateway);
    boolean status = pgService.transact(transaction);
    transaction.setStatus(status);

    Database.txnClientMap.put(transaction, client);
    Database.txnPaymentModeEnum.put(transaction, transaction.getPaymentMode());
    Database.txnPGMap.put(transaction, selectedPaymentGateway);

    Set<Transaction> existingTxn =
        Database.pgTxnMap.getOrDefault(selectedPaymentGateway, new HashSet<>());
    existingTxn.add(transaction);
    Database.pgTxnMap.put(selectedPaymentGateway, existingTxn);

    existingTxn = Database.clientTxnMap.getOrDefault(client, new HashSet<>());
    existingTxn.add(transaction);
    Database.clientTxnMap.put(client, existingTxn);

    existingTxn =
        Database.paymentModeTxnMap.getOrDefault(transaction.getPaymentMode(), new HashSet<>());
    existingTxn.add(transaction);
    Database.paymentModeTxnMap.put(transaction.getPaymentMode(), existingTxn);

    if (status) {
      existingTxn = Database.pgTxnSuccessMap.getOrDefault(selectedPaymentGateway, new HashSet<>());
      existingTxn.add(transaction);
      Database.pgTxnSuccessMap.put(selectedPaymentGateway, existingTxn);
    } else {
      existingTxn = Database.pgTxnFailureMap.getOrDefault(selectedPaymentGateway, new HashSet<>());
      existingTxn.add(transaction);
      Database.pgTxnFailureMap.put(selectedPaymentGateway, existingTxn);
    }

    return false;

  }

  @Override
  public boolean changeRoutingConfig(Map<PaymentGatewayEnum, Integer> routingConfigMap) {
    if (routingConfigMap.size() != PaymentGatewayEnum.values().length) {
      System.out.println("Please provide routing config for all payment gateways");
      return false;
    }
    Database.pgRoutingConfigMap = routingConfigMap;
    return true;
  }

  @Override
  public void showTransactionMetrics() {
    System.out.println("\n\n\n\n");
    System.out.println("Key Metrics");
    System.out.println("PGwise Transaction Count");
    for (Map.Entry<PaymentGatewayEnum, Set<Transaction>> entry : Database.pgTxnMap.entrySet()) {
      System.out.println(entry.getKey() + " " + entry.getValue().size());
    }
    System.out.println();
    System.out.println("PGwise Success Transaction Count");
    for (Map.Entry<PaymentGatewayEnum, Set<Transaction>> entry : Database.pgTxnSuccessMap
        .entrySet()) {
      System.out.println(entry.getKey() + " " + entry.getValue().size());
    }

    System.out.println();
    System.out.println("PGwise Failure Transaction Count");
    for (Map.Entry<PaymentGatewayEnum, Set<Transaction>> entry : Database.pgTxnFailureMap
        .entrySet()) {
      System.out.println(entry.getKey() + " " + entry.getValue().size());
    }
    System.out.println();
    System.out.println("Payment Method wise Transaction Count");
    for (Map.Entry<PaymentModeEnum, Set<Transaction>> entry : Database.paymentModeTxnMap
        .entrySet()) {
      System.out.println(entry.getKey() + " " + entry.getValue().size());
    }
    System.out.println();
    System.out.println("Client wise Transaction count");
    for (Map.Entry<Client, Set<Transaction>> entry : Database.clientTxnMap.entrySet()) {
      System.out.println(entry.getKey() + " " + entry.getValue().size());
    }

  }



  @Override
  public boolean addClient(Client client) {
    return clientService.addClient(client);
  }

  @Override
  public boolean addClient(Client client, PaymentModeEnum paymentModeEnum) {
    if (!Database.paymentModeMaster.contains(paymentModeEnum)) {
      System.out.println("Payment mode: " + paymentModeEnum + " not supported by ecosystem");
      return false;
    }
    clientService.addClient(client, paymentModeEnum);
    return true;
  }

  @Override
  public boolean addClient(Client client, Set<PaymentModeEnum> paymentModeEnums) {
    if (paymentModeEnums == null) {
      return false;
    }
    for (PaymentModeEnum paymentMode : paymentModeEnums) {
      if (!Database.paymentModeMaster.contains(paymentMode)) {
        System.out.println("Payment mode: " + paymentMode + " not supported by ecosystem");
        continue;
      }
      clientService.addClient(client, paymentMode);

    }
    return true;
  }



  @Override
  public boolean removeClient(Client client) {
    return clientService.removeClient(client);
  }


  @Override
  public boolean hasClient(Client client) {
    return clientService.hasClient(client);
  }


  @Override
  public Set<PaymentModeEnum> listSupportedPaymodes(Client client) {
    return clientService.listSupportedPaymodes(client);
  }


  @Override
  public boolean addSupportForPaymode(Client client, PaymentModeEnum paymentModeEnum) {
    if (!Database.paymentModeMaster.contains(paymentModeEnum)) {
      System.out.println("Payment mode: " + paymentModeEnum + " not supported by ecosystem");
      return false;
    }
    return clientService.addSupportForPaymode(client, paymentModeEnum);
  }



}
