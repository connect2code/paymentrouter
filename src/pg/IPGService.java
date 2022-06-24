package pg;

import model.Transaction;

/**
 * Created by Nikhil on 18-Jun-2022
 */
public interface IPGService {
  public boolean transact(Transaction transaction);
}
