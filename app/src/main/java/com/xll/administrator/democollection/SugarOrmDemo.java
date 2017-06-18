package com.xll.administrator.democollection;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.xll.administrator.entity.Student;

/**
 * Created by Administrator on 2017/4/6.
 */

public class SugarOrmDemo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugarorm);
        Student student=new Student("xll",1);
        student.save();


        Student student1=Student.findById(Student.class,1);
        Log.e("student1 name:",student1.getName());

        student1.setName("hahah");
        student1.setId(1);
        student1.save();



    }
}
