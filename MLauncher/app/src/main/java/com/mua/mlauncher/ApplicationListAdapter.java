package com.mua.mlauncher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationListAdapter
        extends RecyclerView.Adapter<ApplicationListAdapter.AppUsageListViewHolder> {
    private List<ApplicationInfo> applicationList = new ArrayList<>();
    private final ApplicationClickListener applicationClickListener;

    public ApplicationListAdapter(ApplicationClickListener applicationClickListener) {
        this.applicationClickListener = applicationClickListener;
    }

    protected class AppUsageListViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView icon;
        private final View currentView;

        AppUsageListViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_item_application_name);
            icon = view.findViewById(R.id.iv_item_application_drawable);
            currentView = view;
        }
    }

    @NotNull
    @Override
    public AppUsageListViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new AppUsageListViewHolder((LayoutInflater.from(parent.getContext()))
                .inflate(R.layout.item_application, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull AppUsageListViewHolder holder, int position) {
        holder.name.setText(applicationList.get(position).getApplicationName());
        holder.icon.setImageDrawable(applicationList.get(position).getApplicationDrawable());
        holder.currentView
                .setOnClickListener(
                        v -> applicationClickListener.onApplicationClick(applicationList.get(position))
                );
    }


    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    public void setApplicationList(List<ApplicationInfo> applicationList) {
        this.applicationList = applicationList;
        Collections.sort(applicationList,
                (o1, o2) -> o1.getApplicationName().toLowerCase().compareTo(o2.getApplicationName().toLowerCase()));
        notifyDataSetChanged();
    }

    /*
    @Override
    public int getItemViewType(int position) {
        if (applicationList.get(position).isContent()) {
            return ApplicationType.TYPE_CONTENT;
        } else {
            return ApplicationType.TYPE_HEADER;
        }
    }

     */

}

enum ApplicationType {

    TYPE_CONTENT(0),
    TYPE_HEADER(1),
    ;

    int id;

    ApplicationType(int id) {
        this.id = id;
    }
}