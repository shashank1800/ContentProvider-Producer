package com.shashankbhat.studenttable.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.shashankbhat.studenttable.R;
import com.shashankbhat.studenttable.databinding.ActivityDeleteStudentBinding;

import java.lang.ref.WeakReference;

import static com.shashankbhat.studenttable.content_provider.CollegeProvider.CONTENT_URI_1;
import static com.shashankbhat.studenttable.utils.Constants.STUDENT_ID;

public class DeleteStudent extends AppCompatActivity {

    private ActivityDeleteStudentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delete_student);

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentId = binding.studentId.getText().toString();
                if(!studentId.isEmpty()){
                    new DeleteSingleStudent(getApplicationContext()).execute(studentId);
                    binding.studentId.setText("");
                }
            }
        });
    }

    private class DeleteSingleStudent extends AsyncTask<String, Void, Void> {
        private final WeakReference<Context> weakReference;

        public DeleteSingleStudent(Context context) {
            this.weakReference = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(String... strings) {

            String selectionClause = STUDENT_ID + " = ?";
            String[] selectArgs = {strings[0]};

            try {
                ContentResolver contentResolver = weakReference.get().getContentResolver();
                contentResolver.delete(Uri.parse(CONTENT_URI_1 + "/"+ strings[0]), selectionClause, selectArgs);
            } catch (Exception e) { }

            //.delete(Uri.parse(String.valueOf(DreamContentProvider.CONTENT_URI)), //delete all!
            return null;
        }
    }
}