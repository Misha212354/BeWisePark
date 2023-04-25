package com.example.bewisepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.bewisepark.Model.AuthRequest;
import com.example.bewisepark.Model.types.Car;
import com.example.bewisepark.Model.types.Item;
import com.example.bewisepark.Model.types.User;
import com.example.bewisepark.Model.types.Violation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HubFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HubFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HubFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HubFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HubFragment newInstance(String param1, String param2) {
        HubFragment fragment = new HubFragment();
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
        View view = inflater.inflate(R.layout.fragment_hub, container, false);

        TextView wlcm = view.findViewById(R.id.wlcmHubTextView);
        wlcm.setText(String.format("WELCOME BACK, %s! \nENJOY YOUR DAY!", User.username));
        view.findViewById(R.id.hubToSubmitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_hubFragment_to_submitFragment);
            }
        });

        Button toView = view.findViewById(R.id.hubToViewButton);
        toView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Violation> violationList = new ArrayList<>();
                List<Car> carList = new ArrayList<>();
                Item.itemList = new ArrayList<>();
                ServiceClient serviceClient = ServiceClient.sharedServiceClient(getActivity().getApplicationContext());

                view.findViewById(R.id.progressBarHub).setVisibility(View.VISIBLE);
                toView.setEnabled(false);

                //TODO: find a better way to call these, this should be in the model layer.
                AuthRequest authRequest1 = new AuthRequest(Request.Method.GET, "https://mopsdev.bw.edu/~mterekho20/archHW/www/rest.php/cars/", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getInt("status") == 0){
                                try {
                                    Type cars = new TypeToken<ArrayList<Car>>() {}.getType();
                                    Gson gson = new Gson();
                                    List<Car> updatedCars = gson.fromJson(response.get("data").toString(), cars);
                                    carList.clear();
                                    carList.addAll(updatedCars);
                                    for(Violation violation:violationList){
                                        for(Car car:carList){
                                            int x = car.getCarId();
                                            int y = violation.getCarId();
                                            if(x == y){
                                                Item item = new Item(violation.getViolationId(), car.getCarId(), violation.getViolation_description(), car.getMake(), car.getModel(), car.getPlate_number());
                                                Item.itemList.add(item);

                                            }
                                        }
                                    }
                                    Navigation.findNavController(view).navigate(R.id.action_hubFragment_to_viewFragment);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                            }else{
                                Toast.makeText(getActivity(),"could not pull cars",Toast.LENGTH_LONG).show();
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

                AuthRequest authRequest = new AuthRequest(Request.Method.GET, "https://mopsdev.bw.edu/~mterekho20/archHW/www/rest.php/violations/", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Type violations = new TypeToken<ArrayList<Violation>>() {}.getType();
                        Gson gson = new Gson();
                        try {
                            List<Violation> updatedViolations = gson.fromJson(response.get("data").toString(), violations);
                            violationList.clear();
                            violationList.addAll(updatedViolations);
                            serviceClient.addRequest(authRequest1);

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


                serviceClient.addRequest(authRequest);



            }
        });

        return view;
    }
}