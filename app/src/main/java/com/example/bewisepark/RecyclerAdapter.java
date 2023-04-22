package com.example.bewisepark;


import static java.lang.Integer.parseInt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.bewisepark.Model.AuthRequest;
import com.example.bewisepark.Model.types.Item;
import com.example.bewisepark.Model.types.User;
import com.example.bewisepark.Model.types.Violation;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    // this class is responsible for taking the data we give in our view and "adapting" our recyclerView to include it

    private static final String TAG = "RecyclerAdapter";
    List<Item> itemList;
    private ServiceClient serviceClient;

    public RecyclerAdapter(List<Item> itemList, FragmentActivity fragmentActivity) {
        this.itemList = itemList;
        this.serviceClient = ServiceClient.sharedServiceClient(fragmentActivity.getApplicationContext());
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
        return itemList.size();
    }

    // this method takes in the view and puts in the data into that view. Used to map data to each defined textView in layout
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.titleTextView.setText(Integer.toString(item.getViolationId()));

        holder.rowCountTextView.setText(item.getViolation_description());
        holder.makeTextView.setText(item.getMake());
        holder.modelTextView.setText(item.getModel());
        holder.plateTextView.setText(item.getPlate_number());

        holder.rowCountEditText.setText(item.getViolation_description());
        holder.makeEditText.setText(item.getMake());
        holder.modelEditText.setText(item.getModel());
        holder.plateEditText.setText(item.getPlate_number());

        boolean isExpanded = itemList.get(position).isExpanded();  // isExpanded checks if we expanded a row
        boolean isExpendedEdit =  itemList.get(position).isExpendedEdit();

        holder.expandableLayoutEdit.setVisibility(isExpendedEdit ? View.VISIBLE : View.GONE); //FIX THIS PART
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);  // if false expanded row will be invisible and if true then it'll be visible
    }

    // this class is responsible for managing rows and keeps track of what views are present in that row
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "CarVH";

        ConstraintLayout expandableLayoutEdit, expandableLayout;
        TextView titleTextView, rowCountTextView, makeTextView, modelTextView, plateTextView;
        EditText rowCountEditText;
        TextView makeEditText, modelEditText, plateEditText;
        Button editButton, deleteButton, submitButtonRecycler, cancelButtonRecycler;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initialize textView variables and expandable layout
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            expandableLayoutEdit = itemView.findViewById(R.id.expandableLayoutEdit);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            rowCountTextView = itemView.findViewById(R.id.rowCountTextView);
            makeTextView = itemView.findViewById(R.id.makeTextView);
            modelTextView = itemView.findViewById(R.id.modelTextView);
            plateTextView = itemView.findViewById(R.id.plateTextView);

            rowCountEditText = itemView.findViewById(R.id.rowCountEditText);
            rowCountEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
            makeEditText = itemView.findViewById(R.id.makeEditText);
            modelEditText = itemView.findViewById(R.id.modelEditText);
            plateEditText = itemView.findViewById(R.id.plateEditText);




            titleTextView.setOnClickListener(new View.OnClickListener() {  // expands the row

                @Override
                public void onClick(View view) {
                    Item item = itemList.get(getAdapterPosition());
                    if(item.isExpendedEdit() != true){
                        item.setExpanded(!item.isExpanded());
                        notifyItemChanged(getAdapterPosition());
                    }else{
                        Toast.makeText(view.getContext(), "Must Submit",Toast.LENGTH_SHORT).show();
                    }

                }
            });

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            submitButtonRecycler = itemView.findViewById(R.id.submitButtonRecycler);
            cancelButtonRecycler = itemView.findViewById(R.id.cancelButtonRecycler2);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Item item = itemList.get(getAdapterPosition());
                    item.setExpanded(!item.isExpanded());
                    item.setExpendedEdit(!item.isExpendedEdit());
                    notifyItemChanged(getAdapterPosition());





                }
            });

            submitButtonRecycler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Item item = itemList.get(position);
                    item.setExpendedEdit(!item.isExpendedEdit());

                    Gson gson = new Gson();

                    Violation violation = new Violation(User.userId, item.getCarId(), item.getViolationId(), rowCountEditText.getText().toString());
                    String violationJ = gson.toJson(violation);
                    JSONObject jsonViolation = null;
                    try {
                        jsonViolation = new JSONObject(violationJ);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    AuthRequest authRequest = new AuthRequest(Request.Method.PUT, "https://mopsdev.bw.edu/~mterekho20/archHW/www/rest.php/violations/", jsonViolation, new Response.Listener<JSONObject>(){
                        public void onResponse(JSONObject response) {
                            //TODO:check if it was not changed. It should return json anyway which it will consider as a proper response.
                            item.setViolation_description(rowCountEditText.getText().toString());
                            notifyItemChanged(getAdapterPosition());
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    int x = 1;
                                }
                            });

                    serviceClient.addRequest(authRequest);




                }
            });

            cancelButtonRecycler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Item item = itemList.get(getAdapterPosition());
                    item.setExpanded(!item.isExpanded());
                    item.setExpendedEdit(!item.isExpendedEdit());
                    notifyItemChanged(getAdapterPosition());
                }
            });


            deleteButton.setOnClickListener(new View.OnClickListener() {  // deletes expanded row
                @Override
                public void onClick(View view3) {
//                    itemList.remove(getAdapterPosition());
//                    notifyItemRemoved(getAdapterPosition());
                    int position = getAdapterPosition();
                    Item item = itemList.get(position);


                    AuthRequest authRequest = new AuthRequest(Request.Method.DELETE, "https://mopsdev.bw.edu/~mterekho20/archHW/www/rest.php/violations/" + Integer.toString(item.getViolationId()), null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //TODO:check if it was not deleted. It should return json anyway which it will consider as a proper response.
                            itemList.remove(position);
                            notifyItemRemoved(position);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    serviceClient.addRequest(authRequest);

                }
            });

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), (CharSequence) itemList.get(getAdapterPosition()),Toast.LENGTH_SHORT).show();
        }
    }
}

