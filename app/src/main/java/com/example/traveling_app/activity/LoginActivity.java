package com.example.traveling_app.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.traveling_app.R;
import com.example.traveling_app.common.DatabaseReferences;
import com.example.traveling_app.model.user.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    ImageView fbBtn;
    ImageView ggBtn;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://travelapp-31533-default-rtdb.firebaseio.com/");
    TextView quenpass;
    FirebaseAuth auth;
    EditText name;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        fbBtn = findViewById(R.id.icon_fb);
        ggBtn = findViewById(R.id.icon_gg);

        email = findViewById(R.id.dangnhap_email);
        final EditText password = findViewById(R.id.dangnhap_matkhau);
        final Button loginbtn = findViewById(R.id.at2_btn1);

        callbackManager = CallbackManager.Factory.create();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String emailtxt = email.getText().toString();
                final String passwordtxt = password.getText().toString();

                if (emailtxt.isEmpty() || passwordtxt.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    Query query = databaseReference.child("users").child(emailtxt);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
//                                Log.d("key", snapshot.toString());
                                User info = snapshot.getValue(User.class);
                                info.setUsername(snapshot.getKey());
                                if (info.getPassword().equals(passwordtxt)) {
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("user",info);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.baseline_keyboard_backspace_24); // Thay thế ic_arrow_back bằng ID của hình ảnh của bạn


        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        // Hiển thị nút quay lại trên Action Bar
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        quenpass= (TextView) findViewById(R.id.dangnhap_quenpass);
        quenpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent2 = new Intent(getApplicationContext(), ForgotPassActivity.class);
                startActivity(myintent2);
            }
        });

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Hoặc thực hiện hành động cụ thể khi nút quay lại được nhấn
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(LoginActivity.this, Login_google.class);
        startActivity(intent);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(LoginActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }
}