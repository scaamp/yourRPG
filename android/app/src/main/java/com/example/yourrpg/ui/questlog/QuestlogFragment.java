package com.example.yourrpg.ui.questlog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.yourrpg.MainActivity;
import com.example.yourrpg.R;
import com.example.yourrpg.activity.NewCharacterActivity;
import com.example.yourrpg.activity.NewQuestActivity;
import com.example.yourrpg.activity.NewSpellActivity;
import com.example.yourrpg.databinding.FragmentQuestlogBinding;
import com.example.yourrpg.model.Character;
import com.example.yourrpg.persistency.SharedPreferencesSaver;
import com.example.yourrpg.ui.spellbook.SpellbookFragment;

public class QuestlogFragment extends Fragment {

    private QuestlogViewModel notificationsViewModel;
    private FragmentQuestlogBinding binding;
    private CheckBox questCheckBox;
    private TextView questTextView;
    private Button goToAddQuestButton;
    public static final int NEW_QUEST = 333;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(QuestlogViewModel.class);
        binding = FragmentQuestlogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        MainActivity mainActivity = (MainActivity) getActivity();
        Character character = mainActivity.getCurrentCharacter();
        questCheckBox = (CheckBox) root.findViewById(R.id.checkBox);
        questTextView = (TextView) root.findViewById(R.id.questTextView);
        goToAddQuestButton = (Button) root.findViewById(R.id.goToAddQuestButton);
        questTextView.setText(character.getName() + "\n Strength: " + character.getStrength());
        questCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (!questCheckBox.isChecked())
                {
                    character.setStrength(character.getStrength()-1);
                }
                if (questCheckBox.isChecked()) {
                    character.setStrength(character.getStrength() + 1);
                }
                questTextView.setText(character.getName() + "\n Strength: " + character.getStrength());
            }
        });
        goToAddQuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getParentFragment() != null;
                startActivityForResult(new Intent(getContext(), NewQuestActivity.class), NEW_QUEST);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}