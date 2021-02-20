package com.example.megastore.views;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.megastore.R;

public class RegisterActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    public static boolean onResetPasswordFragment = false;
    public static boolean setSignUpFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        frameLayout = findViewById(R.id.register_frameLayout);

        if (setSignUpFragment) {
            setSignUpFragment = false;
            setDefaultFragment(new SignUpFragment());
        } else {
            setDefaultFragment(new SignInFragment());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SignInFragment.disableCloseBtn = false;
            SignUpFragment.disableCloseBtn = false;
            if (onResetPasswordFragment) {
                onResetPasswordFragment = false;
                setFragment(new SignInFragment());
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    private void setDefaultFragment(Fragment fragment) {

        /**
         *  FragmentTransaction gives us methods to add, replace, or remove fragments in Android.
         */
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        /**
         * replace() replaces an existing Fragment object from the container containerViewId and adds the the Fragment fragment
         */
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        /**
         * The method commit() schedules this transaction
         */

        fragmentTransaction.commit();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}