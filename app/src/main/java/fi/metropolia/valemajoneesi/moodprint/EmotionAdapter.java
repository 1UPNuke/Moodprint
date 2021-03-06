package fi.metropolia.valemajoneesi.moodprint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        viewHolder.getTextView().setText(EmotionTracker.getEmotion(i).getName());

        ImageView iv = viewHolder.getImageView();
        iv.setContentDescription(EmotionTracker.getEmotion(i).getName());
        iv.setImageDrawable(
                viewHolder.getView().getContext().getDrawable(
                  EmotionTracker.getEmotion(i).getImageId()
                )
        );
        iv.setAlpha(0.5f);
        viewHolder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                // If selected highlight and save the selection
                if(view.isSelected()) {
                    EmotionTracker.selectEmotion(i);
                    view.setAlpha(1f);
                }
                else {
                    EmotionTracker.unselectEmotion(i);
                    view.setAlpha(0.5f);
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return EmotionTracker.getEmotions().size();
    }
}
