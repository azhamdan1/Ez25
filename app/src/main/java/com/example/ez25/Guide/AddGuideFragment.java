package com.example.ez25.Guide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ez25.HomeFragment;
import com.example.ez25.R;
import com.example.ez25.Servicies.FirebaseServices;
import com.example.ez25.Servicies.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddGuideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddGuideFragment extends Fragment {
    private static final int GALLERY_REQUEST_CODE = 123;
    private FirebaseServices fbs;
    private EditText etTittle, etDescription;
    private ImageView ivShow;
    private Utils utils;
    private Button btnAdd;
    private ArrayList<Guide> guides;
    private ArrayList<Uri> photo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddGuideFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddGuideFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddGuideFragment newInstance(String param1, String param2) {
        AddGuideFragment fragment = new AddGuideFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_guide, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        connectComponents();

    }

    private void connectComponents() {
        fbs = FirebaseServices.getInstance();
        etTittle = getView().findViewById(R.id.etTittleAddGuide);
        utils = Utils.getInstance();
        etDescription = getView().findViewById(R.id.etDescriptionAddGuide);
        ivShow = getView().findViewById(R.id.ivShowAddGuide);
        btnAdd = getView().findViewById(R.id.btnAddAddProductFragment);

        ivShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAdd.setText("Uploading ...");
                btnAdd.setEnabled(false);
                if (fbs.getSelectedImageURL() == null) {
                    Toast.makeText(getActivity(), "The picture is missing", Toast.LENGTH_SHORT).show();
                    return;
                }
                // get data from screen
                String tittle = etTittle.getText().toString();
                String description = etDescription.getText().toString();
                String productID = UUID.randomUUID().toString();
                if (tittle.trim().isEmpty()
                        || description.trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Some fields are empty!", Toast.LENGTH_LONG).show();
                    btnAdd.setText("Add");
                    btnAdd.setEnabled(true);
                    return;
                }
                DocumentReference documentReference = fbs.getFire().collection("guides").document(productID);
                Map<String, Object> productMap = new HashMap<>();
                productMap.put("description", description);
                productMap.put("photo", fbs.getSelectedImageURL().toString());
                productMap.put("tittle", tittle);
                documentReference.set(productMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to add product", Toast.LENGTH_SHORT).show();
                        btnAdd.setText("Add");
                        btnAdd.setEnabled(true);
                    }
                });

                //RatingBar productRatingBar = getView().findViewById(R.id.rbProductProductDetails);
                float rating = 0;//productRatingBar.getRating();
                // data validation


                // add data to firestore
                Guide Guide = new Guide(fbs.getSelectedImageURL().toString(), tittle, description);


                fbs.getFire().collection("guides").add(Guide).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Successfully added your product!", Toast.LENGTH_SHORT).show();
                        // gotoHomeFragment
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frameLayoutMain, new HomeFragment());
                        ft.commit();


                        fbs.getFire().collection("guides").add(Guide).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Failure AddGuide: ", e.getMessage());
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Failure AddGuide: ", e.getMessage());
                    }
                });
            }

        });
        btnAdd.setText("Add");
        btnAdd.setEnabled(true);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            ivShow.setImageURI(selectedImageUri);
            utils.uploadImage(getActivity(), selectedImageUri);
        }
    }


}

