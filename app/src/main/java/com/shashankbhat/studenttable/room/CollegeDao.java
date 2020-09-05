package com.shashankbhat.studenttable.room;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.shashankbhat.studenttable.model.Department;
import com.shashankbhat.studenttable.model.SearchHistory;
import com.shashankbhat.studenttable.model.Student;

import java.util.List;

/**
 * Created by SHASHANK BHAT on 04-Sep-20.
 */

@Dao
public interface CollegeDao {

    @Query("SELECT * FROM stud_table")
    List<Student> findAllStudents();

    @Query("SELECT * FROM dept_table")
    List<Department> findAllDepartments();

    @Query("SELECT * FROM stud_table INNER JOIN DEPT_TABLE WHERE deptID=dept_id")
    Cursor findAllStud();

    @Query("SELECT * FROM dept_table")
    Cursor findAllDept();

    @Query("SELECT * FROM dept_table WHERE deptFullName LIKE '%'||:dept||'%' ")
    Cursor findAllDept(String dept);

    @Query("SELECT * FROM search_history_table")
    Cursor findAllSearch();

    @Insert(entity = Student.class)
    void insertStudent(Student student);

    @Insert(entity = Department.class)
    void insertDepartment(Department department);

    @Query("DELETE FROM stud_table WHERE student_id = :id")
    int delete(long id);

    @Insert
    void insertSearch(SearchHistory searchHistory);

}
