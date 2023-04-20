package com.example.bewisepark;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.bewisepark.Model.AuthRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
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
        ServiceClient serviceClient = ServiceClient.sharedServiceClient(getActivity().getApplicationContext());
        List<Car> carIdList = new ArrayList<>();  // defines the list to be shown, could be moved up with other private methods. Test later
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(carIdList);  // these two lines initiate the adapter which is going to display the info we just added
        AuthRequest authRequest = new AuthRequest(Request.Method.GET,
                "https://mopsdev.bw.edu/~mterekho20/archHW/www/rest.php/cars/",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Type carList = new TypeToken<ArrayList<Car>>() {}.getType();
                        Gson gson = new Gson();
                        try {
                            List<Car>updatedCars = gson.fromJson(response.get("data").toString(), carList);
                            carIdList.clear();
                            carIdList.addAll(updatedCars);
                            recyclerAdapter.notifyDataSetChanged();
                            // TODO: Send orders to adapter and let it know that things changed
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

        // hardcoded items for our list of car Ids and violations for testing. Should be changed so that we are just using ids and violations from web. Can also add more attributes.

        AuthRequest.username = "mt45";
        AuthRequest.password = "s3cret";
        serviceClient.addRequest(authRequest);

        recyclerView = view.findViewById(R.id.recyclerView);  // creates the recyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);  // these two ItemDecoration lines just create borders for each item
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                View firstItemView = layoutManager.findViewByPosition(0);

                if (firstItemView != null) {
                    int position = layoutManager.getPosition(firstItemView);

                    if (position == 0) {
                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                    } else {
                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    }
                }
            }
        });

        view.findViewById(R.id.viewToAddButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_viewFragment_to_submitFragment);  // button to submit fragment
            }
        });

        return view;
    }

}