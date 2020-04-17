package com.rp.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StatusAdapter extends FirestoreRecyclerAdapter<StatusModelClass, StatusAdapter.StatusView> {

    public StatusAdapter(@NonNull FirestoreRecyclerOptions<StatusModelClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull StatusView statusView, int i, @NonNull StatusModelClass statusModelClass) {

        statusView.nameTV.setText(statusModelClass.getName());
        statusView.statusTV.setText(statusModelClass.getStatus());
    }

    @NonNull
    @Override
    public StatusView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new StatusView(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.single_row,parent,false
        ));
    }

    class StatusView extends RecyclerView.ViewHolder
    {
        TextView nameTV,statusTV;
        public StatusView(@NonNull View itemView) {
            super(itemView);

            nameTV=itemView.findViewById(R.id.nameTV);
            statusTV=itemView.findViewById(R.id.statusTV);
        }
    }
}
