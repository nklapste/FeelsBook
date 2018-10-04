package ca.klapstein.nklapste_feelsbook;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static ca.klapstein.nklapste_feelsbook.Feel.dateFormat;


/**
 * RecyclerView adapter for integrating a {@code FeelTreeSet}.
 */
public class FeelAdapter extends RecyclerView.Adapter<FeelAdapter.FeelViewHolder> {
    private static final String TAG = "FeelAdapter";

    private final FeelTreeSet feelTreeSet;

    FeelAdapter(FeelTreeSet feelTreeSet) {
        this.feelTreeSet = feelTreeSet;
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
     * @param position {@code int} position of the entity within the RecyclerView.
     */
    @Override
    public void onBindViewHolder(@NonNull FeelViewHolder holder, final int position) {
        Feel feel = (Feel) feelTreeSet.toArray()[position];
        holder.date.setText(dateFormat.format(feel.getDate()));
        holder.title.setText(feel.getFeeling().toString());
        holder.comment.setText(feel.getComment());
    }

    /**
     * Get the total number of items within the {@code FeelTreeSet}.
     *
     * @return {@code int} the total number of items within the {@code FeelTreeSet}.
     */
    @Override
    public int getItemCount() {
        return feelTreeSet.size();
    }

    /**
     * Provide a reference to the views for each data item.
     */
    static class FeelViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView comment;
        final TextView date;

        FeelViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.feel_name);
            date = view.findViewById(R.id.feel_date);
            comment = view.findViewById(R.id.feel_comment);
        }
    }
}