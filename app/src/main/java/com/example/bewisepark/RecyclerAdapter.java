package com.example.bewisepark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    // this class is responsible for taking the data we give in our view and "adapting" our recyclerView to include it

    private static final String TAG = "RecyclerAdapter";
    List<Car> carIdList;

    public RecyclerAdapter(List<Car> carIdList) {
        this.carIdList = carIdList;
    }

    // creates rows and maps items in list to those rows
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // this method represents the number of rows in recyclerView. For example, if cardIdList.size() == 20, there will be 20 rows
    @Override
    public int getItemCount() {
        return carIdList.size();
    }

    // this method takes in the view and puts in the data into that view. Used to map data to each defined textView in layout
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = carIdList.get(position);
        holder.titleTextView.setText(car.getId());
        holder.rowCountTextView.setText(car.getViolation());

        boolean isExpanded = carIdList.get(position).isExpanded();  // isExpanded checks if we expanded a row
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);  // if false expanded row will be invisible and if true then it'll be visible
    }

    // this class is responsible for managing rows and keeps track of what views are present in that row
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "CarVH";

        TextView titleTextView, rowCountTextView;
        ConstraintLayout expandableLayout;
        Button editButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initialize textView variables and expandable layout
            titleTextView = itemView.findViewById(R.id.titleTextView);
            rowCountTextView = itemView.findViewById(R.id.rowCountTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);

            titleTextView.setOnClickListener(new View.OnClickListener() {  // expands the row
                @Override
                public void onClick(View view) {
                    Car car = carIdList.get(getAdapterPosition());
                    car.setExpanded(!car.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(new View.OnClickListener() {  // deletes expanded row
                @Override
                public void onClick(View view3) {
                    carIdList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {  // sends used to edit page, with selected ID present at top. Send through bundle.
                @Override
                public void onClick(View view2) {
                    Car car = carIdList.get(getAdapterPosition());
                    String carId = car.getId();
                    String violation = car.getViolation();


                    Bundle bundle = new Bundle();
                    bundle.putString("carId", carId);
                    bundle.putString("violation", violation);

                    Navigation.findNavController(view2).navigate(R.id.action_viewFragment_to_editFragment, bundle);
                }
            });

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), (CharSequence) carIdList.get(getAdapterPosition()),Toast.LENGTH_SHORT).show();
        }
    }
}

