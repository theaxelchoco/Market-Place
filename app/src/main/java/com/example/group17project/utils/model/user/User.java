package com.example.group17project.utils.model.user;

public class User extends Observer {
  private static User instance = null;

  private String email = "test@dal.ca";
  private int pValuation = 0;
  private int rValuation = 0;
  private float ratingTotal = 0;
  private int numRatings = 0;

  private String userLocation;


  private User() {

  }

  public static User getInstance() {
    if (instance == null) {
      instance = new User();
    }
    return instance;
  }

  public void setpValuation(int pValuation) {
    this.pValuation = pValuation;
  }

  public int getpValuation() {
    return pValuation;
  }

  public void setrValuation(int rValuation) {
    this.rValuation = rValuation;
  }

  public int getrValuation() {
    return rValuation;
  }

  public void setRating(float rating) {
    this.ratingTotal = rating;
  }

  public float getRating() {
    return ratingTotal;
  }

  public void setNumRatings(int num) {
    this.numRatings = num;
  }

  public int getNumRatings() {
    return numRatings;
  }

  public void setUserDetails(String email, int pVal, int rVal, float rate, int rateNums) {
    this.email = email;
    this.pValuation = pVal;
    this.rValuation = rVal;
    this.ratingTotal = rate;
    this.numRatings = rateNums;
  }

  public void setUserLocation(String userLocation) {
    this.userLocation = userLocation;
  }

  public String getUserLocation() {
    return userLocation;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public void update(String message) {

  }
}
