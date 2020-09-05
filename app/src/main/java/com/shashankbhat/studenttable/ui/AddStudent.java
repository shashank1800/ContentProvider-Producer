package com.shashankbhat.studenttable.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.shashankbhat.studenttable.R;
import com.shashankbhat.studenttable.databinding.ActivityAddStudentBinding;
import com.shashankbhat.studenttable.model.Student;
import com.shashankbhat.studenttable.room.CollegeDao;
import com.shashankbhat.studenttable.room.CollegeDatabase;

public class AddStudent extends AppCompatActivity {

    private ActivityAddStudentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_student);

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = binding.name.getText().toString();
                String deptID = binding.deptId.getText().toString();

                if(!name.isEmpty() && !deptID.isEmpty()){
                    new AddToDataBase(name, Integer.parseInt(deptID)).execute();
                    binding.name.setText("");
                    binding.deptId.setText("");
                }
            }
        });

    }

    public class AddToDataBase extends AsyncTask<Void, Void, Void> {

        CollegeDao collegeDao = CollegeDatabase.instance.collegeDao();
        String name;
        int deptID;

        public AddToDataBase(String name, int deptID) {
            this.name = name;
            this.deptID = deptID;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            collegeDao.insertStudent(new Student(name, deptID));
            return null;
        }
    }
}