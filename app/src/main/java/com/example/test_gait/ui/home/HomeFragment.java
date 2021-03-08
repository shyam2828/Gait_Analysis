package com.example.test_gait.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.test_gait.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    private EditText age,cadence,step_time,stride_time;
    private Button start;
    private TextView final3,note;


    //backend

    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    private static final int DATE_PICKER_ID = 1;



    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        age=view.findViewById(R.id.edit_age);
        cadence=view.findViewById(R.id.edit_candence);
        step_time=view.findViewById(R.id.edit_steptime);
        stride_time=view.findViewById(R.id.edit_stridetime);
        start=view.findViewById(R.id.start);
        final3=view.findViewById(R.id.final_note3);
        note=view.findViewById(R.id.note);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                note.setVisibility(View.INVISIBLE);

                final int age1=Integer.parseInt(age.getText().toString());
                final int candence1=Integer.parseInt(cadence.getText().toString());
                final double step=Double.parseDouble(step_time.getText().toString());
                final double stride=Double.parseDouble(stride_time.getText().toString());



                String s;
                int NC;
                double NST,NSTR;
                if ((age1>=70) && (age1<=74))
                {
                    NC=102;
                    NST=0.59;
                    NSTR=1.18;
                }
                else
                {
                    NC=106;
                    NST=0.56;
                    NSTR=1.13;
                }
                int C=(candence1-NC);
                double ST=(step-NST);
                double STR=(stride-NSTR);
                if (C==0 && ST==0 && STR==0)
                {
                    final3.setText("Your gait is normal");
                }
                else if (C<=10)
                {
                    if ((ST<=0.5) && (STR<=1.5))
                    {
                        s="Your gait is at the early stage of abnormality. To improve your gait, kindly decrease your stride time and step time. NOTE: Kindly refer necessary training videos to self-rehabilitate yourself.";
                        final3.setText(s);
                    }
                    else
                    {
                        s="Your gait is at the early stage of abnormality. To improve your gait, kindly decrease your stride time and step time. NOTE: Kindly refer necessary training videos to self-rehabilitate yourself.";
                        final3.setText(s);
                    }
                }
                else if (C<=20)
                {
                    if ((ST<=1) && (STR<=2.5))
                    {
                        s="Your gait is in intermediate stage of abnormality. To improve your gait kindly refer the necessary training videos in video section.";
                        final3.setText(s);
                    }
                    else
                    {
                        s="Your gait is in intermediate stage of abnormality. To improve your gait kindly refer the necessary training videos in video section.";
                        final3.setText(s);
                    }
                }
                else
                {
                    s="Your gait is in final stage of abnormality. Kindly visit doctor to take necessary rehabilitation.";
                    final3.setText(s);
                }
                String data_age=age.getText().toString();
                String data_cadence=cadence.getText().toString();
                String data_step=step_time.getText().toString();
                String data_stride=stride_time.getText().toString();
                opendatabase(data_age,data_cadence,data_step,data_stride);
            }
        });

        return view;
    }

    private void opendatabase(String data_age, String data_cadence, String data_step, String data_stride) {
        auth= FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Gait Analysis").child(auth.getUid());


        java.util.Calendar calendar= java.util.Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
        String time=format.format(calendar.getTime());
        String Date= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Age", data_age);
        hashMap.put("Cadence", data_cadence);
        hashMap.put("Step time", data_step);
        hashMap.put("Stride time", data_stride);
        hashMap.put("Time",time);
        hashMap.put("Date",Date);

        reference.setValue(hashMap);


    }
}