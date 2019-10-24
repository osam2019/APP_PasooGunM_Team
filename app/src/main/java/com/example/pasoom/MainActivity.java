package com.example.pasoom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);
        
        setContentView(R.layout.default_screen);
    }

    public void redirectAdmin(View view)
    {
        Intent adminLogin = new Intent(this, AdminLogin.class);
        startActivity(adminLogin);

    }

    public void redirectUser(View view)
    {
        Intent userLogin = new Intent(this, UserLogin.class);
        startActivity(userLogin);

    }
}



