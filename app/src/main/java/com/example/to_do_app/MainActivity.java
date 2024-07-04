
package com.example.to_do_app;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listview;
    private EditText inputText;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listview = findViewById(R.id.list);
        inputText = findViewById(R.id.edit_text);
        btn = findViewById(R.id.btn);


        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.text_color_layout, items);
        listview.setAdapter(itemsAdapter);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });


        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showEditDialog(position);
                return true;
            }
        });
    }


    private void addItem() {
        String itemText = inputText.getText().toString().trim();
        if (!itemText.isEmpty()) {
            items.add(itemText);
            itemsAdapter.notifyDataSetChanged();
            inputText.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "Please enter text", Toast.LENGTH_SHORT).show();
        }
    }


    private void showEditDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit or Delete Task");


        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(items.get(position));
        builder.setView(input);


        builder.setPositiveButton("Save", (dialog, which) -> {
            String updatedText = input.getText().toString().trim();
            if (!updatedText.isEmpty()) {
                items.set(position, updatedText);
                itemsAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), "Please enter text", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Delete", (dialog, which) -> {
            items.remove(position);
            itemsAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "Task Deleted", Toast.LENGTH_SHORT).show();
        });

        builder.setNeutralButton("Cancel", (dialog, which) -> dialog.cancel());


        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
