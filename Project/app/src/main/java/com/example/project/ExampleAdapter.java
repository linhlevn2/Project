package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<CreateCard> mExampleList;
    private LayoutInflater mInflater;

    public  ExampleAdapter(Context context, ArrayList<CreateCard> exampleList){
        mInflater = LayoutInflater.from(context);
        this.mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleAdapter.ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.card_view, parent,false);
        return new ExampleViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        CreateCard currentItem = mExampleList.get(position);
        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mImageView;
        final ExampleAdapter mAdapter;
        public ExampleViewHolder(View itemView, ExampleAdapter adapter){
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView7);
            mTextView2 = itemView.findViewById(R.id.textView8);
            mImageView = itemView.findViewById(R.id.imageView4);
            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
