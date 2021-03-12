package com.CA326MyBubble.App_Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertisingSet;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.CA326MyBubble.App_Constructors.AddrsArray;
import com.CA326MyBubble.Layout_Models.newsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.CA326MyBubble.R;
import com.CA326MyBubble.App_Interfaces.FragListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import static com.CA326MyBubble.App_Utilities.Utilities.INTENT;
import static com.CA326MyBubble.App_Utilities.Utilities.LOCATION;
import static com.CA326MyBubble.App_Utilities.Utilities.TYPE;
import static com.CA326MyBubble.App_Utilities.Utilities.COUNTRYSELECT;
import static com.CA326MyBubble.App_Utilities.Utilities.SLIDER_INTENT;

public class MainActivity extends AppCompatActivity implements FragListener {
    private FirebaseAuth mAuth;
    public static final String LOG_TAG = "Tag: ";
    AdvertisingSet currentAdvertisingSet;

    // BT used for gaining permissions before starting ScannerService
    BluetoothManager mBluetoothManager;
    BluetoothAdapter mBluetoothAdapter;


    private final static int REQUEST_ENABLE_BT = 1;
    private final static int PERMISSION_REQUEST_FINE_LOCATION = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration;

            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_dashboard, R.id.navigation_info, R.id.navigation_news, R.id.navigation_bubble)
                    .build();




        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(navView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {


        });
        // Asking for BT permission
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("This app needs location access");
            builder.setMessage("Please grant location access so this app can detect peripherals.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_FINE_LOCATION);
                }
            });
            builder.show();
        }
        // Adding all BT MAC Addresses to an ArrayList to be used elsewhere
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference usersRef = rootRef.collection("Emails");
        usersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        AddrsArray.btAddrs.add(document.get("BT_Add").toString());
                    }
                }
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

            Intent it = new Intent(MainActivity.this, com.CA326MyBubble.App_Activities.SettingsActivity.class);
            startActivity(it);
            overridePendingTransition(0,0);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            sendToLogin();
        }
    }

    private void sendToLogin() {
        Intent loginintent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginintent);
        finish();
    }

    @Override
    public void getListIntent(String intent, String arguement) {
        if (intent.equals(INTENT))
        {
            Intent it = new Intent(MainActivity.this, CountriesActivity.class);
            it.putExtra(LOCATION, arguement);
            it.putExtra(TYPE, COUNTRYSELECT);
            startActivity(it);
            overridePendingTransition(0,0);
        }
        if (intent.equals(SLIDER_INTENT))
        {
            Intent it = new Intent(MainActivity.this, com.CA326MyBubble.App_Activities.SliderActivity.class);
            it.putExtra("sliderRequest", arguement);
            startActivity(it);
            overridePendingTransition(0,0);
        }


    }

    @Override
    public void getNewIntent(newsModel newsModel) {
        Intent it = new Intent(MainActivity.this, NewsInfoActivity.class);
        it.putExtra(NewsInfoActivity.PARCELABLE_PARSING_DATA, newsModel);
        startActivity(it);
        overridePendingTransition(0,0);
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }
}
