package com.spungkyb.examplefirebaselogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.spungkyb.examplefirebaselogin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_email_activity_signup)
    EditText editTextEmail;
    @BindView(R.id.edit_text_password_activity_signup)
    EditText editTextPassword;
    @BindView(R.id.relative_layout_progress_activity_signup)
    RelativeLayout relativeLayoutProgress;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        initFirebase();
    }

    private void initFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @OnClick({R.id.button_next_activity_signup})
    public void onClick(Button button) {
        switch (button.getId()) {
            case R.id.button_next_activity_signup:
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                signup(email, password);
                break;
        }
    }

    private void signup(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Masukkan Email anda",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Masukkan Password Anda",Toast.LENGTH_LONG).show();
            return;
        }
            showProgress();
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //signup success
                                Toast.makeText(SignupActivity.this,"Success",Toast.LENGTH_LONG).show();

                            } else {
                                //signup fail
                                Toast.makeText(SignupActivity.this,"Failed",Toast.LENGTH_LONG).show();
                            }
                            showProgress();
                            hideProgress();
                            EditText email = (EditText)findViewById(R.id.edit_text_email_activity_signup);
                            email.setText("");
                            EditText password = (EditText)findViewById(R.id.edit_text_password_activity_signup);
                            password.setText("");

                        }
                    });

    }

    private void hideProgress() {
        relativeLayoutProgress.setVisibility(View.GONE);
    }

    private void showProgress() {
        relativeLayoutProgress.setVisibility(View.VISIBLE);
    }
}

