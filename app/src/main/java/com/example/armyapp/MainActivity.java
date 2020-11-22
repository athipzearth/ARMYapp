package com.example.armyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.armyapp.adapter.UserAdapter;
import com.example.armyapp.db.AppDatabase;
import com.example.armyapp.model.User;
import com.example.armyapp.util.AppExecutors;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onResume() {
        super.onResume();

        final AppExecutors executors = new AppExecutors();
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(MainActivity.this);
                final User[] users = db.userDao().getAllUsers();

                executors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        UserAdapter adapter = new UserAdapter(MainActivity.this, users);
                        mRecyclerView.setAdapter(adapter);
                    }
                });

        /*String msg = "";
        for (User u : users) {
          Log.i(TAG, u.firstName + " " + u.lastName);
          msg += String.format(
              Locale.getDefault(),
              "%s %s %s\n",
              u.firstName, u.lastName, DateFormatter.formatForUi(u.birthDate)
          );
        }

        final String message = msg;
        executors.mainThread().execute(new Runnable() {
          @Override
          public void run() { // main thread
            new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
          }
        });*/
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.user_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        Button button = findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,add_user.class);
                startActivity(intent);
            }
        });
    }
}