package com.example.arecamithra;

import static com.example.arecamithra.R.color.app_secondary_color;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity  {

    static final float END_SCALE = 0.7f;
    private CardView card1, price;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    LinearLayout contentView;
//Bottom Navigation
    BottomNavigationView bnView;

    //Weather api

    final String APP_ID = "072e4eb70c1a99d4f19558a3efd72bb7";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";
    final long MIN_TIME = 20000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;

    String Location_Provider = LocationManager.GPS_PROVIDER;
    CardView weather;
    TextView temperature, state, city,humidity;
    ImageView weather_icon;
    LocationManager mLocationManager;
    LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //getWindow().setStatusBarColor(getResources().getColor(R.color.app_background));
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.app_background, getTheme()));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
//        card1 = findViewById(R.id.card1);
//        price = findViewById(R.id.price);
//Bottom Navigation
        bnView=findViewById(R.id.bottom_nav);

        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.nav_home){
                    loadFrag(new HomeFragment(),true);

                }else if(id==R.id.nav_userguide)
                {
                    loadFrag(new UserGuideFragment(),false);


                }else if(id==R.id.nav_profile){
                    loadFrag(new ProfileFragment(),false);

                }else{
loadFrag(new LogoutFragment(),false);
                }
                return true;
            }
        });
        bnView.setSelectedItemId(R.id.nav_home);
        //Drawer menu
//        drawerLayout = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.navigation_view);
//        menuIcon = findViewById(R.id.menu_icon);
//        contentView = findViewById(R.id.content);

      //  navigationDrawer();


        //Weather
//        weather = findViewById(R.id.weather);
//        temperature = weather.findViewById(R.id.temperature);
//        state = weather.findViewById(R.id.state);
//        city = weather.findViewById(R.id.city);
//        weather_icon = weather.findViewById(R.id.weather_icon);
//        humidity=weather.findViewById(R.id.humidity);


//        card1.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent in = new Intent(MainActivity.this, CaptureImage.class);
////                startActivity(in);
////            }
////        });
////
////        price.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent check = new Intent(MainActivity.this, PriceCheck.class);
////                startActivity(check);
////
////            }
////        });


    }
public void loadFrag(Fragment fragment,boolean flag) {
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    if(flag){
        ft.add(R.id.container,fragment);
    }else{
        ft.replace(R.id.container,fragment);
    }

    ft.commit();
}
  //  @Override
//    protected void onResume() {
//        super.onResume();
//        //getWeatherForCurrentLocation();
//    }

//    private void getWeatherForCurrentLocation() {
//        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        mLocationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(@NonNull Location location) {
//                String Latitude = String.valueOf(location.getLatitude());
//                String Longitude = String.valueOf(location.getLongitude());
//
//                RequestParams params = new RequestParams();
//                params.put("lat", Latitude);
//                params.put("lon", Longitude);
//                params.put("appid", APP_ID);
//                letsdoSomeNetworking(params);
//
//
//            }
//        };
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
//            return;
//        }
//        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationListener);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(MainActivity.this, "Locatioget Sucessfully", Toast.LENGTH_SHORT).show();
//                getWeatherForCurrentLocation();
//            } else {
//
//            }
//        }
//    }
//
//    private void letsdoSomeNetworking(RequestParams params) {
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // super.onSuccess(statusCode, headers, response);
//
//                Toast.makeText(MainActivity.this, "Data get Sucess", Toast.LENGTH_SHORT).show();
//                weatherData weatherD = weatherData.fromJson(response);
//                updateUI(weatherD);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                //super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//        });
//    }
//
//    private void updateUI(weatherData weatherD) {
//        temperature.setText(weatherD.getmTemperature());
//        city.setText(weatherD.getmCity());
//        state.setText(weatherD.getWeatherType());
//        humidity.setText(weatherD.getHumidity());
//        int resourceID = getResources().getIdentifier(weatherD.getmIcon(), "drawable", getPackageName());
//        weather_icon.setImageResource(resourceID);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mLocationManager != null) {
//            mLocationManager.removeUpdates(mLocationListener);
//        }
//    }
//
//    private void navigationDrawer() {
//        navigationView.bringToFront();
//        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.setCheckedItem(R.id.nav_home);
//
//        menuIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (drawerLayout.isDrawerVisible(GravityCompat.START))
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                else
//                    drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
//        animateNavigationDrawer();
//    }
//
//    private void animateNavigationDrawer() {
//        drawerLayout.setScrimColor(getResources().getColor(R.color.app_green));
//        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                // Scale the View based on current slide offset
//                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
//                final float offsetScale = 1 - diffScaledOffset;
//                contentView.setScaleX(offsetScale);
//                contentView.setScaleY(offsetScale);
//                // Translate the View, accounting for the scaled width
//                final float xOffset = drawerView.getWidth() * slideOffset;
//                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
//                final float xTranslation = xOffset - xOffsetDiff;
//                contentView.setTranslationX(xTranslation);
//
//            }
//        });
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (drawerLayout.isDrawerVisible(GravityCompat.START))
//            drawerLayout.closeDrawer(GravityCompat.START);
//        else
//            super.onBackPressed();
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        return true;
//    }
}