package com.example.bewisepark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewFragment extends Fragment {  // this fragment contains our list of ids, each of which has a dropdown menu containing more info and a button

    private RecyclerView recyclerView;  // these two lines define our recyclerView and recyclerAdapter
    private RecyclerAdapter recyclerAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewFragment newInstance(String param1, String param2) {
        ViewFragment fragment = new ViewFragment();
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
        View view = inflater.inflate(R.layout.fragment_view, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);  // creates the recyclerView
        List<Car> carIdList;  // defines the list to be shown, could be moved up with other private methods. Test later

        carIdList = new ArrayList<>();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);  // these two ItemDecoration lines just create borders for each item
        recyclerView.addItemDecoration(dividerItemDecoration);

        // hardcoded items for our list of car Ids and violations for testing. Should be changed so that we are just using ids and violations from web. Can also add more attributes.

        //we need 16 cars
        List<String> idList = Arrays.asList("1D4AEU", "F3F43F", "F3FGT5", "G4G544", "1D4AEU","F3F43F","F3FGT5","G4G544", "G4G544", "G4G544");
        Random random = new Random();

        for(int i = 0; i < idList.size(); i++){
            carIdList.add(new Car(idList.get(i), "Violation " + (random.nextInt(800)+100), "Honda", "Accord", "Black", "JFB8798"));
        }

        recyclerAdapter = new RecyclerAdapter(carIdList);  // these two lines initiate the adapter which is going to display the info we just added
        recyclerView.setAdapter(recyclerAdapter);

        view.findViewById(R.id.viewToAddButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_viewFragment_to_submitFragment);  // button to submit fragment
            }
        });

        return view;
    }

}