package service;

import java.util.HashSet;
import java.util.Set;

import database.Database;
import enums.PaymentModeEnum;
import model.Client;

/**
 * Created by Nikhil on 18-Jun-2022
 */
public class ClientService implements IClientService {

  @Override
  public boolean addClient(Client client) {
    Database.clientPaymentModeMap.put(client, new HashSet<>());
    return true;
  }

  @Override
  public boolean addClient(Client client, PaymentModeEnum paymentModeEnum) {
    Set<PaymentModeEnum> existingPaymentModes = Database.clientPaymentModeMap.get(client);
    if (existingPaymentModes == null) {
      existingPaymentModes = new HashSet<>();
    }
    existingPaymentModes.add(paymentModeEnum);
    Database.clientPaymentModeMap.put(client, existingPaymentModes);
    return true;
  }

  @Override
  public boolean removeClient(Client client) {
    Database.clientPaymentModeMap.remove(client);
    return true;
  }

  @Override
  public boolean hasClient(Client client) {
    return Database.clientPaymentModeMap.containsKey(client);
  }

  @Override
  public Set<PaymentModeEnum> listSupportedPaymodes(Client client) {
    return Database.clientPaymentModeMap.get(client);
  }

  @Override
  public boolean addSupportForPaymode(Client client, PaymentModeEnum paymentModeEnum) {
    Set<PaymentModeEnum> existingPaymentModes = Database.clientPaymentModeMap.get(client);
    existingPaymentModes.add(paymentModeEnum);
    Database.clientPaymentModeMap.put(client, existingPaymentModes);
    return true;
  }

}
