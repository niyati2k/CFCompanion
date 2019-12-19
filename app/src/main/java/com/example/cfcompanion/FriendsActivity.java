package com.example.cfcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FriendsActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private FriendsAdapter friendsAdapter;
  private FloatingActionButton addFriendButton;
  private EditText addFriendText;
  private CfApiHelper cfApiHelper;
  private ArrayList<String> friendHandles = new ArrayList<>();
  private Handler mHandler;
  private Button getRecsButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.friends);

    recyclerView = findViewById(R.id.friendsRecyclerView);
    addFriendButton = findViewById(R.id.addFriendButton);
    addFriendText = findViewById(R.id.addFriendText);
    friendsAdapter = new FriendsAdapter(friendHandles, this);
    recyclerView.setAdapter(friendsAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    mHandler = new Handler(Looper.getMainLooper());
    cfApiHelper = new CfApiHelper();
    getRecsButton = findViewById(R.id.getRecsButton);

    final String username = getIntent().getStringExtra("user_name");

    addFriendButton.setOnClickListener(
        v -> {
          final int size = friendHandles.size();
          final String friendHandle = addFriendText.getText().toString();
          if (friendHandle.equals(username)) {
            addFriendText.setText("");
            saySomething("Friend handle cannot be same as user handle");
            return;
          }
          Callback callback =
              new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                  mHandler.post(() -> saySomething("Sorry, something went wrong"));
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response)
                    throws IOException {
                  if (!response.isSuccessful()) {
                    mHandler.post(
                        () -> {
                          saySomething("Handle not found");
                          addFriendText.setText("");
                        });
                    return;
                  }

                  friendHandles.add(friendHandle);
                  mHandler.post(
                      () -> {
                        friendsAdapter.notifyItemInserted(size);
                        addFriendText.setText("");
                        recyclerView.smoothScrollToPosition(size);
                      });
                }
              };
          cfApiHelper.getUserInfo(friendHandle, callback);
        });

    getRecsButton.setOnClickListener(
        v -> {
          Intent intent = new Intent(FriendsActivity.this, ProblemRecsActivity.class);
          intent.putExtra("username", username);
          intent.putExtra("friends", friendHandles);
          startActivity(intent);
        });
  }

  private void saySomething(String something) {
    Toast toast = Toast.makeText(getApplicationContext(), something, Toast.LENGTH_LONG);
    toast.show();
  }
}
