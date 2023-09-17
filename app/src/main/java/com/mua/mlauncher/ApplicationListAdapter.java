package com.mua.mlauncher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ApplicationListAdapter
        extends RecyclerView.Adapter<ApplicationListAdapter.AppUsageListViewHolder>
        implements Filterable {
    private final ApplicationClickListener applicationClickListener;
    private List<ApplicationInfo> originalApplicationList = new ArrayList<>();
    private List<ApplicationInfo> applicationList = new ArrayList<>();

    public ApplicationListAdapter(ApplicationClickListener applicationClickListener) {
        this.applicationClickListener = applicationClickListener;
    }

    @NotNull
    @Override
    public AppUsageListViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new AppUsageListViewHolder((LayoutInflater.from(parent.getContext()))
                .inflate(R.layout.item_application, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull AppUsageListViewHolder holder, int position) {
        if (getItemViewType(position) == ApplicationViewType.TYPE_CONTENT.id) {
            holder.name.setText(applicationList.get(position).getApplicationName());
            holder.icon.setImageDrawable(applicationList.get(position).getApplicationDrawable());
            holder
                    .currentView
                    .setOnClickListener(
                            v -> applicationClickListener.onApplicationClick(applicationList.get(position))
                    );
            holder
                    .infoButton
                    .setOnClickListener(
                            v -> applicationClickListener.onApplicationInfoClick(applicationList.get(position))
                    );
        } else if (getItemViewType(position) == ApplicationViewType.TYPE_HEADER.id) {
            holder.header.setText(applicationList.get(position).getHeader());
            holder.appDetails.setVisibility(View.GONE);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                applicationList.clear();
                for (ApplicationInfo applicationInfo : originalApplicationList) {
                    if (applicationInfo.getApplicationName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        applicationList.add(applicationInfo);
                    }
                }
                filterResults.values = applicationList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                List<ApplicationInfo> applicationList = (List<ApplicationInfo>) results.values;
                setApplicationListOnly(addHeadersAndSort(applicationList));
            }
        };
    }

    private List<ApplicationInfo> addHeadersAndSort(List<ApplicationInfo> applicationList) {
        applicationList.addAll(listHeaders(applicationList));
        Collections.sort(applicationList,
                (o1, o2) ->
                        o1.getApplicationName().toLowerCase()
                                .compareTo(o2.getApplicationName().toLowerCase())
        );
        return applicationList;
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    private void setApplicationListOnly(List<ApplicationInfo> applicationList) {
        this.applicationList = applicationList;
        notifyDataSetChanged();
    }

    public void setApplicationList(List<ApplicationInfo> applicationList) {
        List<ApplicationInfo> applicationListWithHeaderAndSorted = addHeadersAndSort(applicationList);

        this.originalApplicationList = new ArrayList<>(applicationListWithHeaderAndSorted);
        this.applicationList = new ArrayList<>(applicationListWithHeaderAndSorted);
        notifyDataSetChanged();
    }

    public List<ApplicationInfo> listHeaders(List<ApplicationInfo> applicationList) {
        Set<String> presentHeaders = new HashSet<>();

        for (ApplicationInfo appInfo : applicationList) {
            String firstCharString = appInfo.getApplicationName().toUpperCase().substring(0, 1);
            if (Character.isLetter(firstCharString.charAt(0))) {
                presentHeaders.add(firstCharString);
            } else {
                presentHeaders.add("#");
            }
        }

        List<ApplicationInfo> resultantHeaders = new ArrayList<>();
        for (String header : presentHeaders) {
            resultantHeaders.add(new ApplicationInfo(header));
        }

        return resultantHeaders;
    }

    @Override
    public int getItemViewType(int position) {
        return
                applicationList.get(position).isContent()
                        ? ApplicationViewType.TYPE_CONTENT.id
                        : ApplicationViewType.TYPE_HEADER.id;
    }

    protected class AppUsageListViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView icon;
        private final ImageButton infoButton;

        private final TextView header;
        private final RelativeLayout appDetails;

        private final View currentView;

        AppUsageListViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_item_application_name);
            icon = view.findViewById(R.id.iv_item_application_drawable);
            infoButton = view.findViewById(R.id.ib_item_application_info);
            header = view.findViewById(R.id.tv_item_application_type_header);
            appDetails = view.findViewById(R.id.rv_item_application_type_content);

            currentView = view;
        }
    }

}