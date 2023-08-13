package com.example.androidassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    private final static String TAG = "ChatWindow";
    private ChatDatabaseHelper cdh;
    private final ArrayList<String> chatMessages = new ArrayList<>();
    private ChatAdapter messageAdapter;
    private MessageFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cdh = new ChatDatabaseHelper(getApplicationContext());

        FrameLayout fl = (FrameLayout) findViewById(R.id.activityChatWindowFrameLayout);
        TextView messageTextView;
        TextView idTextView;
        boolean isTablet = true;
        if (fl == null) {
            Log.i(TAG, "Mobile Mode");
            isTablet = false;
        } else {
            Log.i(TAG, "Tablet Mode");
            idTextView = (TextView) findViewById(R.id.fragmentId);
            messageTextView = (TextView) findViewById(R.id.fragmentMessage);

            if (idTextView != null & messageTextView != null) {
                long id = savedInstanceState.getLong("id");
                String message = savedInstanceState.getString("message");

                idTextView.setText(Long.toString(id));
                messageTextView.setText(message);

            }
        }

        this.populateMessageAdapter();

        setContentView(R.layout.activity_chat_window);

        ListView chatWindow = (ListView) findViewById(R.id.activityChatWindowListView);
        Button sendButton = (Button) findViewById(R.id.activityChatWindowSendButton);
        EditText textMessage = (EditText) findViewById(R.id.activityChatWindowTextMessageEditText);

        messageAdapter = new ChatAdapter(this);
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
        ChatWindow chatWindowClass = this;
        boolean finalIsTablet = isTablet;
        chatWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(finalIsTablet) {
                    fragment = new MessageFragment(chatWindowClass);
                    Bundle args = new Bundle();
                    args.putLong("id", id);
                    args.putString("message", chatMessages.get(position));
                    fragment.setArguments(args);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                    ft.add(R.id.activityChatWindowFrameLayout, fragment);

                    ft.commit();
                } else {
                    Intent intent = new Intent(getApplicationContext(),MessageDetails.class);
                    intent.putExtra("id", id);
                    intent.putExtra("message", chatMessages.get(position));
                    startActivityForResult(intent, 10);
                }
            }
        });
    }
    public class ChatAdapter extends ArrayAdapter<String> {
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

        public long getItemId(int position) {
            SQLiteDatabase db = cdh.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM messages", null);
            cursor.moveToPosition(position);
            int columnIndex = cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID);
            return cursor.getLong(columnIndex);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cdh.close();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            long id = extras.getLong("id");
            boolean result = this.deleteMessageById(id);

            if (result) {
                this.updateMessages("Mobile");

                Log.i(TAG, "Message with id " + id + " deleted");
            }
        }
    }

    private void populateMessageAdapter() {
        SQLiteDatabase db = cdh.getWritableDatabase();
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
    }
    public void updateMessages(String type) {
        chatMessages.clear();

        this.populateMessageAdapter();

        messageAdapter.notifyDataSetChanged();

        if (type.equals("Tablet")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commit();
        }
    }
    public boolean deleteMessageById(long id) {
        String idString = Long.toString(id);

        SQLiteDatabase db = cdh.getWritableDatabase();
        int results = db.delete("messages", "id=?", new String[]{idString});
        return results > 0;
    }
}