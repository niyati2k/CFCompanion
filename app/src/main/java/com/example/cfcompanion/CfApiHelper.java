package com.example.cfcompanion;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CfApiHelper {

  private static final String BASE_URL = "https://codeforces.com/api/";
  private final OkHttpClient client = new OkHttpClient();

  public void getUserStatus(String handle, Callback callback) {
    String url = BASE_URL + "user.status?handle=" + handle + "&from=1";
    run(url, callback);
  }

  public Future<Response> getUserStatus(String handle) {
    String url = BASE_URL + "user.status?handle=" + handle + "&from=1";
    return runRequest(url);
  }

  void getUserInfo(String handle, Callback callback) {
    String url = BASE_URL + "user.info?handles=" + handle;
    run(url, callback);
  }

  private void run(String url, Callback callback) {
    Request request = new Request.Builder().url(url).build();

    client.newCall(request).enqueue(callback);
  }

  private Future<Response> runRequest(String url) {
    Request request = new Request.Builder().url(url).build();
    OkHttpResponseFuture result = new OkHttpResponseFuture();

    client.newCall(request).enqueue(result);
    return result.future;
  }

  public class OkHttpResponseFuture implements Callback {
    public final CompletableFuture<Response> future = new CompletableFuture<>();

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
      future.completeExceptionally(e);
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
      future.complete(response);
    }
  }
}
