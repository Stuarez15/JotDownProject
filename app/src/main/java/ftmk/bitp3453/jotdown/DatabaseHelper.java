package ftmk.bitp3453.jotdown;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public static  final String dbName = "JotDown";
    public static  final String tblName = "Notes";

    public static  final String colID = "_id";
    public static  final String colTitle = "noteTitle";
    public static  final String colDesc = "noteDesc";

    public static final String TODO = "todo";
    public static final String ID = "id";
    public static final String TASK = "task";
    public static final String STATUS = "status";

    public static final String tblName2 = "CalenderEvent";
    public static final String Date = "Date";
    public static final String Event = "Event";

    public static final String  crtTable = "create table "
            + tblName + "( "
            + colID + " integer primary key autoincrement, "
            + colTitle + " Text, "
            + colDesc + " Text);";

    public static final String crtTable2 = "create table if not exists "
            + TODO + "( "
            + ID +" integer primary key autoincrement, "
            + TASK +" text, "
            + STATUS +" INTEGER);";

    //sini create table CalenderEvent
    public static final String crtTable3 = "create table if not exists "
            + tblName2 + "( "
            + Date +" TEXT primary key, "
            + Event +" TEXT);";


    public DatabaseHelper(@Nullable Context context){

        super(context, dbName, null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(crtTable);
        db.execSQL(crtTable2);
        db.execSQL(crtTable3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + tblName);
        db.execSQL("drop table if exists "+ TODO);
        db.execSQL("drop table if exists "+ tblName2);
        onCreate(db);
    }

    public void openDatabase(){
        db = this.getWritableDatabase();
    }

    public void insertTask(ToDoModel task){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0);
        db.insert(TODO, null, cv);
    }

    @SuppressLint("Range")
    public List<ToDoModel> getAllTasks(){
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(TODO, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setTask(cur.getString(cur.getColumnIndex(TASK)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TODO, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void insertEvent(String Date, String Event){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", Date);
        contentValues.put("Event", Event);
        db.insert(tblName2,null,contentValues);
    }

    @SuppressLint("Range")
    public String ReadDatabase(String selectedDate){
        String data = "";
        Cursor cursor = db.query("CalenderEvent",null, "Date = ?",
                new String[] { selectedDate }, null,null,null);

        if(cursor.moveToLast()){
            data = cursor.getString(cursor.getColumnIndex("Event"));
        }
        return data;
    }

}
