
package ca.klapstein.nklapste_feelsbook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private static final String TAG = "MyAdapter";
    private ArrayList<FeelCard> feelsList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, comment, date, button;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.feel_name);
            date = (TextView) view.findViewById(R.id.feel_date);
            comment = (TextView) view.findViewById(R.id.feel_comment);
        }
    }

    public MyAdapter(ArrayList<FeelCard> feelsList) {
        this.feelsList = feelsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feel_view, parent, false);
        return new MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.date.setText(feelsList.get(position).getDate());
        holder.title.setText(feelsList.get(position).getFeel().toString());
        holder.comment.setText(feelsList.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return feelsList.size();
    }
}