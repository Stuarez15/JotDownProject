package ftmk.bitp3453.jotdown;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ToDoActivity extends AppCompatActivity implements DialogCloseListener{


    private DatabaseHelper db;

    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton fab;

    private List<ToDoModel> taskList;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
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
        setContentView(R.layout.activity_to_do);


        db = new DatabaseHelper(this);
        db.openDatabase();

        tasksRecyclerView = findViewById(R.id.tasks_recyclerview);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db, ToDoActivity.this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        fab = findViewById(R.id.fab_task);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);

            tasksAdapter.setTasks(taskList);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
                }
            });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout =  findViewById((R.id.my_drawer_layout2));
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout
                , R.string.nav_open
                , R.string.nav_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        navigationView = findViewById(R.id.nav_menu2);
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
                        intent = new Intent(ToDoActivity.this, ToDoActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_reminder:
                        intent = new Intent(ToDoActivity.this, ReminderActivity.class);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });



    }

        @Override
        public void handleDialogClose(DialogInterface dialog) {
            taskList = db.getAllTasks();
            Collections.reverse(taskList);
            tasksAdapter.setTasks(taskList);
            tasksAdapter.notifyDataSetChanged();
        }

}