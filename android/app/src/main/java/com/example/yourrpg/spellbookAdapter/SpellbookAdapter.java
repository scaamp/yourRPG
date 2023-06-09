package com.example.yourrpg.spellbookAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yourrpg.R;

import java.text.DateFormat;
import java.util.ArrayList;

public class SpellbookAdapter extends RecyclerView.Adapter<SpellbookAdapter.ViewHolder> {
    private Context context;
    private Drawable drawable;
    private ArrayList<SpellbookViewHolderAdaptable> list = new ArrayList();
    private SpellbookRemover spellbookRemover;

    public SpellbookAdapter(ArrayList<SpellbookViewHolderAdaptable> list, SpellbookRemover spellbookRemover, Context context)
    {
        this.context = context;
        this.list = list;
        this.spellbookRemover = spellbookRemover;
        //Collections.sort(list, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_spellbook_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        SpellbookViewHolderAdaptable item = list.get(position);
        //drawable = context.getResources().getDrawable(item.getCategoryDrawable());
        //holder.activityImageView.setImageDrawable(drawable);
        DateFormat dateFormat = DateFormat.getDateInstance();
        //holder.leftLabelTopTextView.setText(dateFormat.format(item.getDate()));
        holder.spellTextView.setText("\"" + item.getText() + "\"");
        holder.spellDateTextView.setText(dateFormat.format(item.getDate()));
        holder.spellTrainerTextView.setText(item.getTrainer());
        holder.spellRankTextView.setText("Rank: " + item.getRank());
        holder.trashImageView.setOnClickListener(v ->
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure to delete " + holder.spellTextView.getText() + "?");
            builder.setPositiveButton("Yes", (dialog, which) -> spellbookRemover.remove(list.remove(position)));
            builder.setNeutralButton("No", null);
            builder.create().show();
        });
    }

    @Override
    public int getItemCount()
    {
        if (list == null)
        {
            return 0;
        } else
        {
            return list.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        protected TextView spellTextView;
        protected TextView spellDateTextView;
        protected TextView spellTrainerTextView;
        protected TextView spellRankTextView;
        protected ImageView trashImageView;
        protected ImageView activityImageView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            this.activityImageView = (ImageView) itemView.findViewById(R.id.activity_imageview);
            this.activityImageView.setImageResource(R.drawable.spell);
            this.trashImageView = (ImageView) itemView.findViewById(R.id.trash_image_view);
            this.spellTextView = (TextView) itemView.findViewById(R.id.spellTextView);
            this.spellDateTextView = (TextView) itemView.findViewById(R.id.spellDateTextView);
            this.spellTrainerTextView = (TextView) itemView.findViewById(R.id.spellStatTextView);
            this.spellRankTextView = (TextView) itemView.findViewById(R.id.spellStatPointsTextView);
        }
    }
}
