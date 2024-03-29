package com.example.androidassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    private final static String TAG = "ChatWindow";
    private ChatDatabaseHelper cdh;
    ArrayList<String> chatMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cdh = new ChatDatabaseHelper(getApplicationContext());

        SQLiteDatabase db = cdh.getReadableDatabase();
        // cdh.onUpgrade(db, 1, 2);
        Cursor cursor = db.rawQuery("SELECT * FROM messages", null);
        cursor.moveToFirst();

        Log.i(TAG, "Cursor’s  column count = " + cursor.getColumnCount() );
        while(!cursor.isAfterLast() ) {
            int columnIndex = cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);
            if (columnIndex > -1) {
                Log.i(TAG, "SQL MESSAGE:" + cursor.getString(columnIndex));
                chatMessages.add(cursor.getString(columnIndex));
            }
            cursor.moveToNext();
        }

        setContentView(R.layout.activity_chat_window);

        ListView chatWindow = (ListView) findViewById(R.id.activityChatWindowListView);
        Button sendButton = (Button) findViewById(R.id.activityChatWindowSendButton);
        EditText textMessage = (EditText) findViewById(R.id.activityChatWindowTextMessageEditText);

        ChatAdapter messageAdapter = new ChatAdapter(this);
        chatWindow.setAdapter(messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textMessage.getText().toString();

                SQLiteDatabase db = cdh.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(ChatDatabaseHelper.KEY_MESSAGE, text);
                db.insert(ChatDatabaseHelper.TABLE_NAME, null, cv);

                chatMessages.add(text);
                messageAdapter.notifyDataSetChanged();
                ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView().getRootView();
                messageAdapter.getView(chatMessages.size() - 1, v, viewGroup);
                textMessage.setText("");
            }
        });
    }
    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return chatMessages.size();
        }

        public String getItem(int position) {
            return chatMessages.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if(position % 2 == 1) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            }
            else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position));

            return result;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cdh.close();
    }
}