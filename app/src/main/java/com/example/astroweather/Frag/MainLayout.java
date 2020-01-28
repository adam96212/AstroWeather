package com.example.astroweather.Frag;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.astroweather.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainLayout extends Fragment implements Runnable {

    public EditText input1, input2;
    public TextView watch;
    public Button buttonApply;
    OnMessageReadListener messageReadListener;
    private int hour;
    private int minute;
    private int second;
    private String time;
    public Boolean flaga;
    boolean run = true;
    Handler handler = new Handler();

    public void toast (CharSequence text) {
        Context context = getActivity().getApplicationContext();
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public MainLayout() {
    }

    public void systemTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(500);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Calendar c = Calendar.getInstance();
                                hour = c.get(Calendar.HOUR_OF_DAY);
                                if (hour > 12){
                                    hour = hour - 12;
                                }
                                minute = c.get(Calendar.MINUTE);
                                second = c.get(Calendar.SECOND);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
                                Date date = c.getTime();
                                time = simpleDateFormat.format(date);
                                watch.setText(time);
                            }
                        });
                    }
                    catch (Exception ex) {
                        watch.setText(ex.getMessage());
                    }
                }
            }
        }).start();
    }

    @Override
    public void run() {

    }

    public interface OnMessageReadListener{
        public void onMessageRead(String message);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_layout,container,false);

        input1 = view.findViewById(R.id.input1);
        input2 = view.findViewById(R.id.input1);
        buttonApply = view.findViewById(R.id.button);
        watch = view.findViewById(R.id.watch);
        systemTime();

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input1.getText().length() == 0 || input2.getText().length() == 0){
                    toast("Please provide data!");
                    input1.setText("");
                    input2.setText("");
                }
                else if (String.valueOf(input1).matches("-?\\d+(\\.\\d+)?") || String.valueOf(input2).matches("-?\\d+(\\.\\d+)?") || Double.valueOf(String.valueOf(input1.getText())) > 90.0 || Double.valueOf(String.valueOf(input1.getText())) < -90.0  || Double.valueOf(String.valueOf(input2.getText())) > 90.0 || Double.valueOf(String.valueOf(input2.getText())) < -90.0)
                {
                    toast("Wrong data provided!");
                    input1.setText("");
                    input2.setText("");
                }
                else
                {
                    String message = input1.getText().toString() + "/" + input2.getText().toString();
                    messageReadListener.onMessageRead(message);
                    flaga=true;

                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity)context;

        try {
            messageReadListener = (OnMessageReadListener) activity;
        }
        catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString() + "must override");
        }
    }

}
