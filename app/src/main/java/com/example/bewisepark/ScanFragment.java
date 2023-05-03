package com.example.bewisepark;

import static java.lang.Character.codePointAt;
import static java.lang.Character.getType;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.bewisepark.Model.AuthRequest;
import com.example.bewisepark.Model.types.Car;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanFragment extends Fragment {
    private CodeScanner mCodeScanner;
    private Context thiscontext;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScanFragment newInstance(String param1, String param2) {
        ScanFragment fragment = new ScanFragment();
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
        final Activity activity = getActivity();
        thiscontext = container.getContext();
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        Button cancelButton = view.findViewById(R.id.cancelScan);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {
                Navigation.findNavController(view).navigateUp();
            }
        });

        if(ContextCompat.checkSelfPermission(thiscontext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA}, 123);
        }else{
            startScanning(activity, view);
        }

        return view;

    }


    private void startScanning(Activity activity, View view){
        CodeScannerView scannerView = view.findViewById(R.id.scannerView);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.startPreview();
        Gson gson = new Gson();
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                ServiceClient serviceClient = ServiceClient.sharedServiceClient(getActivity().getApplicationContext());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        String carId = result.getText().toString();

                        AuthRequest authRequest = new AuthRequest(Request.Method.GET, "https://mopsdev.bw.edu/~mterekho20/archHW/www/rest.php/cars/"+carId, null, new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    if(response.getInt("status") == 0){

                                        Toast.makeText(getActivity(), "Car Found", Toast.LENGTH_LONG).show();
                                        Type carType = new TypeToken<ArrayList<Car>>(){}.getType();

                                        List<Car> carList = gson.fromJson(response.get("data").toString(), carType);
                                        Car car = carList.get(0);

                                        bundle.putString("carId", carId);
                                        bundle.putString("make", car.getMake());
                                        bundle.putString("model", car.getModel());
                                        bundle.putString("color", car.getColor());
                                        bundle.putString("plate", car.getPlate_number());

                                        Navigation.findNavController(view).navigate(R.id.action_scanFragment_to_foundCarFragment, bundle);
                                    }
                                    else{
                                        Toast.makeText(getActivity(), "Car Not Found", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(),"Login Failed! Please Check Username or Password.",Toast.LENGTH_LONG).show();
                                Log.e("Volley Error", error.toString());
                            }
                        });
                        serviceClient.addRequest(authRequest);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }

        });



    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 123){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(thiscontext, "Permission Granted", Toast.LENGTH_SHORT).show();
            }else{
               Toast.makeText(thiscontext, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}