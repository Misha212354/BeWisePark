package com.example.recyclerviewexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final String TAG = "RecyclerAdapter";
    List<Car> carIdList;

    public RecyclerAdapter(List<Car> carIdList) {
        this.carIdList = carIdList;
    }

    // this class is the only class responsible for displaying items in recyclerView
    // creates rows and maps items in list to those rows
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // this method represents the number of rows in recyclerView. For example, we have 20
    @Override
    public int getItemCount() {
        return carIdList.size();
    }

    // this method takes in the view and puts in the data into that view. Used to map data
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = carIdList.get(position);
        holder.titleTextView.setText(car.getId());
        holder.rowCountTextView.setText(car.getViolation());

        boolean isExpanded = carIdList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    // this class is responsible for managing rows and keeps track of what views are present in that row
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "CarVH";

        TextView titleTextView, rowCountTextView;
        ConstraintLayout expandableLayout;
        Button testButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            rowCountTextView = itemView.findViewById(R.id.rowCountTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);

            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Car car = carIdList.get(getAdapterPosition());
                    car.setExpanded(!car.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            testButton = itemView.findViewById(R.id.testButton);

            testButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view2) {
                    Toast.makeText(view2.getContext(), "It worked", Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), (CharSequence) carIdList.get(getAdapterPosition()),Toast.LENGTH_SHORT).show();
        }
    }
}
