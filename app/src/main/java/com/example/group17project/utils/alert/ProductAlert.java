package com.example.group17project.utils.alert;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.group17project.R;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.observer.Filter;
import com.example.group17project.utils.model.observer.Observer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProductAlert implements AlertManager {
  private final Set<String> receiverIDs;
  private final Product product;
  private final Context context;
  private final RequestQueue requestQueue;

  /**
   * The HashSet filters is all the filters that are currently in the database
   *
   * @param product the product to be alerted
   * @param filters the list of filters to applied
   */
  public ProductAlert(Product product, Set<Filter> filters, Context context) {
    this.product = product;
    this.receiverIDs = new HashSet<>();
    this.context = context;
    this.requestQueue = Volley.newRequestQueue(context);
    gatherOwnerIDs(filters);
  }

  public Set<String> getReceiverIDs() {
    return receiverIDs;
  }

  /**
   * This is the attach method for the observer pattern
   *
   * @param filters the list of filters to applied
   */
  public void gatherOwnerIDs(Set<Filter> filters) {
    filters.stream()
        .filter(filter -> filter.isMatch(product))
        .filter(filter -> !filter.getOwnerID().equals(product.getOwnerID()))
        .forEach(this::attach);
  }

  @Override
  public void attach(Observer filter) {
    if (filter instanceof Filter) {
      receiverIDs.add(((Filter) filter).getOwnerID());
    }
  }

  @Override
  public void alertUsers() {
    receiverIDs.forEach(this::sendNotification);
  }

  private void sendNotification(String receiverID) {
    try {
      final JSONObject notificationJsonBody = new JSONObject()
          .put("title", "New Product Available")
          .put("body", "You may interested in " + product.getName());

      final JSONObject dataJSONBody = new JSONObject()
          .put("productID", product.getProductID())
          .put("productLocation", product.getLocationID());

      final JSONObject root = new JSONObject()
          .put("to", "/topics/" + receiverID)
          .put("notification", notificationJsonBody)
          .put("data", dataJSONBody);

      JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
          context.getResources().getString(R.string.push_notification_endpoint),
          root,
          response -> {},
          Throwable::printStackTrace) {
        public Map<String, String> getHeaders() {
          final Map<String, String> headers = new HashMap<>();
          headers.put("content-type", "application/json");
          headers.put("authorization", "key=" + context.getResources().getString(R.string.FIREBASE_SERVER_KEY));
          return headers;
        }

      };

      requestQueue.add(request);
    } catch (JSONException e) {
      Log.println(Log.ERROR, "Notification", "Error while creating notification json body");
    }
  }
}
