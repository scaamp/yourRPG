package com.example.yourrpg.questlog;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourrpg.MainActivity;
import com.example.yourrpg.R;
import com.example.yourrpg.databinding.FragmentQuestlogBinding;
import com.example.yourrpg.model.Character;
import com.example.yourrpg.model.Questlog;
import com.example.yourrpg.persistency.SharedPreferencesSaver;
import com.example.yourrpg.questlogAdapter.QuestlogAdapter;
import com.example.yourrpg.questlogAdapter.QuestlogInterface;
import com.example.yourrpg.questlogAdapter.QuestlogViewHolderAdaptable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class QuestlogFragment extends Fragment implements QuestlogInterface {

    private QuestlogViewModel notificationsViewModel;
    private FragmentQuestlogBinding binding;
    private Button goToAddQuestButton;
    private static ArrayList<Questlog> questList;
    private RecyclerView.LayoutManager historyLayoutManager;
    private ArrayList<QuestlogViewHolderAdaptable> allItems;
    private RecyclerView historyRecyclerView;
    private RecyclerView.Adapter questAdapter;
    public static final int NEW_QUEST = 333;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(QuestlogViewModel.class);
        binding = FragmentQuestlogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        goToAddQuestButton = (Button) root.findViewById(R.id.goToAddQuestButton);
        historyRecyclerView = (RecyclerView) root.findViewById(R.id.questRecyclerView);
        goToAddQuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getParentFragment() != null;
                startActivityForResult(new Intent(getContext(), NewQuestActivity.class), NEW_QUEST);
            }
        });
        initQuestList();
        initRecyclerView();
        checkIfDeadlineGone();
        return root;
    }

    private void checkIfDeadlineGone()
    {
        Date date = new Date();
        for (int i=0; i<questList.size(); i++) {
            if (date.after(questList.get(i).getDate()) && !questList.get(i).isDone()) {
                showNotification("Task not completed", "You didn't complete your task: " + questList.get(i).getDesc());
            }
        }
    }

    private void initQuestList() {
        ArrayList<Questlog> newQuestList;
        //newQuestList.add(new Questlog(1,"XD",false));
        newQuestList = SharedPreferencesSaver.loadFrom(getActivity().getSharedPreferences("QUESTLOG_PREF", MODE_PRIVATE), SharedPreferencesSaver.QUESTLOG_PREF);

        if (newQuestList != null) {
            questList = newQuestList;
            Bundle bundle = new Bundle();
            bundle.putSerializable("questListBundle", questList);
            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.putExtra("questListIntent", questList);
            //questList.clear();
//            nullSpellListTextView.setVisibility(View.INVISIBLE);
            //questTextView.setText(questList.get(0).getText());
        } else {
            questList = new ArrayList<>();
//            nullSpellListTextView.setVisibility(View.VISIBLE);
//            nullSpellListTextView.setTextSize(32);
//            nullSpellListTextView.setText("Your quest is empty... \nPlease add your first quest");
        }
    }

    private void initRecyclerView() {
        historyLayoutManager = new LinearLayoutManager(getContext());
        historyRecyclerView.setLayoutManager(historyLayoutManager);

        historyRecyclerView.setHasFixedSize(true);
        updateAllHistoryItems();
        questAdapter = new QuestlogAdapter(allItems, this, getContext());
        historyRecyclerView.setAdapter(questAdapter);
    }

    private ArrayList<QuestlogViewHolderAdaptable> updateAllHistoryItems() {
        allItems = new ArrayList<>();
        allItems.addAll(questList);
        return allItems;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferencesSaver.saveTo(questList, getActivity().getSharedPreferences("QUESTLOG_PREF", MODE_PRIVATE), SharedPreferencesSaver.QUESTLOG_PREF);
        initQuestList();
        initRecyclerView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == NEW_QUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Questlog newQuest = (Questlog) data.getExtras().get(NewQuestActivity.NEW_QUEST);

                    questList.add(newQuest);
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void remove(QuestlogViewHolderAdaptable viewHolderAdaptable) {
        questList.remove(viewHolderAdaptable);
        allItems.remove(viewHolderAdaptable);
        questAdapter.notifyDataSetChanged();

//        retrofitClient = new RetrofitClient(RetrofitAPI.QUESTLOG_URL);
//        Call<ResponseBody> deleteRequest = retrofitClient.getMyRetrofitAPI().deleteSpell(2803);
//        deleteRequest.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                // use response.code, response.headers, etc.
//                response.body();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                // handle failure
//                t.getMessage();
//            }
//        });
        SharedPreferencesSaver.saveTo(questList, getActivity().getSharedPreferences("QUESTLOG_PREF", MODE_PRIVATE), SharedPreferencesSaver.QUESTLOG_PREF);
    }

    @Override
    public void questIsDone(boolean checked, int position, String stat, int statPoints) {
        MainActivity mainActivity = (MainActivity) getActivity();
        Character character = mainActivity.getCurrentCharacter();
        //Character character = CharacterFragment.getInstance().getCurrentCharacter();
        if (checked) {
            questList.get(position).setDone(true);

            if (stat.equals("Strength")) character.setStrength(character.getStrength() + statPoints);
            if (stat.equals("Agility")) character.setAgility(character.getAgility() + statPoints);

            Intent intent = new Intent(getContext(), ReminderQuestBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);

//            Intent intent = new Intent(getContext(), MainActivity.class);
//            intent.putExtra(QUEST_DONE, character);
//            getActivity().setResult(Activity.RESULT_OK, intent);
//            startActivityForResult(intent, 444);
        }
        if (!checked) {
            if (character.getStrength() - statPoints < 0) character.setStrength(0);
            else character.setStrength(character.getStrength() - statPoints);
            questList.get(position).setDone(false);
        }
        SharedPreferencesSaver.saveTo(questList, getActivity().getSharedPreferences("QUESTLOG_PREF", MODE_PRIVATE), SharedPreferencesSaver.QUESTLOG_PREF);
        SharedPreferencesSaver.saveTo(mainActivity.getCharacterList(), getActivity().getSharedPreferences("CHARACTER_PREF", MODE_PRIVATE), SharedPreferencesSaver.CHARACTER_PREF);
    }

    void showNotification(String title, String message) {
        int id = (int) System.currentTimeMillis();

        NotificationManager mNotificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DESCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), "YOUR_CHANNEL_ID")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(message)// message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(getContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(id, mBuilder.build());
    }
}