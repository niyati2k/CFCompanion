package com.example.cfcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

  private EditText handleText;
  private Button submitButton;
  private CfApiHelper cfApiHelper;
  private Handler mHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    handleText = findViewById(R.id.handleEditText);
    submitButton = findViewById(R.id.submitButton);
    cfApiHelper = new CfApiHelper();

    mHandler = new Handler(Looper.getMainLooper());

    submitButton.setOnClickListener(
        v -> {
          final String handle = handleText.getText().toString();
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
                    mHandler.post(() -> saySomething("Handle not found"));
                    return;
                  }

                  mHandler.post(
                      () -> {
                        Intent intent = new Intent(MainActivity.this, FriendsActivity.class);
                        intent.putExtra("user_name", handle);
                        startActivity(intent);
                      });
                }
              };
          cfApiHelper.getUserInfo(handle, callback);
        });
  }

  private void saySomething(String something) {
    Toast toast = Toast.makeText(getApplicationContext(), something, Toast.LENGTH_LONG);
    toast.show();
  }
}
