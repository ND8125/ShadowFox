package com.example.simple_login_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Signup extends AppCompatActivity {
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText username=findViewById(R.id.username);
        EditText password=findViewById(R.id.password);
        Button signup = findViewById(R.id.signup);
        db = new Database(this);

       String preusername= getIntent().getStringExtra("USERNAME");
       if (preusername != null)username.setText(preusername);

       signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username=username.getText().toString().trim();
                String Password = password.getText().toString().trim();

                if (Username.isEmpty() || Password.isEmpty()){
                    Toast.makeText(Signup.this, "plz Fill the all fields", Toast.LENGTH_SHORT).show();
                } else if (Password.length()<6){
                    Toast.makeText(Signup.this, "Password Must be 6 characters", Toast.LENGTH_SHORT).show();
                } else if (db.checkuser(Username)){
                    Toast.makeText(Signup.this, "User Already Exist", Toast.LENGTH_SHORT).show();
                } else {
                    db.Insertuser(Username,Password);
                    Toast.makeText(Signup.this, "Signup Succesfull , plz Login", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Signup.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}