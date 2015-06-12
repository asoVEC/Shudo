package model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aki on 15/06/02.
 */
public class Task {
    private final String TAG = "Task.class";
    private int taskId = -1;
    private String content = null;
    private int important_level = -2;
    private Context context;


    public void setContent(String content) {
        this.content = content;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setImportant_level(int important_level) {
        this.important_level = important_level;
    }

    public int getImportant_level() {
        return important_level;
    }

    public String getContent() {
        return content;
    }

    public Task(Context context) {
        this.context = context;
    }

    //task登録　属性：（TODO名、今日or明日のフラグ）　
    public void addTask() {
        if (content == null || important_level == -2) {
            Log.e(TAG, "addTask():必要な属性がありません");
        } else {
            SQLiteDatabase sdb = null;
            MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
            try {
                sdb = helper.getWritableDatabase();
                //もしくは、
                //sdb = helper.getReadableDatabase();
            } catch (SQLiteException e) {
                Log.e(TAG, "SQLiteDatabase接続に失敗しました");
                //異常終了
            }
            try {
                String sql = "INSERT INTO task(task,important_lv) VALUES(?,?)";
                Log.d(TAG, "(" + content + "," + important_level + ")をtaskテーブルに挿入します");
                //INSERT,DELETE,UPDATE文の実行メソッド=execSQL
//                sdb.execSQL(sql, new String[]{"task" + content, "important_lv" + 0});
                sdb.execSQL(sql, new Object[]{content, important_level});
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    //DBからタスク一覧を取得するメソッドのひな形 引:SELECT文
    public List<Task> getTask(String sqlstr) {
        SQLiteDatabase sdb = null;
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
        try {
            sdb = helper.getWritableDatabase();
            //もしくは、
            //sdb = helper.getReadableDatabase();
        } catch (SQLiteException e) {
            Log.e(TAG, "SQLiteDatabase接続に失敗しました");
            //異常終了
        }
        List<Task> taskList = new ArrayList<>();
        String[] param = {};
        if (sdb != null) {
            Cursor c = sdb.rawQuery(sqlstr,param);
            if(c.moveToFirst()){
                do {
                    Task task = new Task(context);
                    task.taskId = c.getInt(0);
                    task.content = c.getString(1);
                    task.important_level = c.getInt(2);
                    taskList.add(task);
                }while(c.moveToNext());
            }
        }
        return taskList;
    }
    public List<Task> getAllTask(){
        String sqlstr = "select * from task;";
        return getTask(sqlstr);
    }
    public List<Task> getTaskByImportantLv(int lv){
        String sqlstr = "select * from task where important_lv = "+lv+";";
        return getTask(sqlstr);
    }

    public void deleteTask() {
        Log.d(TAG, "deleteTask()がよばれました。taskId :" +taskId+"のタスクを削除します");
        if (taskId == -1) {

        } else {
            SQLiteDatabase sdb = null;
            MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
            try {
                sdb = helper.getWritableDatabase();
                //もしくは、
                //sdb = helper.getReadableDatabase();
            } catch (SQLiteException e) {
                Log.e(TAG, "SQLiteDatabase接続に失敗しました");
                //異常終了
            }
            try {
                String sql = "delete from task where _id == " + taskId;
                sdb.execSQL(sql);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public void updateTask(int important_level){
        Log.d(TAG, "updateTask()がよばれました。taskId :" +taskId+"のタスクを更新します");
        if (taskId == -1) {

        } else {
            SQLiteDatabase sdb = null;
            MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
            try {
                sdb = helper.getWritableDatabase();
                //もしくは、
                //sdb = helper.getReadableDatabase();
            } catch (SQLiteException e) {
                Log.e(TAG, "SQLiteDatabase接続に失敗しました");
                //異常終了
            }
            try {
                String sql = "update table task set important_lv = "+important_level+" where _id == " + taskId;
                sdb.execSQL(sql);
            } catch (Exception e) {
                e.getMessage();
            }
        }

    }


}