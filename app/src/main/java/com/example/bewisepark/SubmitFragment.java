package com.example.bewisepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.bewisepark.Model.AuthRequest;
import com.example.bewisepark.Model.types.Item;
import com.example.bewisepark.Model.types.User;
import com.example.bewisepark.Model.types.Violation;
import com.google.gson.Gson;

import java.util.List;


import static java.lang.Integer.parseInt;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubmitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubmitFragment extends Fragment {  // WIP, we should be able to update the carIdList
    List<Item> itemList;

    public SubmitFragment(List<Item> itemList) {
        this.itemList = itemList;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SubmitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubmitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubmitFragment newInstance(String param1, String param2) {
        SubmitFragment fragment = new SubmitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submit, container, false);
        ServiceClient serviceClient = ServiceClient.sharedServiceClient(getActivity().getApplicationContext());
        Bundle bundle = getArguments();
        EditText carIdEdit = view.findViewById(R.id.carIdField);
        EditText violationEdit = view.findViewById(R.id.violationField);

        if(bundle != null){
            String carId = bundle.getString("carId");
            carIdEdit.setText(carId);
        }


        view.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();

                String carId = carIdEdit.getText().toString();
                String violationS = violationEdit.getText().toString();


                //Check for blanks
                if(carId.isBlank() || violationS.isBlank()){
                    if(carId.isBlank()){
                        carIdEdit.setError("Id is required");
                        Toast.makeText(getActivity(),"Failed to save the violation! Please Enter Car ID",Toast.LENGTH_LONG).show();
                    }
                    else if(violationS.isBlank()) {
                        violationEdit.setError("Violation description is required");
                        Toast.makeText(getActivity(),"Failed to save the violation! Please Enter Violation Description.",Toast.LENGTH_LONG).show();
                    }
                }else{

                    Violation violation = new Violation(User.userId, parseInt(carId), violationS);

                    String violationJ = gson.toJson(violation);
                    JSONObject jsonViolation = null;
                    try {
                        jsonViolation = new JSONObject(violationJ);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    AuthRequest authRequest = new AuthRequest(Request.Method.POST, "https://mopsdev.bw.edu/~mterekho20/archHW/www/rest.php/violations/", jsonViolation, new Response.Listener<JSONObject>(){
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getInt("status") == 0){
                                    //if we got to submit from the scanning the id, by scanning the id we create a bundle.
                                    if(bundle != null){
                                        Navigation.findNavController(view).navigate(R.id.action_submitFragment_to_hubFragment);
                                    }else{
                                        Navigation.findNavController(view).navigateUp();
                                    }

                                    Toast.makeText(getActivity(), "Violation Saved", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getActivity(), "Violation Could Not Be saved", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    int x = 1;
                                }
                            });

                    //Check if the car in the database.
                    AuthRequest authRequest1 = new AuthRequest(Request.Method.GET, "https://mopsdev.bw.edu/~mterekho20/archHW/www/rest.php/cars/" + carId, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getInt("status") == 0){
                                    serviceClient.addRequest(authRequest);
                                }else{
                                    Toast.makeText(getActivity(), "Car Does Not Exist", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            int x = 1;
                        }
                    });

                    serviceClient.addRequest(authRequest1);
                }
            }
        });

        view.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigateUp();
            }
        });


        return view;
    }
}