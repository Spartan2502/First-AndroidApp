package com.example.evp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.DecimalFormat;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String VIEW_CART_BUTTON = "view_cart_button";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        sharedPreferences = getSharedPreferences("cart", MODE_PRIVATE);

        TextView cartItemsTextView = findViewById(R.id.cart_items_textview);
        cartItemsTextView.setText(getCartItemsString());

        TextView totalTextView = findViewById(R.id.total_textview);
        double total = calculateTotal();
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        totalTextView.setText("Total: $" + decimalFormat.format(total));

        BottomNavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.view_cart_button) {
                    finish();
                    return true;
                } else if (itemId == R.id.sign_out_button) {
                    sharedPreferences.edit().clear().apply();
                    Intent signOutIntent = new Intent(CartActivity.this, MainActivity.class);
                    startActivity(signOutIntent);
                    finish();
                    return true;
                } else if (itemId == R.id.empty_cart_button) {
                    sharedPreferences.edit().clear().apply();
                    finish();
                    return true;
                }
                return false;
            }
        });

        Menu menu = navigationView.getMenu();
        MenuItem viewCartButton = menu.findItem(R.id.view_cart_button);
        MenuItem signOutButton = menu.findItem(R.id.sign_out_button);
        MenuItem emptyCartButton = menu.findItem(R.id.empty_cart_button);

        viewCartButton.setTitle("volver");
        signOutButton.setTitle("Cerrar Sesión");
        emptyCartButton.setTitle("Vaciar Carrito");
    }

    private String getCartItemsString() {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, ?> cartItems = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : cartItems.entrySet()) {
            String productName = entry.getKey();
            int quantity = Integer.parseInt(entry.getValue().toString());
            double itemCost = getItemCost(productName);
            double totalCost = itemCost * quantity;

            stringBuilder.append(productName).append(" - ").append(quantity).append(" unidades")
                    .append("\nPrecio unitario: $").append(itemCost)
                    .append("\nPrecio total: $").append(totalCost).append("\n\n");
        }

        return stringBuilder.toString();
    }

    private double getItemCost(String productName) {
        double cost = 0.0;

        if (productName.equals("Refresco")) {
            cost = 20.0;
        } else if (productName.equals("Torta")) {
            cost = 40.0;
        } else if (productName.equals("Taco")) {
            cost = 15.0;
        } else if (productName.equals("Quesadilla")) {
            cost = 30.0;
        } else if (productName.equals("Flan")) {
            cost = 18.0;
        } else if (productName.equals("Volcán")) {
            cost = 20.0;
        }

        return cost;
    }

    private double calculateTotal() {
        double total = 0.0;
        Map<String, ?> cartItems = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : cartItems.entrySet()) {
            String productName = entry.getKey();
            int quantity = Integer.parseInt(entry.getValue().toString());
            double itemCost = getItemCost(productName);
            double totalCost = itemCost * quantity;
            total += totalCost;
        }

        return total;
    }
}
