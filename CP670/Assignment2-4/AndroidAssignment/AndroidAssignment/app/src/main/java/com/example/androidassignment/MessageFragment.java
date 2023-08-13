package com.example.androidassignment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class MessageFragment extends Fragment {
    ChatWindow cw;

    private final static String TAG = "MessageFragment";
    public MessageFragment(ChatWindow chatWindow) {
        if (chatWindow != null) {
            cw = chatWindow;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup parent,
                             @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();

        View view = inflater.inflate (R.layout.fragment_message_details, parent, false);
        TextView messageTextView = view.findViewById(R.id.fragmentMessage);
        TextView idTextView = view.findViewById(R.id.fragmentId);

        String message = arguments.getString("message");
        long id = arguments.getLong("id");

        String idConvert = Long.toString(id);

        String messageText = "Message: " + message;
        String idText = "ID: " + idConvert;

        messageTextView.setText(messageText);
        idTextView.setText(idText);

        Button deleteButton = view.findViewById(R.id.fragmentDelete);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("id", id);
                if (cw == null) {
                    getActivity().setResult(RESULT_OK, data);
                    getActivity().finish();
                } else {
                    boolean result = cw.deleteMessageById(id);
                    if (result) {
                        cw.updateMessages("Tablet");
                        Log.i(TAG, "Successfully deleted message id: " + id);
                    } else {
                        Log.i(TAG, "Error deleting message id: " + id);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
