package com.shashankbhat.studenttable.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.shashankbhat.studenttable.utils.Constants.DEPT_ID;
import static com.shashankbhat.studenttable.utils.Constants.DEPT_TABLE;

/**
 * Created by SHASHANK BHAT on 04-Sep-20.
 */

@Entity(tableName = DEPT_TABLE)
public class Department {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DEPT_ID)
    private int deptID;

    private String deptShortName, deptFullName;

    public Department(String deptShortName, String deptFullName) {
        this.deptShortName = deptShortName;
        this.deptFullName = deptFullName;
    }

    public int getDeptID() {
        return deptID;
    }

    public void setDeptID(int deptID) {
        this.deptID = deptID;
    }

    public String getDeptShortName() {
        return deptShortName;
    }

    public void setDeptShortName(String deptShortName) {
        this.deptShortName = deptShortName;
    }

    public String getDeptFullName() {
        return deptFullName;
    }

    public void setDeptFullName(String deptFullName) {
        this.deptFullName = deptFullName;
    }
}
