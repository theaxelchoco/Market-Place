package com.example.group17project.utils.alert;

import com.example.group17project.utils.model.observer.Observer;

//This interface is used to implement the observer pattern as discussed in class
public interface AlertManager {
  public void attach(Observer observer);

  public void alertUsers();
}
