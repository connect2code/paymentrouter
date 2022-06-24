package pg;

import java.util.Random;

import model.Transaction;

/**
 * Created by Nikhil on 18-Jun-2022
 */
public class PhonePePG implements IPGService {
  private Random rand;

  /**
   *
   */
  public PhonePePG() {
    rand = new Random();
  }


  @Override
  public boolean transact(Transaction transaction) {
    Integer randomNumber = rand.nextInt(1000);
    return (randomNumber % 2) == 0 ? true : false;
  }
}
