package com.example.test_gait.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_gait.Model.Reports;
import com.example.test_gait.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;



public class report_adapter extends FirebaseRecyclerAdapter<Reports,report_adapter.myviewholder>
{
    public report_adapter(@NonNull FirebaseRecyclerOptions<Reports> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Reports model)
    {
        holder.cadence.setText(model.getCadence());
        holder.step_time.setText(model.getSteptime());
        holder.stride_time.setText(model.getStridetime());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_report,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView cadence,step_time,stride_time;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            cadence=(TextView)itemView.findViewById(R.id.cadence_value);
            step_time=(TextView)itemView.findViewById(R.id.steptime_value);
            stride_time=(TextView)itemView.findViewById(R.id.stridetime_value);
        }
    }
}
