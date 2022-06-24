package model;

import java.util.UUID;

/**
 * Created by Nikhil on 18-Jun-2022
 */
public class Client {
  private String id;
  private String name;

  /**
   *
   */
  public Client(String name) {
    this.name = name;
    id = UUID.randomUUID().toString();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }

}
