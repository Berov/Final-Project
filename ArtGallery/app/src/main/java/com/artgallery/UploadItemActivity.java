package com.artgallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.artgallery.Model.DBManager;
import com.artgallery.Model.Item;
import com.artgallery.Model.User;
import com.artgallery.Util.Util;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class UploadItemActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 8;

    private Button cancel, upload;
    private EditText title, price, description, author;
    private User user;
    private View topView;
    private Spinner spType, spSubtype;
    private String type, subtype;
    private ImageView image;
    private double priceVal;
    //    private boolean isTitleTrue, isDescTrue, isAuthorTrue, isPriceTrue = false;
    private boolean isImageChanged = false;
    private byte[] imageAsBytesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_item);

        user = (User) getIntent().getSerializableExtra("USER");
        topView = findViewById(R.id.uploadItem_top_view);

        Toast.makeText(this, "Click on the camera to set your image.", Toast.LENGTH_LONG).show();

        title = (EditText) findViewById(R.id.txtTitlePreview);
        description = (EditText) findViewById(R.id.txtDescPreview);
        price = (EditText) findViewById(R.id.txtPricePreview);
        author = (EditText) findViewById(R.id.txtAuthorPreview);
        spType = (Spinner) findViewById(R.id.spTypeItem);
        spSubtype = (Spinner) findViewById(R.id.spSubtypeItem);


        topView.setPadding(Util.getScreenWidth() / 10, 0, Util.getScreenWidth() / 10, 0);

        final ArrayList<String> types = DBManager.getInstance(this).getTypes();
        ArrayAdapter<String> adapterTypes = new ArrayAdapter(UploadItemActivity.this, android.R.layout.simple_spinner_item, types);
        spType.setAdapter(adapterTypes);
        adapterTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        topView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                topView.requestFocus();
                Util.hideSoftKeyboard(UploadItemActivity.this);
                return false;
            }
        });


//        topView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                topView.requestFocus();
//                Util.hideSoftKeyboard(UploadItemActivity.this);
//                return false;
//            }
//        });

        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = ((TextView) view).getText().toString();
                final ArrayList<String> subtypes = getSubtypesByType(type);
                ArrayAdapter<String> adapterSubtypes = new ArrayAdapter(UploadItemActivity.this, android.R.layout.simple_spinner_item, subtypes);
                spSubtype.setAdapter(adapterSubtypes);
                adapterSubtypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO ??????
            }
        });

        spSubtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subtype = ((TextView) view).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        image = (ImageView) findViewById(R.id.imagePreview);
        setImageListener();

        upload = (Button) findViewById(R.id.btnSaveUpload);
        uploadListener();

        cancel = (Button) findViewById(R.id.btnCancelSave);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                    .setMinCropResultSize(100, 100)
                    .setMaxCropResultSize(300, 300)
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

                    imageAsBytesArray = Util.getBitmapAsByteArray(image);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytesArray, 0, imageAsBytesArray.length);
                    RoundedBitmapDrawable round = RoundedBitmapDrawableFactory.create(getResources(), bitmap);

                    if (image.getWidth() > 1300 || image.getHeight() > 1300) {
                        Toast.makeText(getApplicationContext(), "Picture is too big!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    this.image.setImageDrawable(round);

                    isImageChanged = true;
                    Toast.makeText(getApplicationContext(), "Image changed!", Toast.LENGTH_SHORT).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    isImageChanged = false;
                    Toast.makeText(getApplicationContext(), "Unable to open image...", Toast.LENGTH_LONG).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getApplicationContext(), "Somethings wrong with cropping...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setImageListener() {
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST);
            }
        });
    }

    private void uploadListener() {
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadItem();
            }
        });
    }

    private void uploadItem() {

        if (isValidNewItem()) {
//            Log.e("vlado","" + getSubtypeID());
//            Log.e("vlado","" + title.getText().toString());
//            Log.e("vlado","" + description.getText().toString());
//            Log.e("vlado","" +  Util.twoDecimalPlaces(Double.parseDouble(price.getText().toString())));
//            Log.e("vlado","" + author.getText().toString());
//            Log.e("vlado","" + user.getId());
//            Log.e("vlado","" + imageAsBytesArray);

            Item newItem = new Item(getSubtypeID(),
                    title.getText().toString(),
                    description.getText().toString(),
                    Util.twoDecimalPlaces(Double.parseDouble(price.getText().toString())),
                    author.getText().toString(),
                    user.getId(),
                    imageAsBytesArray);

            int imageID = DBManager.getInstance(this).addNewItem(newItem);

            if (imageID > 0) {
                Toast.makeText(this, "The new item is uploaded for sale.", Toast.LENGTH_SHORT).show();

                newItem.setId(imageID);
                user.addItemForSale(newItem);

                setResult(RESULT_OK);
                finish();

            } else {
                Toast.makeText(this, "Something wrong with the database! Try again!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Sorry! Something wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidNewItem() {

        boolean isValid = true;

        if (!isValidDescription()) {
            isValid = false;
            description.setError("Describe the item please!");
            description.requestFocus();
        }

        if (!isValidPrice()) {
            isValid = false;
            price.setError("Set price please!");
            price.requestFocus();
        }

        if (!isValidTitle()) {
            isValid = false;
            title.setError("Set title please!");
            title.requestFocus();
        }

        if (!isImageChanged) {
            isValid = false;
            Toast.makeText(this, "Select an image please!", Toast.LENGTH_LONG).show();
            topView.requestFocus();
        }

        return isValid;
    }

    private int getSubtypeID() {

        String subtype = spSubtype.getSelectedItem().toString();

        return DBManager.getInstance(UploadItemActivity.this).getSubtypeID(subtype);
    }


    private boolean isValidDescription() {

        boolean isValid = true;

        if (description.getText().toString().isEmpty() || description.getText().toString().length() < 5) {
            isValid = false;

        }

        return isValid;
    }

    private boolean isValidTitle() {

        boolean isValid = true;

        if (title.getText().toString().isEmpty() || title.getText().toString().length() < 5) {
            isValid = false;

        }

        return isValid;
    }

    private boolean isValidPrice() {

        boolean isValid = true;

        if (price.getText().toString().isEmpty() || Double.parseDouble(price.getText().toString()) < 0) {
            isValid = false;
        } else {
            price.setText(String.valueOf(Util.twoDecimalPlaces(Double.parseDouble(price.getText().toString()))));
        }

        return isValid;
    }

    private ArrayList getSubtypesByType(String type) {
        return DBManager.getInstance(this).getSubtypesByType(type);
    }
}
