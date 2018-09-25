package ca.klapstein.nklapste_feelsbook;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static ca.klapstein.nklapste_feelsbook.Feel.dateFormat;


/**
 * RecyclerView adapter for integrating a {@code FeelQueue}.
 */
class FeelAdapter extends RecyclerView.Adapter<FeelAdapter.FeelViewHolder> {
    private static final String TAG = "FeelAdapter";

    private FeelQueue feelsList;

    FeelAdapter(FeelQueue feelQueue) {
        this.feelsList = feelQueue;
    }

    @NonNull
    @Override
    public FeelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feel_view, parent, false);
        return new FeelViewHolder(itemView);
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     *
     * @param holder   {@code FeelViewHolder}
     * @param position {@code int} position of the entity within the RecyclerView
     */
    @Override
    public void onBindViewHolder(@NonNull FeelViewHolder holder, final int position) {
        Feel feel = (Feel) feelsList.toArray()[position];
        holder.date.setText(dateFormat.format(feel.getDate()));
        holder.title.setText(feel.getFeeling().toString());
        holder.comment.setText(feel.getComment());
    }

    @Override
    public int getItemCount() {
        return feelsList.size();
    }

    /**
     * Provide a reference to the views for each data item. Complex data items may need more than
     * one view per item, and you provide access to all the views for a data item in a view holder
     */
    public static class FeelViewHolder extends RecyclerView.ViewHolder {
        public TextView title, comment, date, button;

        FeelViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.feel_name);
            date = view.findViewById(R.id.feel_date);
            comment = view.findViewById(R.id.feel_comment);
        }
    }
}