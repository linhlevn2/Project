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
    public String allSpaHotels="", allGymHotels="", allPoolHotels="",allPriceHotels = "",allDistanceHotels = "";
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

        Button updateBtn = root.findViewById(R.id.button2);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView spaHotels = root.findViewById(R.id.spaTxt);
                final TextView gymHotels = root.findViewById(R.id.gymTxt);
                final TextView poolHotels = root.findViewById(R.id.poolTxt);
                final TextView priceHotels = root.findViewById(R.id.priceText);
                final TextView distanceHotels = root.findViewById(R.id.distanceText);

                CheckBox gymBox = (CheckBox) root.findViewById(R.id.gymBox);
                if (gymBox.isChecked()) {
                    FirebaseFirestore.getInstance().collection("hotels")
                            .whereEqualTo("gym", true)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                            allGymHotels = "Name: " + document.getData().get("name").toString() + "\nLocation: " + document.getData().get("location").toString() + "\n\n" + allGymHotels;
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                    gymHotels.setText(allGymHotels);
                                    allGymHotels="";
                                }
                            });
                } else {
                    gymHotels.setText("There is no hotel that has gym or you did not set your preference for this category");
                }

                CheckBox spaBox = (CheckBox) root.findViewById(R.id.spaBox);
                if (spaBox.isChecked()) {
                    FirebaseFirestore.getInstance().collection("hotels")
                            .whereEqualTo("spa", true)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                            allSpaHotels = "Name: " + document.getData().get("name").toString() + "\nLocation: " + document.getData().get("location").toString() + "\n\n" + allSpaHotels;
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                    spaHotels.setText(allSpaHotels);
                                    allSpaHotels="";
                                }
                            });
                } else {
                    spaHotels.setText("There is no hotel that has spa or you did not set your preference for this category");
                }

                CheckBox poolBox = (CheckBox) root.findViewById(R.id.poolBox);
                if (poolBox.isChecked()) {
                    FirebaseFirestore.getInstance().collection("hotels")
                            .whereEqualTo("pool", true)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                            allPoolHotels = "Name: " + document.getData().get("name").toString() + "\nLocation: " + document.getData().get("location").toString() + "\n\n" + allPoolHotels;
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                    poolHotels.setText(allPoolHotels);
                                    allPoolHotels="";
                                }
                            });
                } else {
                    poolHotels.setText("There is no hotel that has pool or you did not set your preference for this category");
                }

                EditText priceTxt = root.findViewById(R.id.priceTxt);
                String p = priceTxt.getText().toString();
                if(!p.equals("") && Integer.parseInt(p)>23) {
                    FirebaseFirestore.getInstance().collection("hotels")
                            .whereLessThan("price", Integer.parseInt(p))
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                            allPriceHotels = "Name: " + document.getData().get("name").toString() + "\nLocation: " + document.getData().get("location").toString() + "\n\n" + allPriceHotels;
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                    priceHotels.setText(allPriceHotels);
                                    allPriceHotels = "";
                                }
                            });
                } else {
                    priceHotels.setText("There is no hotel that is within your price range or you did not set your preference for this category");
                }

                EditText distanceTxt = root.findViewById(R.id.distanceTxt);
                String distance = (distanceTxt.getText().toString());
                if(!distance.equals("") && Integer.parseInt(distance)>0) {
                    FirebaseFirestore.getInstance().collection("hotels")
                            .whereLessThan("distance", Integer.parseInt(distance))
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                            allDistanceHotels = "Name: " + document.getData().get("name").toString() + "\nLocation: " + document.getData().get("location").toString() + "\n\n" + allDistanceHotels;
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                    distanceHotels.setText(allDistanceHotels);
                                    allDistanceHotels="";
                                }
                            });
                } else {
                    distanceHotels.setText("There is no hotel that is within preferred distance or you did not set your preference for this category");
                }
            }
        });
        return root;
    }
}