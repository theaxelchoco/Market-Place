package com.example.group17project.testhelper;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group17project.R;
import com.example.group17project.utils.model.Product;
import com.example.group17project.utils.model.ProductType;
import com.example.group17project.utils.repository.ProductRepository;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * This activity is used to test the ProductRepository class.
 * NOT A PRODUCTION CLASS, DELETE BEFORE DEPLOY.
 */
public class ProductCRUDHelperActivity extends AppCompatActivity {

  private Product testProduct;
  private Product currentProduct;
  private ProductRepository productRepository;
  private TextView productDisplay;
  private Button addDefaultProductButton, clearAllProductsButton;
  private Button readProductButton, deleteProductButton;
  private Button createNewProductButton, addNewProductButton;

  private EditText testProductID, newProductName, newProductOwnerID;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_product_crud_helper);

    setUp();
    showProduct(currentProduct);

    addDefaultProductButton.setOnClickListener(v -> {
      productRepository.createProduct(testProduct);
      showProduct(testProduct);
    });

    clearAllProductsButton.setOnClickListener(v -> {
      productRepository.deleteAllProducts();
      showProduct(null);
    });

    readProductButton.setOnClickListener(v -> {
      String id = testProductID.getText().toString().trim();
    });

    deleteProductButton.setOnClickListener(v -> {
      String id = testProductID.getText().toString().trim();
      productRepository.deleteProduct(id);
    });

    createNewProductButton.setOnClickListener(v -> {
      String name = newProductName.getText().toString();
      String ownerID = newProductOwnerID.getText().toString();
      currentProduct = new Product(name, ownerID);
      showProduct(currentProduct);
    });

    addNewProductButton.setOnClickListener(v -> {
      productRepository.createProduct(currentProduct);
      showProduct(currentProduct);
    });
  }

  private void showProduct(Product product) {
    productDisplay.setText(product == null ? "null" : product.toString());
  }

  private void setUp() {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://w23-csci3130-group-17-default-rtdb.firebaseio.com/");
    productRepository = new ProductRepository(database, true);

    productDisplay = findViewById(R.id.productDisplay);

    findButtons();
    findEditViews();
    makeDefaultProduct();
  }

  private void findEditViews() {
    testProductID = findViewById(R.id.testProductID);
    newProductName = findViewById(R.id.newTestProductName);
    newProductOwnerID = findViewById(R.id.newTestProductOwner);
  }

  private void findButtons() {
    addDefaultProductButton = findViewById(R.id.addTestProduct);
    clearAllProductsButton = findViewById(R.id.clearTestDB);
    readProductButton = findViewById(R.id.showTestProduct);
    deleteProductButton = findViewById(R.id.deleteTestProduct);
    createNewProductButton = findViewById(R.id.createTestProduct);
    addNewProductButton = findViewById(R.id.addNewTestProduct);
  }

  private void makeDefaultProduct() {
    testProduct = new Product("testProduct", "testOwnerID");
    testProduct.setDescription("testDescription");
    testProduct.setDateAvailable(new Date());
    testProduct.setType(ProductType.COMPUTER_ACCESSORIES);
    testProduct.setLocationID("testLocationID");
    testProduct.setPrice(10.0);
    testProduct.setPreferredExchange(ProductType.MOBILE_PHONES);

    currentProduct = testProduct;
  }
}