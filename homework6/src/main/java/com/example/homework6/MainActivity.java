package com.example.homework6;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DBAdapter dbAdapter;
    private List<String> studentInfoList;
    private MyAdapter adapter;
    private EditText tvId, tvName, tvGender, tvAge;
    private TextView tvQueryResult;
    private ContentResolver resolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        initDB(dbAdapter);
        initView();
        studentInfoList = new ArrayList<>();
        adapter = new MyAdapter(studentInfoList, this);
        ListView listView = findViewById(R.id.db_info);
        listView.setAdapter(adapter);
        refreshShow();
        resolver = getContentResolver();
    }

    private void initView() {
        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
        Button btnDel = findViewById(R.id.btn_delete);
        btnDel.setOnClickListener(this);
        Button btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        Button btnQuery = findViewById(R.id.btn_query);
        btnQuery.setOnClickListener(this);
        tvId = findViewById(R.id.stu_id);
        tvName = findViewById(R.id.stu_name);
        tvGender = findViewById(R.id.stu_gender);
        tvAge = findViewById(R.id.stu_age);
        tvQueryResult = findViewById(R.id.tv_query_result);
    }

    private void initDB(DBAdapter dbAdapter) {
        Student student1 = new Student(110101, "张三", "男", 22);
        Student student2 = new Student(110102, "李四", "女", 21);
        Student student3 = new Student(110103, "王五", "男", 24);
        dbAdapter.insert(student1);
        dbAdapter.insert(student2);
        dbAdapter.insert(student3);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
//                addData();//SQLite
                addDataCp();//ContentProvider
                break;
            case R.id.btn_delete:
//                deleteData();//SQLite
                deleteDataCp();//ContentProvider
                break;
            case R.id.btn_update:
//                updateData();//SQLite
                updateDataCp();//ContentProvider
                break;
            case R.id.btn_query:
//                queryData();//SQLite
                queryDataCp();//ContentProvider
                break;

        }
    }

    private void queryDataCp() {
        /**
         * 使用ContentProvider
         * */
        try {
            long id = Integer.parseInt(tvId.getText().toString());
            Cursor cursor = resolver.query(StudentProvider.CONTENT_URI,
                    new String[]{StudentProvider.KEY_ID, StudentProvider.KEY_NAME, StudentProvider.KEY_GENDER, StudentProvider.KEY_AGE},
                    StudentProvider.KEY_ID + "=" + id, null, null);
            List<Student> studentList = convertToStudent(cursor);
            tvQueryResult.setText(studentList.get(0).toString());
            Toast.makeText(this, "查询成功", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "id格式错误", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (NullPointerException b) {
            tvQueryResult.setText("无信息");
            Toast.makeText(this, "无信息", Toast.LENGTH_SHORT).show();
        }
        clearEditText();

    }

    private void queryData() {
        /**
         * 使用SQLite
         * */
        try {
            long id = Integer.parseInt(tvId.getText().toString());
            List<Student> studentList = dbAdapter.getOneData(id);
            tvQueryResult.setText(studentList.get(0).toString());
            Toast.makeText(this, "查询成功", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException a) {
            Toast.makeText(this, "id格式错误", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException b) {
            tvQueryResult.setText("无信息");
            Toast.makeText(this, "无信息", Toast.LENGTH_SHORT).show();
        }
        clearEditText();
    }

    private void updateDataCp() {
        /**
         * 使用ContentProvider
         * */
        try {
            int id = Integer.parseInt(tvId.getText().toString());
            List<Student> studentList = dbAdapter.getOneData(id);
            String name = tvName.getText().toString().equals("") ? studentList.get(0).name : tvName.getText().toString();
            String gender = tvGender.getText().toString().equals("") ? studentList.get(0).gender : tvGender.getText().toString();
            int age = tvAge.getText().toString().equals("") ? studentList.get(0).age : Integer.parseInt(tvAge.getText().toString());
            Student student = new Student(id, name, gender, age);
            ContentValues values = new ContentValues();
            values.put(StudentProvider.KEY_ID, id);
            values.put(StudentProvider.KEY_NAME, name);
            values.put(StudentProvider.KEY_GENDER, gender);
            values.put(StudentProvider.KEY_AGE, age);
            int count = resolver.update(StudentProvider.CONTENT_URI, values, StudentProvider.KEY_ID + "=" + id, null);
            refreshShow();
            Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
            tvQueryResult.setText(student.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "id格式错误", Toast.LENGTH_SHORT).show();
        }
        clearEditText();
    }

    private void updateData() {
        /**
         * 使用SQLite
         * */
        try {
            int id = Integer.parseInt(tvId.getText().toString());
            List<Student> studentList = dbAdapter.getOneData(id);
            String name = tvName.getText().toString().equals("") ? studentList.get(0).name : tvName.getText().toString();
            String gender = tvGender.getText().toString().equals("") ? studentList.get(0).gender : tvGender.getText().toString();
            int age = tvAge.getText().toString().equals("") ? studentList.get(0).age : Integer.parseInt(tvAge.getText().toString());
            Student student = new Student(id, name, gender, age);
            dbAdapter.updateOneData(id, student);
            refreshShow();
            Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
            tvQueryResult.setText(student.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "id格式错误", Toast.LENGTH_SHORT).show();
        }
        clearEditText();
    }

    private void deleteDataCp() {
        /**
         * 使用ContentProvider
         * */
        try {
            long id = Integer.parseInt(tvId.getText().toString());
            int count = resolver.delete(StudentProvider.CONTENT_URI,StudentProvider.KEY_ID+"="+id,null);
            if (count > 0) {
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                refreshShow();
            } else {
                Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException a) {
            Toast.makeText(this, "id格式错误", Toast.LENGTH_SHORT).show();
        }
        clearEditText();
    }

    private void deleteData() {
        /**
         * 使用SQLite
         * */
        try {
            long id = Integer.parseInt(tvId.getText().toString());
            long resultCode = dbAdapter.deleteOneData(id);
            if (resultCode > 0) {
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                refreshShow();
            } else {
                Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException a) {
            Toast.makeText(this, "id格式错误", Toast.LENGTH_SHORT).show();
        }
        clearEditText();
    }

    private void addDataCp() {
        /**
         * 使用ContentProvider
         * */
        Student student = getInfo();
        ContentValues values = new ContentValues();
        values.put(StudentProvider.KEY_ID, student.id);
        values.put(StudentProvider.KEY_NAME, student.name);
        values.put(StudentProvider.KEY_GENDER, student.gender);
        values.put(StudentProvider.KEY_AGE, student.age);
        if (student != null && resolver.insert(StudentProvider.CONTENT_URI, values) != null) {
            refreshShow();
            Toast.makeText(this, "成功添加数据", Toast.LENGTH_SHORT).show();
            clearEditText();
        } else {
            Toast.makeText(this, "添加数据失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void addData() {
        /**
         * 使用SQLite
         * */
        Student student = getInfo();
        if (student != null && dbAdapter.insert(student) != -1) {
            refreshShow();
            Toast.makeText(this, "成功添加数据", Toast.LENGTH_SHORT).show();
            clearEditText();
        } else {
            Toast.makeText(this, "添加数据失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearEditText() {
        tvId.setText("");
        tvName.setText("");
        tvGender.setText("");
        tvAge.setText("");
    }

    private void refreshShow() {
        List<Student> studentList = dbAdapter.getAllData();
        studentInfoList.clear();
        for (Student s : studentList) {
            studentInfoList.add(s.toString());
        }
        adapter.notifyDataSetChanged();
    }


    private Student getInfo() {
        try {
            int id = Integer.parseInt(tvId.getText().toString());
            String name = tvName.getText().toString();
            String gender = tvGender.getText().toString();
            int age = Integer.parseInt(tvAge.getText().toString());
            return new Student(id, name, gender, age);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "数据格式错误", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @SuppressLint("Range")
    public List<Student> convertToStudent(Cursor cursor) {
        int count = cursor.getCount();
        if (count == 0 || !cursor.moveToFirst()) return null;
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Student student = new Student();
            student.id = cursor.getInt(0);
            student.name = cursor.getString(cursor.getColumnIndex(StudentProvider.KEY_NAME));
            student.gender = cursor.getString(cursor.getColumnIndex(StudentProvider.KEY_GENDER));
            student.age = cursor.getInt(cursor.getColumnIndex(StudentProvider.KEY_AGE));
            studentList.add(student);
            cursor.moveToNext();
        }
        return studentList;
    }
}