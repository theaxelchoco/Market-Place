/*
AddProduct code
Group 17
*/

package com.example.group17project.ProviderFunctionality;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group17project.Homepages.HomepageActivity;
import com.example.group17project.ProviderFunctionality.AddUtils.ProxyAddProduct;
import com.example.group17project.R;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.User;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


  private Button submitButton;
  private Button cancelButton;
  private EditText productName;
  private EditText description;
  private EditText placeOfExchange;
  private EditText marketValue;
  private TextView productNameErrorLbl;
  private TextView exchangeErrorLbl;
  private TextView marketErrorLbl;
  private boolean edit;
  private String productId;
  private Product productToEdit;

  private DatePicker datePicker;
  private User user;

  private Spinner productType;
  private Spinner preferredExchange;

  private String selectedProductType;
  private String selectedPreferredExchange;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_addproduct);
    setUp();
    checkIfEdit();
    user = User.getInstance();
    placeOfExchange.setText(user.getUserLocation());
    submitButton.setOnClickListener(this::onClick);
    cancelButton.setOnClickListener(this::cancelButtonOnClick);
  }

  /**
   * This method checks to see if the form is being reopened to edit or if it is a first time addition. If editing, the
   * application will take the existing data from the item and have it ready for display so the user can edit some or all fields
   * to their desire.
   */
  protected void checkIfEdit() {
    Intent intent = this.getIntent();
    edit = false;
    if (intent != null && intent.hasExtra("edit")) {
      productId = intent.getStringExtra("edit");
      edit = true;

      String name = intent.getStringExtra("name");
      String type = intent.getStringExtra("type");
      String exchange = intent.getStringExtra("exchange");
      String desc = intent.getStringExtra("desc");
      String location = intent.getStringExtra("location");
      Long dateVal = intent.getLongExtra("date", 0);
      Date date = new Date(dateVal);
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      int price = intent.getIntExtra("price", 0);
      productToEdit = new Product(name, User.getInstance().getEmail(), desc, cal, type, location, exchange, price);

      setFields();
    }
  }

  /**
   * This method is used to set the fields of the form to a predefined set of values. Only used for edit mode so
   * user has their existing item details.
   */
  protected void setFields() {
    productName.setText(productToEdit.getName());
    description.setText(productToEdit.getDescription());
    placeOfExchange.setText(productToEdit.getLocationID());
    setDatePicker();
    setSpinners();
  }

  /**
   * This method sets the date picker based off today's date by default
   */
  protected void setDatePicker() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(productToEdit.getDateAvailable());
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);
    datePicker.init(year, month, day, null);
  }

  /**
   * This method sets the spinners to the corresponding value that needs to be displayed by default
   */
  protected void setSpinners() {
    ArrayList<String> types = new ArrayList<>();
    types.add("Baby Toys");
    types.add("Clothes");
    types.add("Computer Accessories");
    types.add("Mobile Phones");
    types.add("Furniture");

    int typePosition = types.indexOf(productToEdit.getType());
    int prefPosition = types.indexOf(productToEdit.getPreferredExchange());

    productType.setSelection(typePosition);
    preferredExchange.setSelection(prefPosition);
  }

  public void cancelButtonOnClick(View view) {
    switchBack();
  }

  /**
   * This is the onlclick method for the submit button. Checks to see if all required fields are filled out.
   * Adds item to repo and switches intent after validation, otherwise displays errors.
   *
   * @param view the view on the screen
   */
  public void onClick(View view) {
    String productNameText = getProductName();
    String descriptionText = getDescription();
    String productTypeText = getType();
    String prefExchange = getPreferredExchange();
    String exchangePlace = getPlaceOfExchange();
    String marketVal = getMarketValue();
    Calendar date = getDate();
    ProxyAddProduct proxyAdder = ProxyAddProduct.getInstance();
    boolean operationStatus;

    if (edit) {
      operationStatus =
          proxyAdder.edit(
              productNameText,
              user.getEmail(),
              descriptionText,
              date,
              productTypeText,
              exchangePlace,
              prefExchange,
              marketVal,
              productId
          );
    } else {
      operationStatus =
          proxyAdder.add(
              productNameText,
              user.getEmail(),
              descriptionText,
              date,
              productTypeText,
              exchangePlace,
              prefExchange,
              marketVal
          );
    }

    if (operationStatus) {
      if (edit) {
        Toast.makeText(this, getResources().getString(R.string.SUCCESSFUL_PRODUCT_UPDATE).trim(), Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(this, getResources().getString(R.string.SUCCESSFUL_PRODUCT_CREATION).trim(), Toast.LENGTH_SHORT).show();
      }
      switchBack();
    } else {
      setErrors(productNameText, exchangePlace, marketVal);
    }
  }

  /**
   * This method is used to switch back to the main activity but in the provider fragment
   */
  public void switchBack() {
    Intent intent = new Intent(this, HomepageActivity.class);
    intent.putExtra("fragmentId", "provider");
    startActivity(intent);
  }

  /**
   * This method is used to set the corresponding error labels with designated messages helping the user correct their input
   *
   * @param productName     the entered product name
   * @param placeOfExchange the entered place of exchange
   * @param marketVal       the entered market value
   */
  protected void setErrors(String productName, String placeOfExchange, String marketVal) {
    String pNameError = "";
    String excError = "";
    String marketError = "";

    if (!productName.isEmpty()) {
      pNameError = getString(R.string.PRODUCT_NAME_ERROR);
    }
    if (!placeOfExchange.isEmpty()) {
      excError = getString(R.string.EXCHANGE_PLACE_ERROR);

    }
    if (!validMarketValue(marketVal)) {
      if (marketVal.isEmpty()) {
        marketError = getString(R.string.EMPTY_MARKET_VAL);
      } else {
        marketError = getString(R.string.MARKET_VAL_NEED_INT_ERROR);
      }
    }

    setProductNameErrorLbl(pNameError);
    setExchangeErrorLbl(excError);
    setMarketErrorLbl(marketError);
  }

  protected void setMarketErrorLbl(String message) {
    marketErrorLbl.setText(message);
  }

  protected void setProductNameErrorLbl(String message) {
    productNameErrorLbl.setText(message);
  }

  protected void setExchangeErrorLbl(String message) {
    exchangeErrorLbl.setText(message);
  }


  protected String getProductName() {
    return productName.getText().toString().trim();
  }

  protected String getDescription() {
    return description.getText().toString().trim();
  }

  protected String getPlaceOfExchange() {
    return placeOfExchange.getText().toString().trim();
  }

  protected String getMarketValue() {
    return marketValue.getText().toString().trim();
  }

  protected Calendar getDate() {
    int day = datePicker.getDayOfMonth();
    int month = datePicker.getMonth();
    int year = datePicker.getYear();

    Calendar cal = Calendar.getInstance();
    cal.set(year, month, day);
    return cal;
  }

  /**
   * Checks to see if the product name is valid (not empty)
   *
   * @param name product name entered by user
   * @return true if valid, false otherwise
   */
  public boolean validProductName(String name) {
    return !name.isEmpty();
  }

  /**
   * Checks to see if market value is valid (not empty and only integer)
   *
   * @param marketValue market val entered by user
   * @return true if valid, false otherwise
   */
  public boolean validMarketValue(String marketValue) {
    if (marketValue.isEmpty()) {
      return false;
    }
    try {
      int intVal = Integer.parseInt(marketValue);
      return intVal > 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Checks to see if place of exchange is valid (not empty)
   *
   * @param place place of exchange entered by user
   * @return true if valid, false otherwise
   */
  public boolean validPlaceOfExchange(String place) {
    return !place.isEmpty();
  }

  protected String getType() {
    return selectedProductType;
  }

  protected String getPreferredExchange() {
    return selectedPreferredExchange;
  }

  private void setUp() {
    ProductRepository productRepository;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");
    productRepository = new ProductRepository(database, false);

    findViewComponents();
    datePicker.setMinDate(System.currentTimeMillis() - 1000);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.product_types, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    productType.setAdapter(adapter);
    preferredExchange.setAdapter(adapter);

    productType.setOnItemSelectedListener(this);
    preferredExchange.setOnItemSelectedListener(this);
  }

  private void findViewComponents() {
    submitButton = findViewById(R.id.submit_button);
    cancelButton = findViewById(R.id.cancel_button);

    productName = findViewById(R.id.productNameEditText);
    description = findViewById(R.id.description_edittext);
    placeOfExchange = findViewById(R.id.place_of_exchange_edittext);
    marketValue = findViewById(R.id.market_value_edittext);

    productNameErrorLbl = findViewById(R.id.productNameErrorLabel);
    exchangeErrorLbl = findViewById(R.id.exchangeErrorLbl);
    marketErrorLbl = findViewById(R.id.marketValueErrorLbl);

    datePicker = findViewById(R.id.date_picker);
    productType = findViewById(R.id.product_type_spinner);
    preferredExchange = findViewById(R.id.preferred_exchanges_spinner);
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    int viewId = adapterView.getId();
    String selectedItem = adapterView.getItemAtPosition(i).toString();

    if (viewId == R.id.product_type_spinner) {
      selectedProductType = selectedItem;
    } else if (viewId == R.id.preferred_exchanges_spinner) {
      selectedPreferredExchange = selectedItem;
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {
    //This method should not do anything if nothing selected, hence why it is empty
  }
}