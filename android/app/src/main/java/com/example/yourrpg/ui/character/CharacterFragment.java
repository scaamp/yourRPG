package com.example.yourrpg.ui.character;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.yourrpg.MainActivity;
import com.example.yourrpg.R;
import com.example.yourrpg.databinding.FragmentCharacterBinding;
import com.example.yourrpg.model.Character;
import com.example.yourrpg.persistency.SharedPreferencesSaver;
import com.example.yourrpg.ui.spellbook.SpellbookFragment;


public class CharacterFragment extends Fragment {

    private CharacterViewModel homeViewModel;
    private FragmentCharacterBinding binding;
    private TextView strengthPoints;
    private TextView agilityPoints;
    private static CharacterFragment instance = null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(CharacterViewModel.class);

        binding = FragmentCharacterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        strengthPoints = (TextView) root.findViewById(R.id.strengthPointsHome);
        agilityPoints = (TextView) root.findViewById(R.id.agilityPoints);
        instance = this;
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        Character character = mainActivity.getCurrentCharacter();
        strengthPoints.setText(String.valueOf(character.getStrength()));
        agilityPoints.setText(String.valueOf(character.getAgility()));

        SharedPreferencesSaver.saveTo(mainActivity.getCharacterList(), getActivity().getSharedPreferences("CHARACTER_PREF", MODE_PRIVATE));
        SharedPreferencesSaver.saveSpellbookTo(SpellbookFragment.getSpellList(), getActivity().getSharedPreferences("SPELLBOOK_PREF", MODE_PRIVATE));
    }
}