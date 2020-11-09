package com.example.project.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    View root = null;
    private HomeViewModel homeViewModel;
    private Button postBtn;
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
        postBtn = (Button) root.findViewById(R.id.button2);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_Fetch_Update_Info(v);
            }
        });
        return root;
    }
        Map<String, Object> review = new HashMap<>();
        private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("users/user");
        public void save_Fetch_Update_Info(View view){
            EditText reviewView = root.findViewById(R.id.textReview);
            final String reviewText = reviewView.getText().toString();
            //TextView userView = root.findViewById(R.id.emailText);
            //String userEmail = userView.getText().toString();

            if (reviewText.isEmpty()) {return;}

            review.put("user", reviewText);

            mDocRef.set(review).addOnSuccessListener(new OnSuccessListener<Void>() {
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

            final TextView reviewsText = root.findViewById(R.id.reviews);
            mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        String reviewGetText = documentSnapshot.getString(reviewText);
                        reviewsText.setText(reviewText+"\n");
                    }
                }
            });


        }

}