package com.example.tvstream;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChannelAdapter channelAdapter;
    private List<Channel> channelList;
    private DatabaseHelper dbHelper;
    private Button addChannelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Database Helper
        dbHelper = new DatabaseHelper(this);

        // Load Channels from Database
        channelList = loadChannelsFromDatabase();

        // Set Adapter
        channelAdapter = new ChannelAdapter(this, channelList);
        recyclerView.setAdapter(channelAdapter);

        // Add channel button click listener
        addChannelButton = findViewById(R.id.addChannelButton);
        addChannelButton.setOnClickListener(v -> showAddChannelDialog());
    }

    private List<Channel> loadChannelsFromDatabase() {
        List<Channel> channels = new ArrayList<>();

        // Fetch all channels from the database
        Cursor cursor = dbHelper.getAllChannels();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String url = cursor.getString(cursor.getColumnIndexOrThrow("url"));
                channels.add(new Channel(name, url));
            }
            cursor.close(); // Close the cursor to free resources
        }

        return channels;
    }

    // Method to show the dialog for adding a new channel
    private void showAddChannelDialog() {
        // Create the dialog view
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_channel, null);
        EditText channelNameEditText = dialogView.findViewById(R.id.channelNameEditText);
        EditText channelUrlEditText = dialogView.findViewById(R.id.channelUrlEditText);

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Channel")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String channelName = channelNameEditText.getText().toString().trim();
                    String channelUrl = channelUrlEditText.getText().toString().trim();

                    if (channelName.isEmpty() || channelUrl.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please enter both name and URL", Toast.LENGTH_SHORT).show();
                    } else {
                        addChannelToDatabase(channelName, channelUrl);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // Method to add a new channel to the database
    private void addChannelToDatabase(String name, String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the new channel into the database
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("url", url);

        long newRowId = db.insert("channels", null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Channel added successfully", Toast.LENGTH_SHORT).show();

            // Reload the channels after adding
            channelList.clear();
            channelList.addAll(loadChannelsFromDatabase());
            channelAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Failed to add channel", Toast.LENGTH_SHORT).show();
        }
    }
}
