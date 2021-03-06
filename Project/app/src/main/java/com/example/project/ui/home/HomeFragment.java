package com.example.project.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {
    View root = null;
    private HomeViewModel homeViewModel;
    private Button postBtn;
    Map<String, Object> review = new HashMap<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final TextView allHotelsTxt = root.findViewById(R.id.hotelTxt);
        FirebaseFirestore.getInstance().collection("hotels")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String AllHotelsTxt = "";
                        if (task.isSuccessful()) {
                            int i = 6;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                AllHotelsTxt = "0" + String.valueOf(i) + " Name: " + document.getData().get("name").toString() + "\nLocation:" + document.getData().get("location").toString() + "\n\n" + AllHotelsTxt;
                                i = i - 1;
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        allHotelsTxt.setText(AllHotelsTxt);
                    }
                });

        setAllReviews(root);

        Button postBtn = (Button) root.findViewById(R.id.button);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostReview(v);
                setAllReviews(v);
            }
        });

        Button deleteBtn = (Button) root.findViewById(R.id.button3);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteReview(v);
                setAllReviews(v);
            }
        });

        return root;
    }
    public void setAllReviews(View v) {
        final TextView allReviews = root.findViewById(R.id.textView7);
        FirebaseFirestore.getInstance().collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String AllReviewsTxt = "";
                        if (task.isSuccessful()) {
                            for (int i = 6; i > 0; i = i - 1) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    if (document.getData().get("0" + String.valueOf(i)) != null) {
                                        AllReviewsTxt = "Hotel#: 0" + String.valueOf(i) + " User#: " + document.getData().get("id") + "\nReview: " + document.getData().get("0" + String.valueOf(i)).toString() + "\n\n" + AllReviewsTxt;
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            }
                        }
                        allReviews.setText(AllReviewsTxt);
                    }
                });
    }

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("users/user");

    public void PostReview(View view) {
        EditText reviewTxt = root.findViewById(R.id.reviewTxt);
        String r = reviewTxt.getText().toString();
        if (r.isEmpty()) {
            return;
        }
        Spinner s = root.findViewById(R.id.spinner);
        String hotelId = s.getSelectedItem().toString();
        review.put(hotelId,r);
        mDocRef.set(review, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("userReview", "Document has been saved!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("userReview", "Document was not saved!", e);
            }
        });
        showUserReviews();
    }

    public void deleteReview(View view) {
        Spinner s = root.findViewById(R.id.spinner);
        String hotelId = s.getSelectedItem().toString();
        review.put(hotelId,FieldValue.delete());
        mDocRef.set(review, SetOptions.merge());
        showUserReviews();
    }

    public void showUserReviews(){
        final TextView userReview = root.findViewById(R.id.textView6);
        FirebaseFirestore.getInstance().collection("users")
                .whereEqualTo("id","user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String AllReviewsTxt = "";
                        if (task.isSuccessful()) {
                            for (int i = 6; i > 0; i = i - 1) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    if (document.getData().get("0" + String.valueOf(i)) != null) {
                                        AllReviewsTxt = "Hotel#: 0" + String.valueOf(i) + "\nReview: " + document.getData().get("0" + String.valueOf(i)).toString() + "\n\n" + AllReviewsTxt;
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            }
                        }
                        userReview.setText(AllReviewsTxt);
                    }
                });
    }
}

