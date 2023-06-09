package com.example.yourrpg.questlogAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yourrpg.R;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class QuestlogAdapter extends RecyclerView.Adapter<QuestlogAdapter.ViewHolder> {
    private Context context;
    private Drawable drawable;
    private ArrayList<QuestlogViewHolderAdaptable> list = new ArrayList();
    private QuestlogInterface questlogInterface;

    public QuestlogAdapter(ArrayList<QuestlogViewHolderAdaptable> list, QuestlogInterface questlogInterface, Context context) {
        this.context = context;
        this.list = list;
        this.questlogInterface = questlogInterface;
        Collections.sort(list, (o2, o1) -> o2.getDate().compareTo(o1.getDate()));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_questlog_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        QuestlogViewHolderAdaptable item = list.get(position);
        DateFormat dateFormat = DateFormat.getDateInstance();
        //holder.leftLabelTopTextView.setText(dateFormat.format(item.getDate()));

        holder.checkBox.setChecked(item.isDone());
        holder.questTextView.setText("\"" + item.getDesc() + "\"");
        holder.questDateTextView.setText(dateFormat.format(item.getDate()));
        holder.questStatTextView.setText(item.getStat());
        holder.questStatPointsTextView.setText("Points: " + item.getStatPoints());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    questlogInterface.questIsDone(false, holder.getAdapterPosition(), item.getStat(), item.getStatPoints());
                    item.setDone(false);
                }
                if (b) {
                    questlogInterface.questIsDone(true, holder.getAdapterPosition(), item.getStat(), item.getStatPoints());
                    item.setDone(true);
                }

            }
        });
        holder.trashImageView.setOnClickListener(v ->
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure to delete " + holder.questTextView.getText() + "?");
            builder.setPositiveButton("Yes", (dialog, which) -> questlogInterface.remove(list.remove(position)));
            builder.setNeutralButton("No", null);
            builder.create().show();
        });
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView questTextView;
        protected TextView questDateTextView;
        protected TextView questStatTextView;
        protected TextView questStatPointsTextView;
        protected ImageView trashImageView;
        protected CheckBox checkBox;
        //protected boolean checked;

        public ViewHolder(View itemView) {
            super(itemView);
            this.trashImageView = (ImageView) itemView.findViewById(R.id.trash_image_view);
            this.questTextView = (TextView) itemView.findViewById(R.id.questTextView);
            this.questDateTextView = (TextView) itemView.findViewById(R.id.questDateTextView);
            this.questStatTextView = (TextView) itemView.findViewById(R.id.questStatTextView);
            this.questStatPointsTextView = (TextView) itemView.findViewById(R.id.questStatPointsTextView);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.questCheckBox);
        }
    }
}
