package com.mua.mlauncher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ApplicationListAdapter
        extends RecyclerView.Adapter<ApplicationListAdapter.AppUsageListViewHolder> {
    private List<ApplicationInfo> applicationList = new ArrayList<>();

    protected class AppUsageListViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView icon;

        AppUsageListViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_item_application_name);
            icon = view.findViewById(R.id.iv_item_application_drawable);
        }
    }

    @NotNull
    @Override
    public AppUsageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppUsageListViewHolder((LayoutInflater.from(parent.getContext()))
                .inflate(R.layout.item_application, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull AppUsageListViewHolder holder, int position) {
        holder.name.setText(applicationList.get(position).getApplicationName());
        holder.icon.setImageDrawable(applicationList.get(position).getApplicationDrawable());
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    public void setApplicationList(List<ApplicationInfo> applicationList) {
        this.applicationList = applicationList;
        notifyDataSetChanged();
    }
}