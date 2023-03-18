package com.example.group17project.utils;

import androidx.appcompat.app.AlertDialog;

public class Methods {
  private Methods() {
  }

  public static void makeAlert(String message, AlertDialog.Builder builder) {
    builder.setMessage(message);
    builder.setCancelable(true);
    builder.setPositiveButton("OK", (dialog, which) -> dialog.cancel());
    AlertDialog alert = builder.create();
    alert.show();
  }
}
