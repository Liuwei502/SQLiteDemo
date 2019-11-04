package com.example.sqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button insert;
    private Button update;
    private Button delete;
    private Button query;
    private TextView textview;
    /**
     * 请输入要插入的数据
     */
    private EditText inset_edittext;
    /**
     * 清除
     */
    private Button insert_cleardata;
    /**
     * 请输入更新前的内容
     */
    private EditText update_before_edittext;
    /**
     * 请输入更新后的内容
     */
    private EditText update_after_edittext;
    /**
     * 清除
     */
    private Button update_cleardata;
    /**
     * 请输入要删除的内容
     */
    private EditText delete_edittext;
    /**
     * 清除
     */
    private Button delete_cleardata;
    /**
     * 清除查询
     */
    private Button clear_query;
    private MySQLite mySQLite;
    private SQLiteDatabase db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }


    private void initView() {
        inset_edittext = (EditText) findViewById(R.id.inset_edittext);
        insert = (Button) findViewById(R.id.insert);
        insert.setOnClickListener(this);
        insert_cleardata = (Button) findViewById(R.id.insert_cleardata);
        insert_cleardata.setOnClickListener(this);
        update_before_edittext = (EditText) findViewById(R.id.update_before_edittext);
        update_after_edittext = (EditText) findViewById(R.id.update_after_edittext);
        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(this);
        update_cleardata = (Button) findViewById(R.id.update_cleardata);
        update_cleardata.setOnClickListener(this);
        delete_edittext = (EditText) findViewById(R.id.delete_edittext);
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        delete_cleardata = (Button) findViewById(R.id.delete_cleardata);
        delete_cleardata.setOnClickListener(this);
        query = (Button) findViewById(R.id.query);
        query.setOnClickListener(this);
        clear_query = (Button) findViewById(R.id.clear_query);
        clear_query.setOnClickListener(this);
        textview = (TextView) findViewById(R.id.textview);
        //依靠DatabaseHelper的构造函数创建数据库
        mySQLite = new MySQLite(this,"users_db",null,1);
        db = mySQLite.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        String insetData = inset_edittext.getText().toString();
        String deleteData = delete_edittext.getText().toString();
        String updateAfterData = update_after_edittext.getText().toString();
        String updateBeforeData = update_before_edittext.getText().toString();

        switch (v.getId()) {
            default:
                break;
               //插入
            case R.id.insert:
                //创建存放数据的ContentValues对象
                ContentValues contentValues = new ContentValues();
                contentValues.put("name",insetData);
                //数据库执行插入命令
                db.insert("user",null,contentValues);
                break;
                //清空输入框
            case R.id.insert_cleardata:
                inset_edittext.setText("");
                break;
                //更新
            case R.id.update:
                //创建ContentValues对象存放需要给的内容
                ContentValues contentValues1 = new ContentValues();
                contentValues1.put("name",updateAfterData);
                //数据库执行更新语句
                //第三个参数:需要修改的是哪个  第四个参数:想要修改成的
                db.update("user",contentValues1,"name = ?",new String[]{updateBeforeData});
                break;
                //清空输入框
            case R.id.update_cleardata:
                update_after_edittext.setText("");
                update_before_edittext.setText("");
                break;
                //删除
            case R.id.delete:
                db.delete("user","name = ?",new String[]{deleteData});
                break;
                //清空输入框
            case R.id.delete_cleardata:
                delete_edittext.setText("");
                break;
                //查询全部
            case R.id.query:
                //创建游标对象
                Cursor cursor = db.query("user", new String[]{"name"}, null, null, null, null, null);
                //利用游标遍历所有数据对象
                //为了显示全部，把所有对象连接起来，放到TextView中
                    String textData = "";
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        textData = textData+"\n"+name;
                    }
                    textview.setText(textData);
                    //关闭游标释放资源
                cursor.close();
                break;
                //清空查询内容
            case R.id.clear_query:
                textview.setText("");
                textview.setText("查询内容为空");
                break;
        }
    }




}
