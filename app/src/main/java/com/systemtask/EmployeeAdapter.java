package com.systemtask;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>{

    private Context mCtx;
    private List<Employee> employeeList;

    public EmployeeAdapter(Context mCtx, List<Employee> employeeList) {
        this.mCtx = mCtx;
        this.employeeList = employeeList;
    }
    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_item, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.name.setText(employeeList.get(position).name);
        holder.ageText.setText(String.valueOf(employeeList.get(position).age));
        holder.city.setText(employeeList.get(position).city);

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView name, ageText, city;

        public EmployeeViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.Name);
            ageText = itemView.findViewById(R.id.ageText);
            city = itemView.findViewById(R.id.city);
        }

    }

    public void updateData(RealmResults<Employee> result) {
        employeeList = new ArrayList<Employee>(result);
    }
}
