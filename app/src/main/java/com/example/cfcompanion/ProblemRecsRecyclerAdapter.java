package com.example.cfcompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProblemRecsRecyclerAdapter
    extends RecyclerView.Adapter<ProblemRecsRecyclerAdapter.ProblemRecsViewHolder> {

  private Context context;
  private LayoutInflater inflater;
  private List<ProblemRecs> problemRecs;

  public ProblemRecsRecyclerAdapter(Context context, List<ProblemRecs> problemRecs) {
    this.context = context;
    this.problemRecs = problemRecs;
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public ProblemRecsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View listItemLayoutView = inflater.inflate(R.layout.row, parent, false);
    return new ProblemRecsViewHolder(listItemLayoutView);
  }

  @Override
  public void onBindViewHolder(@NonNull ProblemRecsViewHolder holder, int position) {
    ProblemRecs currentProblemRec = problemRecs.get(position);
    holder.numberFriendsTextView.setText(currentProblemRec.numberFriends() + "");
    holder.ratingTextView.setText(currentProblemRec.problem().getRating() + "");
    String problemId =
        currentProblemRec.problem().getContestId() + "/" + currentProblemRec.problem().getIndex();
    holder.problemIdTextView.setText(problemId);
    holder.nameTextView.setText(currentProblemRec.problem().getName());
  }

  @Override
  public int getItemCount() {
    return problemRecs.size();
  }

  class ProblemRecsViewHolder extends RecyclerView.ViewHolder {
    final TextView problemIdTextView, nameTextView, ratingTextView, numberFriendsTextView;

    public ProblemRecsViewHolder(@NonNull View itemView) {
      super(itemView);
      problemIdTextView = itemView.findViewById(R.id.problemIdTextView);
      nameTextView = itemView.findViewById(R.id.nameTextView);
      ratingTextView = itemView.findViewById(R.id.ratingTextView);
      numberFriendsTextView = itemView.findViewById(R.id.numberFriendsTextView);
    }
  }
}
