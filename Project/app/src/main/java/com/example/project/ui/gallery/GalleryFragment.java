package com.example.project.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    private View root, v;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        v = inflater.inflate(R.layout.fragment_slideshow, container, false);
        Button updateBtn = v.findViewById(R.id.button);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Preference updated!", Toast.LENGTH_LONG).show();
            }
        });

        Boolean gym = false, spa = false, pool = false;
        CheckBox spaBox = (CheckBox) v.findViewById(R.id.spaBox);
        if (spaBox.isChecked()) {
            spa = true;
        }
        CheckBox gymBox = (CheckBox) v.findViewById(R.id.gymBox);
        if (gymBox.isChecked()) {
            gym = true;
        }
        CheckBox poolBox = (CheckBox) v.findViewById(R.id.poolBox);
        if (poolBox.isChecked()) {
            pool = true;
        }
        EditText distanceTxt = v.findViewById(R.id.distanceTxt);
        String distance = (distanceTxt.getText().toString());
        EditText priceTxt = v.findViewById(R.id.priceTxt);
        String price = (priceTxt.getText().toString());

        final TextView spaHotels = root.findViewById(R.id.spaTxt);
        final TextView gymHotels = root.findViewById(R.id.gymTxt);
        final TextView poolHotels = root.findViewById(R.id.poolTxt);
        final TextView priceHotels = root.findViewById(R.id.priceText);
        final TextView distanceHotels = root.findViewById(R.id.distanceText);

        FirebaseFirestore.getInstance().collection("hotels")
                .whereEqualTo("gym", gym)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String allSpaHotels = "";
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                allSpaHotels = "Name: " + document.getData().get("name").toString() + "\nLocation: " + document.getData().get("location").toString() + "\n\n" + allSpaHotels;
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        spaHotels.setText(allSpaHotels);
                    }
                });

        FirebaseFirestore.getInstance().collection("hotels")
                .whereEqualTo("spa", spa)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String allGymHotels = "";
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                allGymHotels = "Name: " + document.getData().get("name").toString() + "\nLocation: " + document.getData().get("location").toString() + "\n\n" + allGymHotels;
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        gymHotels.setText(allGymHotels);
                    }
                });

        FirebaseFirestore.getInstance().collection("hotels")
                .whereEqualTo("pool", pool)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String allPoolHotels = "";
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                allPoolHotels = "Name: " + document.getData().get("name").toString() + "\nLocation: " + document.getData().get("location").toString() + "\n\n" + allPoolHotels;
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        poolHotels.setText(allPoolHotels);
                    }
                });

        FirebaseFirestore.getInstance().collection("hotels")
                .whereLessThan("price", price)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String allPriceHotels = "";
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                allPriceHotels = "Name: " + document.getData().get("name").toString() + "\nLocation: " + document.getData().get("location").toString() + "\n\n" + allPriceHotels;
                            }
                            priceHotels.setText(allPriceHotels);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            priceHotels.setText("no hotel");
                        }

                    }
                });

        FirebaseFirestore.getInstance().collection("hotels")
                .whereLessThan("distance", distance)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String allDistanceHotels = "";
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                allDistanceHotels = "Name: " + document.getData().get("name").toString() + "\nLocation: " + document.getData().get("location").toString() + "\n\n" + allDistanceHotels;
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        distanceHotels.setText(allDistanceHotels);
                    }
                });
        return root;
    }
}