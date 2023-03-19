package com.example.group17project.utils;

import android.os.Bundle;
import android.util.Range;

import androidx.appcompat.app.AlertDialog;

import com.example.group17project.utils.model.Filter;
import com.example.group17project.utils.model.ProductType;

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

  public static Filter makeFilter(Bundle bundle) {
    return new Filter(
        null,
        ProductType.valueOf(bundle.getString("productType")),
        ProductType.valueOf(bundle.getString("preferredProductType")),
        makeRange(bundle.getString("minPrice"), bundle.getString("maxPrice")),
        bundle.getString("location")
    );
  }

  private static Range<Integer> makeRange(String min, String max) {
    int minPrice = min.isEmpty() ? 0 : Integer.parseInt(min);
    int maxPrice = max.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(max);
    return new Range<>(minPrice, maxPrice);
  }
}
