package com.example.group17project.utils.model.user;

import com.example.group17project.utils.alert.AlertManager;

public abstract class Observer {
  public AlertManager manager;

  public abstract void update(String message);
}
