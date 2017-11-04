package com.artgallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.artgallery.Model.DBManager;
import com.artgallery.Model.User;
import com.artgallery.Util.Util;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {
    public static final int GALLERY_REQUEST = 3;
    private EditText name;
    private EditText address;
    private EditText password;
    private EditText confirm;
    private EditText phone;
    private User user;
    private Button update, cancel, imageBtn;
    private ImageView image;
    private byte[] bytes;
    private View topView;
    private boolean imageChanged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = (User) getIntent().getSerializableExtra("USER");
        name = (EditText) findViewById(R.id.profile_edit_text_name);
        address = (EditText) findViewById(R.id.profile_edit_text_address);
        password = (EditText) findViewById(R.id.profile_edit_text_password);
        confirm = (EditText) findViewById(R.id.profile_edit_text_confirm_password);
        phone = (EditText) findViewById(R.id.profile_edit_text_phone);
        update = (Button) findViewById(R.id.profile_btn_update);
        cancel = (Button) findViewById(R.id.profile_btn_cancel);
        imageBtn = (Button) findViewById(R.id.profile_btn_image);
        topView = findViewById(R.id.profile_top_view2);
        image = (ImageView) findViewById(R.id.profile_user_image);

        name.setText(user.getName());
        address.setText(user.getAddress());
        password.setText(user.getPassword());
        confirm.setText(user.getPassword());
        phone.setText(user.getPhoneNumber());
        topView.setPadding(Util.getScreenWidth() / 10, 0, Util.getScreenWidth() / 10, 0);

        byte[] bytes = user.getUserImageBytes();

        if (bytes == null) {

            image.setImageResource(R.mipmap.emptyprofile);
        } else {

            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            RoundedBitmapDrawable round = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            round.setCircular(true);
            image.setImageDrawable(round);
        }

        topView.requestFocus();

        topView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Util.hideSoftKeyboard(ProfileActivity.this);
            }
        });


        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST);
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.setName(name.getText().toString());
                user.setAddress(address.getText().toString());
                user.setPassword(password.getText().toString());
                user.setPhoneNumber(phone.getText().toString());

                DBManager.getInstance(ProfileActivity.this).updateUser(user);

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("USER", user);
                intent.putExtras(bundle);

                setResult(RESULT_OK, intent);
                finish();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setMinCropResultSize(100, 100)
                    .setMaxCropResultSize(400, 400)
                    .setAspectRatio(1, 1)
                    .start(this);
        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(resultUri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    bytes = Util.getBitmapAsByteArray(image);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    user.setUserImageBytes(bytes);
                    RoundedBitmapDrawable round = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                    round.setCircular(true);
                    this.image.setImageDrawable(round);
                    imageChanged = true;
                    Toast.makeText(getApplicationContext(), "Image changed!", Toast.LENGTH_SHORT).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    imageChanged = false;
                    Toast.makeText(getApplicationContext(), "Unable to open image...", Toast.LENGTH_LONG).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getApplicationContext(), "Somethings wrong with cropping...", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
