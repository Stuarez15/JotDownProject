  package ftmk.bitp3453.jotdown;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

  public class AddNoteActivity extends Activity implements View.OnClickListener {

    private Button addTodobtn;
    private EditText titleEditText;
    private EditText descEditText;
    private DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Note");
        setContentView(R.layout.activity_add_note);

        titleEditText = findViewById(R.id.title_editText);
        descEditText = findViewById(R.id.description_editText);
        addTodobtn = findViewById(R.id.add_record);

        dbManager = new DBManager(this);
        dbManager.open();
        addTodobtn.setOnClickListener(this);
    }

      @Override
      public void onClick(View v) {
          switch (v.getId()){
              case R.id.add_record:
                  final String name = titleEditText.getText().toString();
                  final  String desc = descEditText.getText().toString();

                  dbManager.insert(name, desc);

                  Intent main = new Intent(AddNoteActivity.this, MainActivity.class)
                          .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                  startActivity(main);
                  break;
          }

      }
  }