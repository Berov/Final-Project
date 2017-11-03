package com.artgallery;

import android.os.Bundle;
import android.view.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.view.LayoutInflater;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsForSameFragment extends Fragment {


    View root;

    public ItemsForSameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_items_for_salle, container, false);
//
//
//        cancel = (Button) root.findViewById(R.id.add_item_btn_cancel);
//        upload = (Button) root.findViewById(R.id.add_item_btn_upload);
//        title = (EditText) root.findViewById(R.id.add_item_title);
//        txtPrice = (EditText) root.findViewById(R.id.add_item_price);
//        description = (EditText) root.findViewById(R.id.add_item_description);
//        author = (EditText) root.findViewById(R.id.add_item_author);
//
//        spType = (Spinner) root.findViewById(R.id.spTypeItem);
//        spSubtype = (Spinner) root.findViewById(R.id.spSubtypeItem);
//
//        //root.getContext() OR getActivity ?????
//        ArrayAdapter<CharSequence> arrTypesAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.itemTypes, android.R.layout.simple_spinner_item);
//        arrTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//
//        spType.setAdapter(arrTypesAdapter);
//        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                type = ((TextView) view).getText().toString();
//                ArrayList itemsForSale;
//                itemsForSale = testSubtypesByType(type);
//                ArrayAdapter adapter1 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, itemsForSale);
//                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spSubtype.setAdapter(adapter1);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        spSubtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                subtype = ((TextView) view).getText().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//        spSubtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                subtype = ((TextView) view).getText().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        image = (ImageView) root.findViewById(R.id.add_item_image);
//        uploadImageListener();
//
//        upload = (Button) root.findViewById(R.id.add_item_btn_upload);
////        btnUploadListener();
//
//        cancel = (Button) root.findViewById(R.id.add_item_btn_cancel);
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().onBackPressed();
//            }
//        });

        return root;
    }

//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
//            Uri imageUri = data.getData();
//            CropImage.activity(imageUri)
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setMinCropResultSize(100, 100)
//                    .setMaxCropResultSize(300, 300)
//                    .setAspectRatio(1, 1)
//                    .start(getActivity());
//        }
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//
//                Uri resultUri = result.getUri();
//
//                InputStream inputStream;
//
//                try {
//                    inputStream = getActivity().getContentResolver().openInputStream(resultUri);
//
//                    Bitmap image = BitmapFactory.decodeStream(inputStream);
//
//                    bytes = Util.getBitmapAsByteArray(image);
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                    RoundedBitmapDrawable round = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
//
//                    if (image.getWidth() > 1300 || image.getHeight() > 1300) {
//                        Toast.makeText(getActivity(), "Picture is too big!", Toast.LENGTH_LONG).show();
//                        return;
//                    }
//
//
////                    image.setImageDrawable(round);
//
//                    imageChanged = true;
//                    Toast.makeText(getActivity(), "Image changed!", Toast.LENGTH_SHORT).show();
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                    imageChanged = false;
//                    Toast.makeText(getActivity(), "Unable to open image...", Toast.LENGTH_LONG).show();
//                }
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Toast.makeText(getActivity(), "Somethings wrong with cropping...", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }



//
//
//    private void uploadImageListener() {
//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                getActivity().startActivityForResult(intent, GALLERY_REQUEST);
//            }
//        });
//    }
//
//    private ArrayList testSubtypesByType(String type) {
//
//        DBManager dbr = DBManager.getInstance(getActivity());
//        Cursor res = dbr.selectSubtypeForSpecType(type);
//
//        ArrayList<String> mStringList = new ArrayList<>();
//
//        while (res.moveToNext()) {
//            mStringList.add(res.getString(0));
//        }
//
//        ArrayList stockArr = new ArrayList();
//        stockArr = mStringList;
//
//        return stockArr;
//
//    }


    //TODO
    public interface dataFromAddItemFragment {
        void onDataPass(String data);

        void someOtherMethod(FragmentContainer container);
    }
}