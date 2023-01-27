package ftmk.bitp3453.jotdown;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class ReminderActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DatabaseHelper db;
    private EditText editText;
    private CalendarView calendarView;
    private String selectedDate, Event = "", text, data ;
    private Button btnSave;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        editText = findViewById(R.id.editTextTextPersonName);
        calendarView = findViewById(R.id.calendarView2);
        btnSave = findViewById(R.id.button);

        drawerLayout =  findViewById((R.id.my_drawer_layout3));
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout
                , R.string.nav_open
                , R.string.nav_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.nav_menu3);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_notes:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_toDo:
                        intent = new Intent(ReminderActivity.this, ToDoActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_reminder:
                        intent = new Intent(ReminderActivity.this, ReminderActivity.class);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = dayOfMonth + "/" + month + "/" + year;
                data = db.ReadDatabase(selectedDate);
                editText.setText(data);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        text = editText.getText().toString();
                        db.insertEvent(selectedDate,text);
                    }
                });
            }
        });

        db = new DatabaseHelper(this);
        db.openDatabase();

    }

    public void SaveButton(View view){
    }

}