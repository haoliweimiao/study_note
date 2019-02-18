package com.hlw.sqlitedemo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private SQLiteDatabase db;
    private MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainController = new MainController();

        //依靠DatabaseHelper带全部参数的构造函数创建数据库
        SQLiteHelper dbHelper = new SQLiteHelper(MainActivity.this, SQLiteHelper.DB_NAME, null, SQLiteHelper.DB_VERSION);
        db = dbHelper.getWritableDatabase();

        Button btnInsert = (Button) findViewById(R.id.btn_insert);
        btnInsert.setOnClickListener(this);

        Button btnDelete = (Button) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);

        Button btnUpdate = (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);

        Button btnQuery = (Button) findViewById(R.id.btn_query);
        btnQuery.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                mainController.add(db, "test1");
                mainController.add(db, String.format("name%s", System.currentTimeMillis()));
                break;
            case R.id.btn_delete:
                mainController.delete(db, "test1");
                break;
            case R.id.btn_update:
                mainController.update(db, "test1", "test2");
                break;
            case R.id.btn_query:
                String logString = mainController.query(db);
                Log.e("!!!!!!", String.format("\n%s", logString));
                break;
        }
    }
}
