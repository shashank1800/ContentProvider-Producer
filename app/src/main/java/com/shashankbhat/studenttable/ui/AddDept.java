package com.shashankbhat.studenttable.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.shashankbhat.studenttable.R;
import com.shashankbhat.studenttable.databinding.ActivityAddDeptBinding;
import com.shashankbhat.studenttable.model.Department;
import com.shashankbhat.studenttable.room.CollegeDao;
import com.shashankbhat.studenttable.room.CollegeDatabase;

public class AddDept extends AppCompatActivity {

    private ActivityAddDeptBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_dept);

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String shortName = binding.shortName.getText().toString();
                String longName = binding.longName.getText().toString();

                if(!shortName.isEmpty() && !longName.isEmpty()){
                    new AddToDataBase(shortName, longName).execute();
                    binding.shortName.setText("");
                    binding.longName.setText("");
                }
            }
        });
    }

    public class AddToDataBase extends AsyncTask<Void, Void, Void> {

        CollegeDao collegeDao = CollegeDatabase.instance.collegeDao();
        String shortName, longName;

        public AddToDataBase(String shortName, String longName) {
            this.shortName = shortName;
            this.longName = longName;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            collegeDao.insertDepartment(new Department(shortName, longName));
            return null;
        }
    }
}