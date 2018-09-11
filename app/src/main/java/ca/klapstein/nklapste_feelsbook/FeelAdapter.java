package ca.klapstein.nklapste_feelsbook;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


class FeelAdapter extends RecyclerView.Adapter<FeelAdapter.FeelViewHolder> {
    private static final String TAG = "FeelAdapter";
    private ArrayList<Feel> feelsList;

    public FeelAdapter(ArrayList<Feel> feelList) {
        this.feelsList = feelList;
    }

    @NonNull
    @Override
    public FeelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feel_view, parent, false);
        return new FeelViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull FeelViewHolder holder, final int position) {
        holder.date.setText(feelsList.get(position).getDate());
        holder.title.setText(feelsList.get(position).getFeeling());
        holder.comment.setText(feelsList.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return feelsList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class FeelViewHolder extends RecyclerView.ViewHolder {
        public TextView title, comment, date, button;

        public FeelViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.feel_name);
            date = (TextView) view.findViewById(R.id.feel_date);
            comment = (TextView) view.findViewById(R.id.feel_comment);
        }
    }
}