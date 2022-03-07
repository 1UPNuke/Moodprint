package fi.metropolia.valemajoneesi.moodprint;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class EmotionAdapter extends RecyclerView.Adapter<EmotionAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;
        private final View view;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View
            view = v;
            textView = (TextView) v.findViewById(R.id.textView);
            imageView = (ImageView) v.findViewById(R.id.imageView);
        }

        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public View getView() {
            return view;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emotion_grid_item, parent, false);
        view.getLayoutParams().height = parent.getMeasuredHeight() / 5;
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(EmotionTracker.getInstance().getEmotion(i).getName());

        ImageView iv = viewHolder.getImageView();
        iv.setContentDescription(EmotionTracker.getInstance().getEmotion(i).getName());
        iv.setImageDrawable(
                viewHolder.getView().getContext().getDrawable(
                  EmotionTracker.getInstance().getEmotion(i).getImageId()
                )
        );
        ImageViewCompat.setImageTintList(iv, ColorStateList.valueOf(Color.rgb(128, 128, 128)));

        viewHolder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()) {
                    EmotionTracker.getInstance().selectEmotion(i);
                    ImageViewCompat.setImageTintList((ImageView) view, null);
                }
                else {
                    EmotionTracker.getInstance().unselectEmotion(i);
                    ImageViewCompat.setImageTintList((ImageView) view, ColorStateList.valueOf(Color.rgb(128, 128, 128)));
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return EmotionTracker.getInstance().getEmotions().size();
    }
}
