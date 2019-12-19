package com.example.cfcompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
// import com.example.cfcompanion.databinding.ListItemBinding;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendViewHolder> {
  private static final String TAG = "FriendsAdapter";
  //  private ListItemBinding listItemBinding;
  private ArrayList<String> friendHandles;
  private Context context;
  private LayoutInflater inflater;

  public FriendsAdapter(ArrayList<String> friendHandles, Context context) {
    this.friendHandles = friendHandles;
    this.context = context;
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View listItemLayoutView = inflater.inflate(R.layout.list_item, parent, false);
    return new FriendViewHolder(listItemLayoutView, this);
  }

  @Override
  public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
    String currentFriend = friendHandles.get(position);
    holder.friendTextView.setText(currentFriend);
  }

  @Override
  public int getItemCount() {
    return friendHandles.size();
  }

  public class FriendViewHolder extends RecyclerView.ViewHolder {
    final TextView friendTextView;
    final FriendsAdapter friendsAdapter;

    public FriendViewHolder(View layoutView, FriendsAdapter adapter) {
      super(layoutView);
      friendTextView = layoutView.findViewById(R.id.friendTextView);
      friendsAdapter = adapter;
    }
  }
}
