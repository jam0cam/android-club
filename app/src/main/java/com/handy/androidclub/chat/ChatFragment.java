package com.handy.androidclub.chat;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.handy.androidclub.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatFragment extends ListFragment {
    private static final String FIREBASE_URL = "https://a-team.firebaseio.com";

    private String mUsername;
    private Firebase mFirebaseRef;
    private ChatListAdapter mChatListAdapter;

    @Bind(R.id.chat_messageInput)
    EditText mInputText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mUsername = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        mFirebaseRef = new Firebase(FIREBASE_URL).child("chat");
        // Setup our input methods. Enter key on the keyboard or pushing the send button
        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        final ListView listView = getListView();
        mChatListAdapter = new ChatListAdapter(
                mFirebaseRef.limitToLast(50),
                getActivity(),
                R.layout.view_chat_message,
                mUsername
        );
        listView.setAdapter(mChatListAdapter);
        mChatListAdapter.registerDataSetObserver(
                new DataSetObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        listView.setSelection(mChatListAdapter.getCount() - 1);
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        mChatListAdapter.cleanup();
    }

    @OnClick(R.id.chat_sendButton)
    void sendMessage() {
        String input = mInputText.getText().toString();
        if (!input.isEmpty()) {
            Chat chat = new Chat(input, mUsername);
            mFirebaseRef.push().setValue(chat);
            mInputText.setText("");
        }
    }
}
