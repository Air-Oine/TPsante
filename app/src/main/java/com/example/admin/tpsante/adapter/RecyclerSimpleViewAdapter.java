package com.example.admin.tpsante.adapter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.tpsante.ListUtilisateurFragment;
import com.example.admin.tpsante.R;
import com.example.admin.tpsante.UtilisateurFragment;

import java.util.List;

/**
 * Created by AirOine on 15/06/2017.
 */

public class RecyclerSimpleViewAdapter extends RecyclerView.Adapter<RecyclerSimpleViewAdapter.ViewHolder> {

    private FragmentManager fragmentManager;

    /**
     * List items
     */
    private List<String> items;
    /**
     * the resource id of item Layout
     */
    private int itemLayout;

    /**
     * Constructor RecyclerSimpleViewAdapter
     * @param items : the list items
     * @param itemLayout : the resource id of itemView
     */
    public RecyclerSimpleViewAdapter(FragmentManager fragmentManager, List<String> items, int itemLayout) {
        this.fragmentManager = fragmentManager;
        this.items = items;
        this.itemLayout = itemLayout;
    }

    /*public ListUtilisateurFragment.UtilisateurViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.utilisateur_list_content, parent, false);
        return new ListUtilisateurFragment.UtilisateurViewHolder(view1);
    }*/

    /*@Override
    public void onBindViewHolder(ListUtilisateurFragment.UtilisateurViewHolder holder, final int position) {
        holder.mContentView.setText("Temp");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilisateurFragment utilisateurFragment = (UtilisateurFragment) getFragmentManager().findFragmentById(R.id.article_fragment);

                if (utilisateurFragment != null) {
                    utilisateurFragment.updateArticleView(position);
                } else {
                    UtilisateurFragment newFragment = new UtilisateurFragment();
                    Bundle args = new Bundle();
                    args.putInt(UtilisateurFragment.ARG_POSITION, position);
                    newFragment.setArguments(args);

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, newFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }*/
    /**
     * Create View Holder by Type
     * @param parent, the view parent
     * @param viewType : the type of View
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get inflater and get view by resource id itemLayout
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        // return ViewHolder with View
        return new RecyclerSimpleViewAdapter.ViewHolder(v);
    }

    /**
     * Get the size of items in adapter
     * @return the size of items in adapter
     */
    @Override
    public int getItemCount() {
        return items.size();
    }
    /**
     * Bind View Holder with Items
     * @param holder: the view holder
     * @param position : the current position
     */
    @Override
    public void onBindViewHolder(RecyclerSimpleViewAdapter.ViewHolder holder, final int position) {
        // find item by position
        String item = items.get(position);
        // save information in holder, we have one type in this adapter
        holder.primaryText.setText(item);
        holder.itemView.setTag(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilisateurFragment utilisateurFragment = (UtilisateurFragment) fragmentManager.findFragmentById(R.id.article_fragment);

                if (utilisateurFragment != null) {
                    utilisateurFragment.updateArticleView(position);
                } else {
                    UtilisateurFragment newFragment = new UtilisateurFragment();
                    Bundle args = new Bundle();
                    args.putInt(UtilisateurFragment.ARG_POSITION, position);
                    newFragment.setArguments(args);

                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, newFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }
    /**
     *
     * @author florian
     * Class viewHolder
     * Hold an textView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // TextViex
        public TextView primaryText;
        /**
         * Constructor ViewHolder
         * @param itemView: the itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            // link primaryText
            primaryText = (TextView) itemView.findViewById(R.id.content);
        }
    }
}