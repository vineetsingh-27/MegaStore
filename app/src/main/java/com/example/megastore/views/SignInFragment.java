package com.example.megastore.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.megastore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.megastore.views.RegisterActivity.onResetPasswordFragment;

public class SignInFragment extends Fragment {

    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout;

    private EditText email;
    private EditText password;

    private TextView forgotPassword;

    private ImageButton closeBtn;
    private Button signInBtn;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        dontHaveAnAccount = view.findViewById(R.id.tv_dont_have_an_account);
        parentFrameLayout = getActivity().findViewById(R.id.register_frameLayout);

        email = view.findViewById(R.id.sign_in_email);
        password = view.findViewById(R.id.sign_in_password);

        forgotPassword = view.findViewById(R.id.tv_forgot_password);

        closeBtn = view.findViewById(R.id.sign_in_close_btn);
        signInBtn = view.findViewById(R.id.sign_in_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        progressBar = view.findViewById(R.id.sign_in_progressBar);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainIntent();
            }
        });

        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignUpFragment());
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResetPasswordFragment = true;
                setFragment(new ResetPasswordFragment());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailAndPassword();
            }
        });
    }

    private void checkEmailAndPassword() {
        if (email.getText().toString().matches(emailPattern)) {
            if (password.length() >= 8) {

                progressBar.setVisibility(View.VISIBLE);
                signInBtn.setEnabled(false);
                signInBtn.setTextColor(Color.argb(50f, 255, 255, 255));

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mainIntent();
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signInBtn.setEnabled(true);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(getActivity(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                signInBtn.setEnabled(true);
                signInBtn.setTextColor(Color.rgb(255, 255, 255));
            }
        } else {
            Toast.makeText(getActivity(), "Invalid email or password", Toast.LENGTH_SHORT).show();
            signInBtn.setEnabled(true);
            signInBtn.setTextColor(Color.rgb(255, 255, 255));
        }
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(email.getText().toString())) {
            if (!TextUtils.isEmpty(password.getText().toString())) {
                signInBtn.setEnabled(true);
                signInBtn.setTextColor(Color.rgb(255, 255, 255));
            } else {
                signInBtn.setEnabled(false);
                signInBtn.setTextColor(Color.argb(50f, 255, 255, 255));
            }
        } else {
            signInBtn.setEnabled(false);
            signInBtn.setTextColor(Color.argb(50f, 255, 255, 255));
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void mainIntent() {
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();
    }
}