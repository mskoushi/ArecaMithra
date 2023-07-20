package com.example.arecamithra;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {
    private CardView card1, price;
    CardView card3,card4;
    TextView name;
    //Weather api
CircleImageView profile_image;
    final String APP_ID = "072e4eb70c1a99d4f19558a3efd72bb7";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";
    final long MIN_TIME = 1000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;

    String Location_Provider = LocationManager.GPS_PROVIDER;
    CardView weather;
    TextView temperature, state, city,humidity;
    ImageView weather_icon;
    LocationManager mLocationManager;
    LocationListener mLocationListener;
SessionManagerWeather sessionManagerWeather;
    HashMap<String, String> weatherDetails;
     int resourceID1;
    private Context mContext;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_home, container, false);

        card1 = view.findViewById(R.id.card1);
        price = view.findViewById(R.id.price);
        card3=view.findViewById(R.id.card3);
        card4=view.findViewById(R.id.card4);
        name=view.findViewById(R.id.fullName);
profile_image=view.findViewById(R.id.profile_image);
        //Weather
        weather = view.findViewById(R.id.weather);
        temperature = weather.findViewById(R.id.temperature);
        state = weather.findViewById(R.id.state);
        city = weather.findViewById(R.id.city);
        weather_icon = weather.findViewById(R.id.weather_icon);
        humidity=weather.findViewById(R.id.humidity);


        sessionManagerWeather=new SessionManagerWeather(getActivity());
        weatherDetails = sessionManagerWeather.getWeatherDetailFromSession();


        temperature.setText(weatherDetails.get(SessionManagerWeather.KEY_TEMPERATURE));
        city.setText(weatherDetails.get(SessionManagerWeather.KEY_CITY));
        state.setText(weatherDetails.get(SessionManagerWeather.KEY_STATE));
        humidity.setText(weatherDetails.get(SessionManagerWeather.KEY_HUMIDITY));
        if(!weatherDetails.get(SessionManagerWeather.KEY_WEATHERICON).equalsIgnoreCase(" ")) {
            resourceID1 = getResources().getIdentifier(weatherDetails.get(SessionManagerWeather.KEY_WEATHERICON), "drawable", getActivity().getPackageName());
            weather_icon.setImageResource(resourceID1);
        }

        //getWeatherForCurrentLocation();
        SessionManagerUser sessionManagerUser=new SessionManagerUser(getActivity());
        HashMap<String,String> userDetails=sessionManagerUser.getUserDetailFromSession();
        String fullName=userDetails.get(SessionManagerUser.KEY_FULLNAME);

        name.setText("Hi "+fullName+",");

        String stringImage=userDetails.get(SessionManagerUser.KEY_IMAGE);
        if(!stringImage.equalsIgnoreCase(" ")){
            byte[] b= Base64.decode(stringImage,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(b,0,b.length);
            profile_image.setImageBitmap(bitmap);
        }

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(getActivity(), PestsAndManagement.class);
                startActivity(c);
            }
        });
card4.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent c=new Intent(getActivity(),DiseaseList.class);
        startActivity(c);
    }
});
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), CaptureImage.class);
                startActivity(in);
            }
        });
        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent check = new Intent(getActivity(), PriceCheck.class);
                startActivity(check);

            }
        });
        return view;

    }
    @Override
    public void onResume() {
        super.onResume();
        sessionManagerWeather=new SessionManagerWeather(getActivity());
        weatherDetails = sessionManagerWeather.getWeatherDetailFromSession();


        temperature.setText(weatherDetails.get(SessionManagerWeather.KEY_TEMPERATURE));
        city.setText(weatherDetails.get(SessionManagerWeather.KEY_CITY));
        state.setText(weatherDetails.get(SessionManagerWeather.KEY_STATE));
        humidity.setText(weatherDetails.get(SessionManagerWeather.KEY_HUMIDITY));
        if(!weatherDetails.get(SessionManagerWeather.KEY_WEATHERICON).equalsIgnoreCase(" ")) {
            resourceID1 = getResources().getIdentifier(weatherDetails.get(SessionManagerWeather.KEY_WEATHERICON), "drawable", getActivity().getPackageName());
            weather_icon.setImageResource(resourceID1);
        }
        getWeatherForCurrentLocation();
    }
    private void getWeatherForCurrentLocation() {
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());

                RequestParams params = new RequestParams();
                params.put("lat", Latitude);
                params.put("lon", Longitude);
                params.put("appid", APP_ID);
                letsdoSomeNetworking(params);


            }
        };
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Locatioget Sucessfully", Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            } else {

            }
        }
    }

    private void letsdoSomeNetworking(RequestParams params) {
        Context context=getActivity();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // super.onSuccess(statusCode, headers, response);

                //Toast.makeText(context, "Data get Success", Toast.LENGTH_SHORT).show();
                weatherData weatherD = weatherData.fromJson(response);
                updateUI(weatherD,context);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), "weather data loading failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(weatherData weatherD,Context context) {
       // sessionManagerWeather=new SessionManagerWeather(context);
        sessionManagerWeather.updateTemperature(weatherD.getmTemperature());
        sessionManagerWeather.updateCity(weatherD.getmCity());
        sessionManagerWeather.updateState(weatherD.getWeatherType());
        sessionManagerWeather.updateHumidity(weatherD.getHumidity());
        sessionManagerWeather.updateWeatherIcon(weatherD.getmIcon());

        weatherDetails = sessionManagerWeather.getWeatherDetailFromSession();


        temperature.setText(weatherDetails.get(SessionManagerWeather.KEY_TEMPERATURE));
        city.setText(weatherDetails.get(SessionManagerWeather.KEY_CITY));
        state.setText(weatherDetails.get(SessionManagerWeather.KEY_STATE));
        humidity.setText(weatherDetails.get(SessionManagerWeather.KEY_HUMIDITY));
        int resourceID = mContext.getResources().getIdentifier(weatherDetails.get(SessionManagerWeather.KEY_WEATHERICON), "drawable", mContext.getPackageName());
        weather_icon.setImageResource(resourceID);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

}