package com.example.to_do_app;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class another_main extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private EditText inputEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        listView = findViewById(R.id.list);
        inputEditText = findViewById(R.id.edit_text);
        addButton = findViewById(R.id.btn);

        // Initialize the list of items and ArrayAdapter
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);

        // Set click listener for the add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        // Set long click listener for list items to remove tasks
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeItem(position);
                return true; // Consume the long click event
            }
        });
    }

    // Method to add a new item to the list
    private void addItem() {
        String itemText = inputEditText.getText().toString().trim();
        if (!itemText.isEmpty()) {
            items.add(itemText);
            itemsAdapter.notifyDataSetChanged(); // Update the ListView
            inputEditText.setText(""); // Clear the EditText after adding item
        } else {
            Toast.makeText(getApplicationContext(), "Please enter text", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to remove an item from the list
    private void removeItem(int position) {
        if (position >= 0 && position < items.size()) {
            String removedItem = items.remove(position);
            itemsAdapter.notifyDataSetChanged(); // Update the ListView
            Toast.makeText(getApplicationContext(), "Task removed: " + removedItem, Toast.LENGTH_SHORT).show();
        }
    }
}
