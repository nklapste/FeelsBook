package ca.klapstein.nklapste_feelsbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomListAdapter  extends ArrayAdapter<FeelCard> {

    private static final String TAG = "CustomListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView title;
        TextView comment;

    }

    /**
     * Default constructor for the CustomListAdapter
     */
    CustomListAdapter(Context context, int resource, ArrayList<FeelCard> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the persons information
        String title = getItem(position).getFeel().toString();

        final FeelCard feel = getItem(position);
        try{
            //create the view result for showing the animation
            final View result;

            //ViewHolder object
            final ViewHolder holder;

            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(mResource, parent, false);
                holder= new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.cardTitle);
                holder.comment = (TextView) convertView.findViewById(R.id.feel_comment);

                result = convertView;
                final Button button_delete = convertView.findViewById(R.id.button_delete);
                button_delete.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Code here executes on main thread after user presses button
                        Log.d(TAG, "pushed button_delete");
                        remove(feel);
                    }
                });

                final Button button_comment = convertView.findViewById(R.id.button_comment);
                button_comment.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Code here executes on main thread after user presses button
                        Log.d(TAG, "pushed button_comment");
                        feel.setComment("comment");
                        holder.comment.setText("comment");
                    }
                });


                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            Animation animation = AnimationUtils.loadAnimation(mContext,
                    (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
            result.startAnimation(animation);
            lastPosition = position;

            holder.title.setText(title);
            return convertView;
        }catch (IllegalArgumentException e){
            Log.e(TAG, "getView: IllegalArgumentException: " + e.getMessage());
            return convertView;
        }

    }

}