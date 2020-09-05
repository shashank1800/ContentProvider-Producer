package com.shashankbhat.studenttable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shashankbhat.studenttable.databinding.ActivityMainBinding;
import com.shashankbhat.studenttable.model.Department;
import com.shashankbhat.studenttable.room.CollegeDao;
import com.shashankbhat.studenttable.room.CollegeDatabase;
import com.shashankbhat.studenttable.ui.AddDept;
import com.shashankbhat.studenttable.ui.AddStudent;
import com.shashankbhat.studenttable.ui.DeleteStudent;

import java.lang.ref.WeakReference;

import static com.shashankbhat.studenttable.content_provider.CollegeProvider.CONTENT_URI_1;
import static com.shashankbhat.studenttable.content_provider.CollegeProvider.CONTENT_URI_2;
import static com.shashankbhat.studenttable.utils.Constants.DEPT_ID;
import static com.shashankbhat.studenttable.utils.Constants.STUDENT_ID;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private CollegeDao collegeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        CollegeDatabase collegeDatabase = CollegeDatabase.getInstance(this);
        collegeDao = collegeDatabase.collegeDao();

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchString = binding.deptName.getText().toString();
                if(!searchString.isEmpty()){
                    new SearchDept(getApplicationContext()).execute(searchString);
                    binding.deptName.setText("");
                }
            }
        });

        updateUI();

    }

    private void updateUI() {
        new ReadAllDepartments().execute();
        new ReadAllStudents().execute();
    }

    class ReadAllStudents extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {

            StringBuilder builder = new StringBuilder();
            try{

                Cursor cursor = getApplicationContext().getContentResolver().query(Uri.parse(CONTENT_URI_1), null, null, null, null);

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    builder.append(cursor.getString(cursor.getColumnIndex(STUDENT_ID)))
                            .append(". ")
                            .append(cursor.getString(cursor.getColumnIndex("name")))
                            .append(" ")
                            .append(cursor.getString(cursor.getColumnIndex("deptFullName")))
                            .append("\n");

                    cursor.moveToNext();
                }
            }catch (Exception ex){}

            return builder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            binding.studentTable.setText(result);
        }
    }

    class ReadAllDepartments extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {

            StringBuilder stringBuilder = new StringBuilder();

            for(Department department : collegeDao.findAllDepartments())
                stringBuilder
                        .append(department.getDeptID())
                        .append(". ")
                        .append(department.getDeptShortName())
                        .append(" ")
                        .append(department.getDeptFullName())
                        .append(" ")
                        .append("\n");

            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            binding.deptTable.setText(result);
        }
    }


    private class SearchDept extends AsyncTask<String, Void, String> {
        private final WeakReference<Context> weakReference;

        public SearchDept(Context context) {
            this.weakReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder builder = new StringBuilder();

            String selectionClause = "deptFullName" + " = LIKE ?";
            String[] selectArgs = {strings[0]};

            try {
                ContentResolver contentResolver = weakReference.get().getContentResolver();
                Cursor cursor = contentResolver.query(Uri.parse(CONTENT_URI_2 + "/"+ strings[0]), null, selectionClause, selectArgs, null);

                int depId= cursor.getColumnIndex(DEPT_ID);
                int depFullName = cursor.getColumnIndex("deptFullName");
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    System.out.println(cursor.getString(depId)+ " " + cursor.getString(depFullName) );
                    builder.append(cursor.getString(depId))
                            .append(" ")
                            .append(cursor.getString(depFullName))
                            .append("\n");

                    cursor.moveToNext();
                }
            } catch (Exception ex) {
                System.out.println("Some erorr occurred "+ ex.getMessage());
            }

            //.delete(Uri.parse(String.valueOf(DreamContentProvider.CONTENT_URI)), //delete all!
            return builder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            binding.deptTable.setText(result);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.refresh:
                updateUI();
                break;

            case R.id.add_dept:
                startActivity(new Intent(getApplicationContext(), AddDept.class));
                break;

            case R.id.add_student:
                startActivity(new Intent(getApplicationContext(), AddStudent.class));
                break;

            case R.id.delete_student:
                startActivity(new Intent(getApplicationContext(), DeleteStudent.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}