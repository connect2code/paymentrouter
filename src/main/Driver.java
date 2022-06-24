package main;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import enums.PaymentModeEnum;
import model.Client;
import model.Transaction;
import service.IRouterService;
import service.RouterService;

/**
 * Created by Nikhil on 18-Jun-2022
 */
public class Driver {

  public static void main(String args[]) {
    Set<PaymentModeEnum> initialPaymentModes = new HashSet<>();
    initialPaymentModes.add(PaymentModeEnum.CC);
    initialPaymentModes.add(PaymentModeEnum.DC);
    IRouterService routerService = new RouterService(initialPaymentModes);


    System.out
        .println("Supported Payment Modes by ecosystem: " + routerService.listSupportedPaymodes());

    System.out.println();
    System.out.println("Adding Payment Mode: " + PaymentModeEnum.NETBANKING);
    routerService.addSupportForPaymode(PaymentModeEnum.NETBANKING);
    routerService.addSupportForPaymode(PaymentModeEnum.UPI);
    System.out
        .println("Supported Payment Modes by Ecosystem: " + routerService.listSupportedPaymodes());

    System.out.println();
    System.out.println("Ecosystem has any clients? " + routerService.hasClient());
    System.out.println();
    Client client1 = new Client("client1");
    System.out.println("Adding Client: " + client1);
    routerService.addClient(client1);
    Client client2 = new Client("client2");
    System.out.println("Adding Client: " + client2 + " with Payment mode: "
        + Arrays.asList(PaymentModeEnum.values()));
    routerService.addClient(client2, new HashSet<>(Arrays.asList(PaymentModeEnum.values())));
    System.out.println();
    System.out.println("Ecosystem has any clients? " + routerService.hasClient());

    System.out.println();
    System.out.println("Supported Payment Modes for client: " + client1 + " "
        + routerService.listSupportedPaymodes(client1));
    System.out.println("Supported Payment Modes for client: " + client2 + " "
        + routerService.listSupportedPaymodes(client2));

    System.out.println();
    System.out
        .println("Adding Payment mode: " + PaymentModeEnum.NETBANKING + " to client: " + client1);
    routerService.addSupportForPaymode(client1, PaymentModeEnum.NETBANKING);
    System.out.println("Supported Payment Modes for client: " + client1 + " "
        + routerService.listSupportedPaymodes(client1));

    System.out.println("Adding Payment mode: " + PaymentModeEnum.UPI + " to client: " + client2);
    routerService.addSupportForPaymode(client2, PaymentModeEnum.UPI);
    System.out.println("Supported Payment Modes for client: " + client2 + " "
        + routerService.listSupportedPaymodes(client2));

    System.out.println();
    System.out.print("Current Distribution: " + routerService.showDistribution());
    System.out.println();

    System.out.println();
    System.out
        .print("PaymentMode wise Distribution: " + routerService.showPaymentModewiseDistribution());
    System.out.println();

    for (int i = 0; i < 100; i++) {
      Transaction txn = new Transaction(
          PaymentModeEnum.values()[(i % PaymentModeEnum.values().length)], (double) 10);
      routerService.transact(txn, client1);
    }

    routerService.reconfigureDistribution();

    System.out.println();
    System.out.print("Current Distribution: " + routerService.showDistribution());
    System.out.println();

    System.out.println();
    System.out
        .print("PaymentMode wise Distribution: " + routerService.showPaymentModewiseDistribution());
    System.out.println();

    for (int i = 0; i < 100; i++) {
      Transaction txn = new Transaction(
          PaymentModeEnum.values()[(i % PaymentModeEnum.values().length)], (double) 10);
      routerService.transact(txn, client2);
    }
    routerService.showTransactionMetrics();


  }

}
