package com.example.admin.tpsante;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.tpsante.adapter.RecyclerSimpleViewAdapter;
import com.example.admin.tpsante.database.datasource.DataSource;
import com.example.admin.tpsante.database.modele.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AirOine on 16/06/2017.
 */

public class ListUtilisateurFragment extends Fragment {

    private RecyclerView recyclerView;

    private DataSource dataSource;

    private List<User> users = new ArrayList<>();
    private List<String> usersList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_utilisateurs, container, false);

        recyclerView = (RecyclerView) view;
        recyclerView.setAdapter(new RecyclerSimpleViewAdapter(getFragmentManager(), usersList, R.layout.utilisateur_list_content));

        try {
            dataSource = new DataSource<>(getContext(), User.class);
            dataSource.open();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshListUsers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        dataSource.close();
    }

    private void refreshListUsers() {
        users = dataSource.readAll();

        usersList.clear();

        for(User user : users) {
            usersList.add(user.getNom());
        }

        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
