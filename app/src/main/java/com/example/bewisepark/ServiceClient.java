package com.example.bewisepark;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

// Taken from example done in class
public class ServiceClient {
    private static ServiceClient serviceClient;
    private static Context context;
    private RequestQueue requestQueue;

    private ServiceClient(Context ctx) {
        this.context = ctx;
    }

    public static synchronized ServiceClient sharedServiceClient(Context ctx) {
        if (serviceClient == null) {
            serviceClient = new ServiceClient(ctx);
        }
        return serviceClient;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this.context.getApplicationContext());
        }
        return requestQueue;
    }

    public void addRequest(Request request) {
        getRequestQueue().add(request);
    }
}
