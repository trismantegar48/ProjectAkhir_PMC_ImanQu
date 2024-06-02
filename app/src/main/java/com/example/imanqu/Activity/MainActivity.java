package com.example.imanqu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;

import com.example.imanqu.Fragment.AboutFragment;
import com.example.imanqu.Fragment.HomeFragment;
import com.example.imanqu.Fragment.ProfileFragment;
import com.example.imanqu.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView imageView;
    private FragmentContainerView fragment_container;
    private LinearLayout main_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.openmenu);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        main_container = findViewById(R.id.main_container);
        fragment_container = findViewById(R.id.fragment_container);

        // Set onClickListener untuk imageView (openmenu)
        imageView.setOnClickListener(view -> {
            // Aksi yang diinginkan saat imageView diklik
            Toast.makeText(MainActivity.this, "Menu clicked", Toast.LENGTH_SHORT).show();
            drawerLayout.open();
        });

        // Cek apakah pengguna sudah login
        if (!isLoggedIn()) {
            // Jika belum login, arahkan ke LoginActivity
            goToLoginActivity();
            return;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
        navigationView.setCheckedItem(R.id.nav_home); // digunakan untuk menandai navigation drawer Home

        // NavigationView
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {  // untuk berpindah ke HomeFragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new HomeFragment())
                            .commit();
                } else if (itemId == R.id.nav_profile) { // untuk berpindah ke ProfileFragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new ProfileFragment())
                            .commit();
                } else if (itemId == R.id.nav_about) { // untuk berpindah ke AboutFragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new AboutFragment())
                            .commit();
                } else if (itemId == R.id.nav_logout) { // untuk Logout
                    showLogoutConfirmationDialog();
                }
                drawerLayout.close();
                return true;
            }
        });
    }

    // Method untuk mengecek status login
    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    // Method untuk navigasi ke LoginActivity
    private void goToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // Method untuk logout tanpa menghapus nama pengguna dan kata sandi
    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Method untuk menampilkan dialog konfirmasi logout
    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Apakah Anda yakin untuk keluar?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                })
                .setNegativeButton("Tidak", null)
                .show();
    }
}
