package com.aagjm.dare;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by slamo on 25. 01. 2017.
 */

public class loginrequest extends StringRequest {
    //glej RegisterRequest
    private static final String prijava_url="https://unswerving-register.000webhostapp.com/scripts/login.php";

    private Map<String,String> params;
    public loginrequest(String username, String password, Response.Listener<String> listener) {
        super(Method.POST, prijava_url, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

    }
    @Override
    public Map<String,String> getParams(){
            return params;
        }


}

