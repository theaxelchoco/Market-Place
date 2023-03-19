package com.example.group17project.utils;

import android.os.Bundle;
import android.util.Range;

import androidx.appcompat.app.AlertDialog;

import com.example.group17project.utils.model.Filter;
import com.example.group17project.utils.model.ProductType;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;

public class Methods {
  private final static String GOOGLE_MAPS_API_KEY = "AIzaSyADgAur80t_vb3uX2PEm75aNBiUq9yFOOU";

  private Methods() {
  }

  public static void makeAlert(String message, AlertDialog.Builder builder) {
    builder.setMessage(message);
    builder.setCancelable(true);
    builder.setPositiveButton("OK", (dialog, which) -> dialog.cancel());
    AlertDialog alert = builder.create();
    alert.show();
  }

  public static Filter makeFilter(Bundle bundle, String ownerID) {
    return new Filter(
        ownerID,
        ProductType.valueOf(bundle.getString("productType")),
        ProductType.valueOf(bundle.getString("preferredProductType")),
        makeRange(bundle.getString("minPrice"), bundle.getString("maxPrice")),
        bundle.getString("location")
    );
  }

  public static boolean isSameLocation(String city1, String city2) {
    if (city1.isEmpty() || city2.isEmpty()) {
      return true;
    }
    GeoApiContext context = new GeoApiContext.Builder()
        .apiKey(GOOGLE_MAPS_API_KEY)
        .build();

    try {
      GeocodingResult[] results1 = GeocodingApi.geocode(context, city1).await();
      GeocodingResult[] results2 = GeocodingApi.geocode(context, city2).await();

      if (results1.length > 0 && results2.length > 0) {
        String city1Formatted = results1[0].addressComponents[0].longName;
        String city2Formatted = results2[0].addressComponents[0].longName;
        return city1Formatted.equals(city2Formatted);
      }
    } catch (IOException | InterruptedException | ApiException e) {
      e.printStackTrace();
    }
    return false;
  }

  private static Range<Integer> makeRange(String min, String max) {
    int minPrice = min.isEmpty() ? 0 : Integer.parseInt(min);
    int maxPrice = max.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(max);
    return new Range<>(minPrice, maxPrice);
  }
}
