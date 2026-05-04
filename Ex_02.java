import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextName);
        button = findViewById(R.id.buttonSubmit);
        spinner = findViewById(R.id.spinnerOptions);

        String[] items = {"Option 1", "Option 2", "Option 3"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                items
        );

        spinner.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editText.getText().toString();
                String selected = spinner.getSelectedItem().toString();

                Toast.makeText(MainActivity.this,
                        "Name: " + name + ", Choice: " + selected,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}