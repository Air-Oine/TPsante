package com.example.admin.tpsante;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.admin.tpsante.database.datasource.DataSource;
import com.example.admin.tpsante.database.modele.User;
import com.google.gson.Gson;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PageCreateUser.class);
                startActivityForResult(intent, 1);
            }
        });

        if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null) {
                return;
            }

            ListUtilisateurFragment firstFragment = new ListUtilisateurFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, firstFragment)
                    .commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                //Récupération de l'utilisateur crée sur l'activité dédiée
                String userJson = data.getStringExtra(PageCreateUser.RESULT);
                User user = new Gson().fromJson(userJson, User.class);

                //Insertion de l'utilisateur dans la DB
                try {
                    DataSource dataSource = new DataSource<>(this, User.class);
                    dataSource.open();

                    long rowId = dataSource.insert(user);;

                    // Test to see if the insertion was ok
                    if (rowId == -1) {
                        Toast.makeText(getApplicationContext(), "Error when creating an User",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "User created and stored in database",
                                Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
