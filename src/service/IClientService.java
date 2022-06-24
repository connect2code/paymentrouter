package service;

import java.util.Set;

import enums.PaymentModeEnum;
import model.Client;

/**
 * Created by Nikhil on 18-Jun-2022
 */
public interface IClientService {
  public boolean addClient(Client client);

  public boolean addClient(Client client, PaymentModeEnum paymentModeEnums);

  public boolean removeClient(Client client);

  public boolean hasClient(Client client);

  public Set<PaymentModeEnum> listSupportedPaymodes(Client client);

  public boolean addSupportForPaymode(Client client, PaymentModeEnum paymentModeEnum);


}
