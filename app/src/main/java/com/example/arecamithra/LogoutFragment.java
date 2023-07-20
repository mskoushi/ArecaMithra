package com.example.arecamithra;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LogoutFragment extends Fragment {
Button logout;

    public LogoutFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        logout=view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagerUser sessionManagerUser=new SessionManagerUser(getActivity());
                sessionManagerUser.logoutUserFromSession();
                SessionManagerWeather sessionManagerWeather=new SessionManagerWeather(getActivity());
                sessionManagerWeather.logoutWeatherFromSession();
                Intent i=new Intent(getActivity(),Login.class);
                startActivity(i);
            }
        });


        return view;
    }
}