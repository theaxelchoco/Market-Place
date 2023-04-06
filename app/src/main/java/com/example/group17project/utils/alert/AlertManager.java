package com.example.group17project.utils.alert;

import com.example.group17project.utils.model.observer.Observer;

public interface AlertManager {
  public void attach(Observer observer);

  public void alertUsers();
}
