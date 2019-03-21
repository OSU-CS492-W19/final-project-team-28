package com.example.swapisearcher;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.swapisearcher.data.SWAPI_Repo;

import java.util.List;

public class Saved_SWAPI_Adapter extends RecyclerView.Adapter<Saved_SWAPI_Adapter.RepoViewHolder>{
    private List<SWAPI_Repo> mRepo;
    private OnSavedItemClickListener mItemClickListener;

    public interface OnSavedItemClickListener {
        void onSavedItemClick(SWAPI_Repo repo);
    }

    public Saved_SWAPI_Adapter(OnSavedItemClickListener clickListener) {
        mItemClickListener = clickListener;
    }
    public void updateRepoList(List<SWAPI_Repo> forecastItems) {
        mRepo = forecastItems;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (mRepo != null) {
            return mRepo.size();
        } else {
            return 0;
        }
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.saved_repos, parent, false);
        return new RepoViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        SWAPI_Repo repo = mRepo.get(position);
        holder.bind(repo);
    }

    class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mLocationTV;

        public RepoViewHolder(View itemView) {
            super(itemView);
            mLocationTV = itemView.findViewById(R.id.tv_SWAPI_Elem_name);

            itemView.setOnClickListener(this);
        }

        public void bind(SWAPI_Repo repo) {
            String name = repo.name;
            mLocationTV.setText(name);
        }

        @Override
        public void onClick(View v) {
            SWAPI_Repo repo = mRepo.get(getAdapterPosition());
            mItemClickListener.onSavedItemClick(repo);
        }
    }
}
