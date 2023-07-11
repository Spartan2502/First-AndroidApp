package com.example.evp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;


public class MenuActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Map<String, Integer> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sharedPreferences = getSharedPreferences("cart", MODE_PRIVATE);
        cartItems = new HashMap<>();

        Button addRefrescoButton = findViewById(R.id.add_refresco_button);
        addRefrescoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = "Refresco";
                double productCost = 20.0;

                addItemToCart(productName, productCost);
            }
        });

        Button addTortaButton = findViewById(R.id.add_torta_button);
        addTortaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = "Torta";
                double productCost = 40.0;

                addItemToCart(productName, productCost);
            }
        });

        Button addTacoButton = findViewById(R.id.add_taco_button);
        addTacoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = "Taco";
                double productCost = 15.0;

                addItemToCart(productName, productCost);
            }
        });

        Button addQuesadillaButton = findViewById(R.id.add_quesadilla_button);
        addQuesadillaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = "Quesadilla";
                double productCost = 30.0;

                addItemToCart(productName, productCost);
            }
        });

        Button addFlanButton = findViewById(R.id.add_flan_button);
        addFlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = "Flan";
                double productCost = 18.0;

                addItemToCart(productName, productCost);
            }
        });

        Button addVolcanButton = findViewById(R.id.add_volcan_button);
        addVolcanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = "Volcán";
                double productCost = 20.0;

                addItemToCart(productName, productCost);
            }
        });


        BottomNavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.view_cart_button) {
                    Intent cartIntent = new Intent(MenuActivity.this, CartActivity.class);
                    startActivity(cartIntent);
                    return true;
                } else if (itemId == R.id.sign_out_button) {
                    sharedPreferences.edit().clear().apply();
                    Intent signOutIntent = new Intent(MenuActivity.this, MainActivity.class);
                    startActivity(signOutIntent);
                    finish();
                    return true;
                } else if (itemId == R.id.empty_cart_button) {
                    sharedPreferences.edit().clear().apply();
                    cartItems.clear();
                    return true;
                }
                return false;
            }
        });

        // Configuración del texto de los botones del menú
        Menu menu = navigationView.getMenu();
        MenuItem viewCartButton = menu.findItem(R.id.view_cart_button);
        MenuItem signOutButton = menu.findItem(R.id.sign_out_button);
        MenuItem emptyCartButton = menu.findItem(R.id.empty_cart_button);

        viewCartButton.setTitle("Ver Carrito");
        signOutButton.setTitle("Cerrar Sesión");
        emptyCartButton.setTitle("Vaciar Carrito");
    }

    private void addItemToCart(String productName, double productCost) {
        int quantity = 1;

        if (cartItems.containsKey(productName)) {
            quantity = cartItems.get(productName) + 1;
        }
        cartItems.put(productName, quantity);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(productName, quantity);
        editor.apply();

        showAddToCartToast(productName, quantity, productCost);
    }

    private void showAddToCartToast(String productName, int quantity, double productCost) {
        String toastMessage = "Agregado al carrito: " + productName +
                "\nCantidad: " + quantity +
                "\nPrecio individual: $" + productCost +
                "\nPrecio total: $" + (quantity * productCost);

        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }
}