package com.example.project.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

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

        Map<String, Object> allHotels = new HashMap<>();
        //DocumentReference mDocRef1 = FirebaseFirestore.getInstance().document("hotels");
        //CollectionReference hotels = FirebaseFirestore.getInstance().collection("hotels");
        CollectionReference hotels = FirebaseFirestore.getInstance().collection("hotels");

        final TextView allHotelsTxt = root.findViewById(R.id.hotelTxt);

        FirebaseFirestore.getInstance().collection("hotels")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String AllHotelsTxt="";
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                AllHotelsTxt = "Name: "+document.getData().get("name").toString() + "\nLocation:" + document.getData().get("location").toString() + "\n\n" +AllHotelsTxt;
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        allHotelsTxt.setText(AllHotelsTxt);
                    }
                });

                //String hotelsList = mDocRef1.getString("a");

        //TextView hotels = root.findViewById(R.id.hotelTxt);
        //hotels.setText(hotelsList);

        /*postBtn = (Button) root.findViewById(R.id.button2);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_Fetch_Update_Review(v);
            }
        });

        Button deleteBtn = (Button) root.findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deleteReview(v);
            }
        });*/

        /*View v = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<CreateCard> cardItem = new ArrayList<>();
        cardItem.add(new CreateCard(R.drawable.hotel3, "T1", "T2"));
        cardItem.add(new CreateCard(R.drawable.hotel1, "T3", "T2"));
        cardItem.add(new CreateCard(R.drawable.hotel2, "T4", "T2"));

        RecyclerView mRecyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView = v.findViewById(R.id.recyclerView2);
        mAdapter = new ExampleAdapter(this.getContext(), cardItem);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(HomeFragment, LinearLayoutManager.HORIZONTAL, false);
        mAdapter = new ExampleAdapter(cardItem);
        mRecyclerView = findViewById(R.id.recyclerView2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);*/

        return root;
    }
        /*Map<String, Object> review = new HashMap<>();
        private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("users/user");

        public void save_Fetch_Update_Review(View view) {
            EditText reviewView = root.findViewById(R.id.textReview);
            final String reviewText = reviewView.getText().toString();
            //TextView userView = root.findViewById(R.id.emailText);
            //String userEmail = userView.getText().toString();

            if (reviewText.isEmpty()) {
                return;
            }

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
                    if (documentSnapshot.exists()) {
                        String reviewGetText = documentSnapshot.getString(reviewText);
                        reviewsText.setText(reviewText + "\n");
                    }
                }
            });
        }

        public void deleteReview(View view){
            mDocRef.delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("userReview", "DocumentSnapshot successfully deleted!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("userReview", "Error deleting document", e);
                        }
                    });*/
        }

