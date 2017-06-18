package com.xll.administrator.democollection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SpannableStringActivity extends AppCompatActivity {
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable_string);
        tv1=(TextView)findViewById(R.id.tv1);
        SpannableString ss=new SpannableString("谢黎黎的SpannableString测试");
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(SpannableStringActivity.this,"haha",Toast.LENGTH_SHORT).show();
                Log.e("tttt","click");
                tv1.setText("为什么点我");
            }
        },1,7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv1.setText(ss);
    }
}
