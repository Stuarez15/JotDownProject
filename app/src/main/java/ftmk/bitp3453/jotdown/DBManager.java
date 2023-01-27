package ftmk.bitp3453.jotdown;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context c){
        this.context = c;
    }

    public DBManager open() throws SQLException{
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public void insert(String title, String desc){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.colTitle, title);
        contentValues.put(DatabaseHelper.colDesc, desc);
        database.insert(DatabaseHelper.tblName, null, contentValues);
    }

    public Cursor fetch(){
        String[] columns = new String[] {DatabaseHelper.colID,
        DatabaseHelper.colTitle, DatabaseHelper.colDesc};

        Cursor cursor = database.query(DatabaseHelper. tblName,
                columns,
                null,
                null,
                null,
                null,
                null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long id, String title, String desc){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.colTitle, title);
        contentValues.put(DatabaseHelper.colDesc, desc);

        int i = database.update(DatabaseHelper.tblName, contentValues,
                DatabaseHelper.colID+ " = " + id, null);
        return i;
    }

    public void delete(long id){
        database.delete(DatabaseHelper.tblName, DatabaseHelper.colID + " = "
                + id, null);
    }
}
