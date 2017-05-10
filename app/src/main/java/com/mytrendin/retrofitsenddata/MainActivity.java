package com.mytrendin.retrofitsenddata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    EditText name,email, password;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name= (EditText) findViewById(R.id.name);
        email= (EditText) findViewById(R.id.email);
        password= (EditText) findViewById(R.id.password);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient();
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.43.87")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                ApiService service = retrofit.create(ApiService.class);
                Student student = new Student();
                student.setName(name.getText().toString());
                student.setEmail(email.getText().toString());
                student.setPassword(email.getText().toString());
                Call<Student> call = service.insertData(student.getName(),student.getEmail(),student.getPassword());

                call.enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {

                        Toast.makeText(MainActivity.this, "response"+response, Toast.LENGTH_LONG).show();

                        name.setText("");
                        email.setText("");
                        password.setText("");

                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {


                        Log.i("Hello",""+t);
                        Toast.makeText(MainActivity.this, "Throwable"+t, Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

    }
}
