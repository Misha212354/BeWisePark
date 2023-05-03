package com.example.bewisepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoundCarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoundCarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FoundCarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoundCarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoundCarFragment newInstance(String param1, String param2) {
        FoundCarFragment fragment = new FoundCarFragment();
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
        Bundle bundle = getArguments();
        String make = bundle.getString("make");
        String model = bundle.getString("model");
        String color = bundle.getString("color");
        String plate = bundle.getString("plate");
        String carId = bundle.getString("carId");


        View view = inflater.inflate(R.layout.fragment_found_car, container, false);

        TextView makeTV = view.findViewById(R.id.foundMake);
        TextView modelTV = view.findViewById(R.id.foundModel);
        TextView colorTV = view.findViewById(R.id.foundColor);
        TextView plateTV = view.findViewById(R.id.foundPN);
        TextView carIdTV = view.findViewById(R.id.CarID);

        carIdTV.setText(String.format("Car ID: %s", carId.toUpperCase()));
        makeTV.setText(make.toUpperCase());
        modelTV.setText(model.toUpperCase());
        colorTV.setText(color.toUpperCase());
        plateTV.setText(plate.toUpperCase());

        view.findViewById(R.id.buttonBackFound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_foundCarFragment_to_hubFragment);
            }
        });

        view.findViewById(R.id.addViolationFromFound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Navigation.findNavController(view).navigate(R.id.action_foundCarFragment_to_submitFragment, bundle);
            }
        });

        view.findViewById(R.id.backToScanButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {
                Navigation.findNavController(view).navigateUp();
            }
        });



        return view;
    }
}