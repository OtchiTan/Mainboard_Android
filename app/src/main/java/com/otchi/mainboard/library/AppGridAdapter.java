package com.otchi.mainboard.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.otchi.mainboard.R;
import com.otchi.mainboard.modele.Application;

import java.util.ArrayList;

public class AppGridAdapter extends BaseAdapter {
    ArrayList<Application> applications;
    LayoutInflater layoutInflater;
    Context context;

    public AppGridAdapter(Context context, ArrayList<Application> applications) {
        this.context = context;
        this.applications = applications;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return applications.size();
    }

    @Override
    public Application getItem(int i) {
        return applications.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.layout_main_appitem,null);
        Application app = getItem(i);
        TextView tvName = view.findViewById(R.id.mainapp_tv_name);
        tvName.setText(app.name);
        return view;
    }
}
