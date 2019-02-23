package com.newsapp.cetbusadmin;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddStudentActivity extends AppCompatActivity {
    private String emptyErrorMsg;
    private EditText idText,nameText,phoneText,emailText,passText,addressText;
    private DatabaseReference dbStudentRef;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        emptyErrorMsg = "Field cannot be left blank";

        idText = findViewById(R.id.student_id);
        nameText = findViewById(R.id.student_name);
        phoneText = findViewById(R.id.student_phone);
        emailText = findViewById(R.id.student_email);
        passText = findViewById(R.id.student_password);
        addressText = findViewById(R.id.student_address);

        dbStudentRef = FirebaseDatabase.getInstance().getReference().child("users").child("student");
        mAuth = FirebaseAuth.getInstance();
    }

    public void doAddStudent(final View view) {
        if(validate()){
            final ProgressDialog pd=new ProgressDialog(this);
            pd.setMessage("Please Wait");
            pd.setCancelable(false);
            pd.show();

            final String id = idText.getText().toString();
            final String name = nameText.getText().toString();
            final String phone = phoneText.getText().toString();
            final String email = emailText.getText().toString();
            final String pass = passText.getText().toString();
            final String address = addressText.getText().toString();

            mAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    //final String uid=authResult.getUser().getUid();
                    Map<String, Object> m = new HashMap<>();

                    SimpleDateFormat date=new SimpleDateFormat("dd/MM/yyyy");
                    String dateText = date.format(new Date());

                    m.put("id",id);
                    m.put("name",name);
                    m.put("phone",phone);
                    m.put("email",email);
                    m.put("address",address);
                    m.put("date",dateText);

                    final String key = dbStudentRef.push().getKey();

                    dbStudentRef.child(key).updateChildren(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                pd.dismiss();
                                Toast.makeText(AddStudentActivity.this, "Student Added Successfully", Toast.LENGTH_SHORT).show();
                                clearFields();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }
    boolean validate() {
        if (TextUtils.isEmpty(idText.getText().toString().trim())) {
            idText.requestFocus();
            idText.setError(emptyErrorMsg);
            return false;
        }
        if (TextUtils.isEmpty(nameText.getText().toString().trim())) {
            nameText.requestFocus();
            nameText.setError(emptyErrorMsg);
            return false;
        }
        if (TextUtils.isEmpty(phoneText.getText().toString().trim())) {
            phoneText.requestFocus();
            phoneText.setError(emptyErrorMsg);
            return false;
        } if (TextUtils.isEmpty(emailText.getText().toString().trim())) {
            emailText.requestFocus();
            emailText.setError(emptyErrorMsg);
            return false;
        } if (TextUtils.isEmpty(passText.getText().toString().trim())) {
            passText.requestFocus();
            passText.setError(emptyErrorMsg);
            return false;
        } if (TextUtils.isEmpty(addressText.getText().toString().trim())) {
            addressText.requestFocus();
            addressText.setError(emptyErrorMsg);
            return false;
        }
        return true;
    }
     void clearFields(){
        idText.setText("");
        nameText.setText("");
        phoneText.setText("");
        emailText.setText("");
        passText.setText("");
        addressText.setText("");
    }
}
