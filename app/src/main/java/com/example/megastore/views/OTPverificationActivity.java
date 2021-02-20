package com.example.megastore.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.megastore.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OTPverificationActivity extends AppCompatActivity {

    private TextView phoneNo;
    private EditText otp;
    private Button verifyBtn;
    private String userNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        phoneNo = findViewById(R.id.phone_no);
        otp = findViewById(R.id.otp);
        verifyBtn = findViewById(R.id.verify_btn);

        userNo = getIntent().getStringExtra("mobileNo");
        phoneNo.setText("Verification code has been sent to +91 " + userNo);

        Random random = new Random();
        final int OTP_number = random.nextInt(999999 - 111111) + 111111;
        String SMS_API = "https://www.fast2sms.com/dev/bulkV2";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SMS_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                verifyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (otp.getText().toString().equals(String.valueOf(OTP_number))) {
                            DeliveryActivity.codOrderConfirmed = true;
                            finish();
                        } else {
                            Toast.makeText(OTPverificationActivity.this, "Incorrect OTP!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                finish();
                Toast.makeText(OTPverificationActivity.this, "Failed to send the otp verification code!", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("authorization", "WEnAPsgzQ8w0NSue3Tq2DM471LdhlCvXcYmUIa5OZGFrkKbV6y8GxX4eCAVKOTagbhDpHsLSwYcFiWtR");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> body = new HashMap<>();
                body.put("sender_id", "CHKSMS");
                body.put("language", "english");
                body.put("route", "s");
                body.put("numbers", userNo);
                body.put("message", "2");
                body.put("variables", "{#var#}");
                body.put("variables_values", String.valueOf(OTP_number));

                return body;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        RequestQueue requestQueue = Volley.newRequestQueue(OTPverificationActivity.this);
        requestQueue.add(stringRequest);

    }
}