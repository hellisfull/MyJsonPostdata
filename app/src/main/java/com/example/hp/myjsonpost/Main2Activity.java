package com.example.hp.myjsonpost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView tv1,tv2,tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv1=(TextView)findViewById(R.id.tv2);
        tv2=(TextView)findViewById(R.id.tv3);
        tv3=(TextView)findViewById(R.id.tv4);
        Intent in =getIntent();
        String id=in.getExtras().getString("id");
        String namef=in.getExtras().getString("namefirst");
        String namel=in.getExtras().getString("namesecond");
        tv1.setText(namef);
        tv2.setText(namel);
        tv3.setText("id:"+id);

    }
}
