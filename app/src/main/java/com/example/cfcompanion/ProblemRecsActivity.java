package com.example.cfcompanion;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProblemRecsActivity extends AppCompatActivity {

  private List<ProblemRecs> problemRecs = new ArrayList<>();
  private RecyclerView recyclerView;
  private ProblemRecsRecyclerAdapter problemRecsAdapter;
  private CfApiHelper cfApiHelper = new CfApiHelper();
  private Gson gson = new Gson();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_problem_recs);

    final String username = getIntent().getStringExtra("username");
    final List<String> friends = getIntent().getStringArrayListExtra("friends");

    recyclerView = findViewById(R.id.problemRecsRecyclerView);
    problemRecsAdapter = new ProblemRecsRecyclerAdapter(this, problemRecs);
    recyclerView.setAdapter(problemRecsAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    createData(username, friends);
  }

  void createData(String username, List<String> friends) {
    List<Future<Response>> responseFutures = new ArrayList<>();
    Future<Response> userResponse = cfApiHelper.getUserStatus(username);
    for (String friend : friends) {
      responseFutures.add(cfApiHelper.getUserStatus(friend));
    }
    User user = createUser(userResponse, username);
    if (user == null) {
      return;
    }
    List<User> friendUsers = new ArrayList<>();
    for (int i = 0; i < friends.size(); i++) {
      String handle = friends.get(i);
      Future<Response> responseFuture = responseFutures.get(i);
      User friend = createUser(responseFuture, handle);
      if (friend != null) friendUsers.add(friend);
    }
    List<ProblemRecs> recs = getProblemRecs(user, friendUsers);
    Collections.sort(recs, (o1, o2) -> o2.numberFriends() - o1.numberFriends());
    recs = recs.subList(0, 10);
    problemRecs.addAll(recs);
    problemRecsAdapter.notifyDataSetChanged();
  }

  List<ProblemRecs> getProblemRecs(User user, List<User> friends) {
    List<ProblemRecs> problemRecs = new ArrayList<>();
    HashMap<Problem, Integer> problemsToCount = new HashMap<>();
    Set<Problem> userSolvedProblems = user.getSolvedProblems();
    for (User friend : friends) {
      for (Problem solvedProblem : friend.getSolvedProblems()) {
        if (userSolvedProblems.contains(solvedProblem)) {
          continue;
        }
        Integer count = problemsToCount.getOrDefault(solvedProblem, 0);
        problemsToCount.put(solvedProblem, count + 1);
      }
    }
    problemsToCount.forEach(
        (k, v) -> problemRecs.add(ProblemRecs.builder().setProblem(k).setNumberFriends(v).build()));
    return problemRecs;
  }

  User createUser(Future<Response> responseFuture, String handle) {
    try {
      Response response = responseFuture.get();
      JSONArray results = new JSONObject(response.body().string()).getJSONArray("result");
      User user = new User();
      user.setHandle(handle);
      for (int i = 0; i < results.length(); i++) {
        Problem problem =
            gson.fromJson(
                results.getJSONObject(i).getJSONObject("problem").toString(), Problem.class);

        String verdict = results.getJSONObject(i).getString("verdict");
        if (problem == null) continue;
        if (verdict.equals("OK")) {
          user.addSolvedProblem(problem);
        } else {
          user.addAttemptedProblem(problem);
        }
      }
      return user;
    } catch (ExecutionException | InterruptedException | IOException | JSONException e) {
      e.printStackTrace();
      Log.d("ProblemRecsActivity", e.getMessage(), e.getCause());
      return null;
    }
  }
}
