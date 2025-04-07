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

public class MainActivity extends AppCompatActivity {

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText username=findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        Button signup = findViewById(R.id.signup);

        db=new Database(this);

       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String Username=username.getText().toString().trim();
               String Password = password.getText().toString().trim();

               if (Username.isEmpty() || Password.isEmpty()){
                   Toast.makeText(MainActivity.this, "plz Fill the all fields", Toast.LENGTH_SHORT).show();
               } else if (Password.length() < 6) {
                   Toast.makeText(MainActivity.this, "Password Must be 6 characters", Toast.LENGTH_SHORT).show();
               } else if (db.validateuser(Username,Password)){
                   Intent intent = new Intent(MainActivity.this, Welcome.class);
                   intent.putExtra("username",Username);
                   startActivity(intent);
               } else if (!db.checkuser(Username)){
                   Toast.makeText(MainActivity.this, "User not found plz sign up first", Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(MainActivity.this, "Password is incorrect, plz enter a valid password", Toast.LENGTH_SHORT).show();
               }
           }
       });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
            }
        });
    }
}