package com.example.admin.tpsante;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.tpsante.database.datasource.DataSource;
import com.example.admin.tpsante.database.modele.User;

import java.util.List;

public class UtilisateurFragment extends Fragment {

    public static final String ARG_POSITION = "position";

    private DataSource dataSource;
    int mCurrentPosition = -1;
    TextView article;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        //String jsonDatasource = getArguments().getString("DATASOURCE");
        try {
            dataSource = new DataSource<>(getContext(), User.class);
            dataSource.open();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        View view = inflater.inflate(R.layout.utilisateur, container, false);
        article = (TextView) view.findViewById(R.id.article);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if(args != null) {
            updateArticleView(args.getInt(ARG_POSITION));
        }
        else if (mCurrentPosition != -1) {
            updateArticleView(mCurrentPosition);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        dataSource.close();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    public void updateArticleView(int position) {
        if(article != null) {
            //On récupère la liste es utilisateurs en base
            List<User> users = dataSource.readAll();

            //On affiche les informations de l'utilisateur sélectionné
            article.setText(users.get(position).getNom());
            mCurrentPosition = position;
        }
    }
}
