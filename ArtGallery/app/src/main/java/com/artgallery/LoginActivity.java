package com.artgallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.artgallery.Model.DBManager;
import com.artgallery.Model.User;
import com.artgallery.Util.Util;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private View topView;
    private ImageView logo;
    private static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button) findViewById(R.id.login_btn_login);
        Button register = (Button) findViewById(R.id.login_btn_register);

        email = (EditText) findViewById(R.id.login_edit_text_email);
        password = (EditText) findViewById(R.id.login_edit_text_password);
        topView = findViewById(R.id.login_top_view);
        logo = (ImageView) findViewById(R.id.login_image_view_logo);

        topView.setPadding(Util.getScreenWidth() / 10, Util.getScreenHeight() / 10, Util.getScreenWidth() / 10, Util.getScreenHeight() / 10);
        logo.getLayoutParams().height = Util.getScreenHeight() / 8;


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user;

                if (DBManager.getInstance(LoginActivity.this).isValidLogin(email.getText().toString(), password.getText().toString())) {
                    user = DBManager.getInstance(LoginActivity.this).getUser(email.getText().toString(), password.getText().toString());

                    if (user == null) {

                        Toast.makeText(LoginActivity.this, "Mysterious mistake!\nMay be a hack or no internet connection!", Toast.LENGTH_SHORT).show();

                        return;
                    }

                    addUserItems(user);

                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("USER", user);
                    intent.putExtras(bundle);
                    startActivity(intent);

                    finish();
                } else {

                    Toast.makeText(LoginActivity.this, R.string.WrongEmailOrPassword, Toast.LENGTH_SHORT).show();
                }
            }
        });


        topView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Util.hideSoftKeyboard(LoginActivity.this);

                return false;
            }
        });
    }


    private void addUserItems(User user) {

        user.setIUserItems(DBManager.getInstance(this).getUserItems(user.getId()));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            String emailMessage = data.getStringExtra("EMAIL");
            String passwordMessage = data.getStringExtra("PASSWORD");

            email.setText(emailMessage);
            password.setText(passwordMessage);
        }
    }
}
