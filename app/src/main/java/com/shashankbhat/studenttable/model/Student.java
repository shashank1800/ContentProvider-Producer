package com.shashankbhat.studenttable.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.shashankbhat.studenttable.utils.Constants;
import static com.shashankbhat.studenttable.utils.Constants.DEPT_ID;
import static com.shashankbhat.studenttable.utils.Constants.STUDENT_ID;
import static com.shashankbhat.studenttable.utils.Constants.STUD_TABLE;

/**
 * Created by SHASHANK BHAT on 04-Sep-20.
 */

@Entity(tableName = STUD_TABLE)
public class Student {

    @ColumnInfo(name = Constants.STUDENT_ID)
    @PrimaryKey(autoGenerate = true)
    private int studentId;

    private String name;

    @ForeignKey(entity = Department.class, parentColumns = DEPT_ID, childColumns = STUDENT_ID)
    private int deptID;

    public Student(String name, int deptID) {
        this.name = name;
        this.deptID = deptID;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeptID() {
        return deptID;
    }

    public void setDeptID(int deptID) {
        this.deptID = deptID;
    }
}
