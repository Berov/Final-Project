package com.artgallery;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.artgallery.Model.DBManager;
import com.artgallery.Model.User;
import com.artgallery.Util.Util;
import com.artgallery.Util.Validator;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText name;
    private EditText password;
    private EditText password2;
    private EditText address;
    private EditText phoneNumber;
    private CheckBox agreeConditions;
    private View registerLayout, topView;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button register = (Button) findViewById(R.id.register_btn_register);
        Button cancel = (Button) findViewById(R.id.register_btn_cancel);
        TextView terms = (TextView) findViewById(R.id.register_terms_and_conditions);
        email = (EditText) findViewById(R.id.register_edit_text_email);
        name = (EditText) findViewById(R.id.register_edit_text_name);
        password = (EditText) findViewById(R.id.register_edit_text_password);
        password2 = (EditText) findViewById(R.id.register_edit_text_confirm_password);
        address = (EditText) findViewById(R.id.register_edit_text_address);
        phoneNumber = (EditText) findViewById(R.id.register_edit_text_phone_number);
        agreeConditions = (CheckBox) findViewById(R.id.register_checkbox_agree_conditions);
        registerLayout = findViewById(R.id.register_scroll_view);
        topView = findViewById(R.id.register_layout);

        topView.setPadding(Util.getScreenWidth() / 10, 0, Util.getScreenWidth() / 10, 0);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isUserDataValid()) {
                    String setName = name.getText().toString();
                    String setPassword = password.getText().toString();
                    String setEmail = email.getText().toString();
                    String setPhone = phoneNumber.getText().toString();
                    String setAddress = address.getText().toString();

                    User u = new User(setName, setEmail, setPassword, setPhone, setAddress, 0);
                    DBManager.getInstance(RegisterActivity.this).addUser(u);
                    Toast.makeText(RegisterActivity.this, R.string.RegistrationSuccess, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.putExtra("EMAIL", setEmail);
                    intent.putExtra("PASSWORD", setPassword);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                Toast.makeText(RegisterActivity.this, R.string.RegistrationCanceled, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Util.hideSoftKeyboard(RegisterActivity.this);

                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.terms, null);
                popupWindow = new PopupWindow(container, Util.getScreenWidth() / 10 * 8, Util.getScreenHeight() / 10 * 8, true);
//                popupWindow.showAtLocation(registerLayout, Gravity.NO_GRAVITY, Util.getScreenWidth() / 10, Util.getScreenHeight() / 10);
                popupWindow.showAtLocation(registerLayout, Gravity.CENTER, 0, 0);
                Button agree = (Button) container.findViewById(R.id.terms_btn_agree);
                Util.dimBehind(popupWindow);

                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });


                agree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        agreeConditions.setChecked(true);
                        popupWindow.dismiss();
                    }
                });

            }
        });

        topView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Util.hideSoftKeyboard(RegisterActivity.this);
                return false;
            }
        });

//        topView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Util.hideSoftKeyboard(RegisterActivity.this);
//            }
//        });

//        topView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Util.hideSoftKeyboard(RegisterActivity.this);
//                return false;
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        findViewById(R.id.register_scroll_view).setBackgroundColor(ColorUtils.setAlphaComponent(Color.argb(1, 1, 1, 1), 0));
    }

    private boolean isUserDataValid() {
        boolean isValid = true;

        if (!agreeConditions.isChecked()) {
            isValid = false;
            View view = findViewById(R.id.register_layout);
            Snackbar.make(view, R.string.haveToAgreeTerms, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            agreeConditions.requestFocus();
        }

        if (!Validator.isValidPhoneNumber(phoneNumber.getText().toString())) {
            phoneNumber.setError(getString(R.string.phoneConditions));
            isValid = false;
            phoneNumber.requestFocus();
        }

        if (address.getText().toString().isEmpty()) {
            address.setError(getString(R.string.addAddress));
            isValid = false;
            address.requestFocus();
        }

        if (!password.getText().toString().matches(password2.getText().toString())) {
            password2.setError(getString(R.string.invalidPasswordConfirmation));
            isValid = false;
            password2.requestFocus();
        }

        if (!Validator.isValidPassword(password.getText().toString())) {
            password.setError(getString(R.string.passwordConditions));
            isValid = false;
            password.requestFocus();
        }

        if (!Validator.isValidName(name.getText().toString())) {
            name.setError(getString(R.string.enterName));
            isValid = false;
            name.requestFocus();
        }

        if (!Validator.isValidEmail(email.getText().toString())) {
            email.setError(getString(R.string.invalidEmail));
            isValid = false;
            email.requestFocus();
        }

        if (DBManager.getInstance(RegisterActivity.this).userExists(email.getText().toString())) {
            isValid = false;
            email.setError(getString(R.string.registeredEmail));
            email.requestFocus();
        }

        return isValid;
    }
}
