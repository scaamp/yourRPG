package com.example.yourrpg.ui.questlog;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourrpg.MainActivity;
import com.example.yourrpg.R;
import com.example.yourrpg.activity.NewQuestActivity;
import com.example.yourrpg.databinding.FragmentQuestlogBinding;
import com.example.yourrpg.model.Character;
import com.example.yourrpg.model.Questlog;
import com.example.yourrpg.persistency.SharedPreferencesSaver;
import com.example.yourrpg.questlogAdapter.QuestlogAdapter;
import com.example.yourrpg.questlogAdapter.QuestlogRemover;
import com.example.yourrpg.questlogAdapter.QuestlogViewHolderAdaptable;

import java.util.ArrayList;


public class QuestlogFragment extends Fragment implements QuestlogRemover {

    private QuestlogViewModel notificationsViewModel;
    private FragmentQuestlogBinding binding;
    private CheckBox questCheckBox;
    private Button goToAddQuestButton;
    public static final int NEW_QUEST = 333;
    private static ArrayList<Questlog> questList;

    private RecyclerView.LayoutManager historyLayoutManager;
    private ArrayList<QuestlogViewHolderAdaptable> allItems;
    private RecyclerView historyRecyclerView;
    private RecyclerView.Adapter questAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(QuestlogViewModel.class);
        binding = FragmentQuestlogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        MainActivity mainActivity = (MainActivity) getActivity();
        Character character = mainActivity.getCurrentCharacter();
        //questCheckBox = (CheckBox) root.findViewById(R.id.checkBox);
        goToAddQuestButton = (Button) root.findViewById(R.id.goToAddQuestButton);
        historyRecyclerView = (RecyclerView) root.findViewById(R.id.questRecyclerView);
//        questCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (!questCheckBox.isChecked()) {
//                    character.setStrength(character.getStrength() - 1);
//                }
//                if (questCheckBox.isChecked()) {
//                    character.setStrength(character.getStrength() + 1);
//                }
//            }
//        });
        goToAddQuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getParentFragment() != null;
                startActivityForResult(new Intent(getContext(), NewQuestActivity.class), NEW_QUEST);
            }
        });
        initQuestList();
        initRecyclerView();
        return root;
    }

    private void initQuestList() {
        ArrayList<Questlog> newQuestList = new ArrayList<>();
        //newQuestList.add(new Questlog(1,"XD",false));
        newQuestList = SharedPreferencesSaver.loadQuestlogFrom(getActivity().getSharedPreferences("QUESTLOG_PREF", MODE_PRIVATE));

        if (newQuestList.size() != 0) {
            questList = newQuestList;
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
        SharedPreferencesSaver.saveQuestlogTo(questList, getActivity().getSharedPreferences("QUESTLOG_PREF", MODE_PRIVATE));
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
        SharedPreferencesSaver.saveQuestlogTo(questList, getActivity().getSharedPreferences("QUESTLOG_PREF", MODE_PRIVATE));
    }
}