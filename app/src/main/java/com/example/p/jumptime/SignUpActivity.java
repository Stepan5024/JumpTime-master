package com.example.p.jumptime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    private String mLogin;
    private FirebaseUser user;
    private String mPassword;
    private String mPasswordRepeat;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.activity_sign_up);
        Button reg = (Button) findViewById(R.id.button4); // кнопка регистрации
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogin = ((EditText) findViewById(R.id.Login)).getText().toString();
                mPassword = ((EditText) findViewById(R.id.Password)).getText().toString();
                mPasswordRepeat = ((EditText) findViewById(R.id.RepeatPassword)).getText().toString();
                if ("".equals(mLogin) || "".equals(mPassword) || "".equals(mPasswordRepeat)) {
                    Toast.makeText(getApplicationContext(), "Одно из полей не заполненно. Пожалуйста, заполните все поля и повторите отправку", Toast.LENGTH_LONG).show();
                } else {
                    if (mPasswordRepeat.equals(mPassword)) {
                        addUser();
                    } else {
                        Toast.makeText(getApplicationContext(), "Пароли не совпадают, повторите попытку", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }

    private void addUser() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mLogin + "", mPassword + "").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            myRef.child("users").child(user.getUid()).child("infoUser").child("name").setValue(mLogin);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);

                    intent.putExtra("PARAM", 2);

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Регистрация провалена", Toast.LENGTH_LONG).show();
                }
        }
    });
}
}



