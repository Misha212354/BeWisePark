package com.example.bewisepark;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bewisepark.Model.AuthRequest;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        view.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceClient serviceClient = ServiceClient.sharedServiceClient(getActivity().getApplicationContext());

                EditText nameField = view.findViewById(R.id.usernameField);
                String name = nameField.getText().toString();

                EditText passwordField = view.findViewById(R.id.passwordField);
                String password = passwordField.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("username", name);

                if(name.isBlank()){
                    nameField.setError("Username is required");
                    Toast.makeText(getActivity(),"Failed to Login! Please Enter Username",Toast.LENGTH_LONG).show();
                }
                else if(password.isBlank()) {
                    passwordField.setError("Password is required");
                    Toast.makeText(getActivity(),"Failed to Login! Please Enter Password.",Toast.LENGTH_LONG).show();
                }

                else {
                    AuthRequest authRequest = new AuthRequest(Request.Method.GET, "https://mopsdev.bw.edu/~bkrupp/330/www/rest.php/orders", null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getActivity(),"Login Successful!",Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_hubFragment, bundle);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(),"Login Failed! Please Check Username or Password.",Toast.LENGTH_LONG).show();
                            Log.e("Volley Error", error.toString());
                        }
                    });
                    AuthRequest.username = name; // Whatever value from the form (use bkrupp for now)
                    AuthRequest.password = password; // Whatever value from the form (use s3cr3t for now)
                    serviceClient.addRequest(authRequest);
                }

            }
        });
        return view;
    }
}