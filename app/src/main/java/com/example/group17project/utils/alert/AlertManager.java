package com.example.group17project.utils.alert;

import com.example.group17project.utils.model.user.Observer;

public interface AlertManager {
  public void attach(Observer user);

  public void alertUsers();
}
