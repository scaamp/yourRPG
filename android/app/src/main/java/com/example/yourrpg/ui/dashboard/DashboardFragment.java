package com.example.yourrpg.ui.dashboard;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.yourrpg.activity.NewSpellbookActivity;
import com.example.yourrpg.R;
import com.example.yourrpg.databinding.FragmentDashboardBinding;
import com.example.yourrpg.model.Character;
import com.example.yourrpg.model.Spellbook;
import com.example.yourrpg.persistency.SharedPreferencesSaver;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private static ArrayList<Spellbook> spellList;
    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private Button goToAddSpell;
    //public ArrayList<Spellbook> spellList;
    private TextView spellTextView;
    private String spellText;
    public static final int NEW_SPELL = 222;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //spellList = new ArrayList<>();
        spellTextView = (TextView) root.findViewById(R.id.spellTextView);
        //
        goToAddSpell = (Button) root.findViewById(R.id.goToAddSpellButton);
        goToAddSpell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getParentFragment() != null;
                startActivityForResult(new Intent(getContext(), NewSpellbookActivity.class), NEW_SPELL);
            }
        });
        initSpellList();
        return root;
    }


    private void initSpellList() {
        ArrayList<Spellbook> newSpellList = SharedPreferencesSaver.loadSpellbookFrom(getActivity().getSharedPreferences("SPELLBOOK_PREF", MODE_PRIVATE));
        if (newSpellList != null) {
            spellList = newSpellList;
            spellTextView.setText(spellList.get(0).getText());
        } else {
            spellList = new ArrayList<>();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferencesSaver.saveSpellbookTo(spellList, getActivity().getSharedPreferences("SPELLBOOK_PREF", MODE_PRIVATE));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == NEW_SPELL) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Spellbook newSpell = (Spellbook) data.getExtras().get(NewSpellbookActivity.NEW_SPELL);
                    spellText = String.valueOf(newSpell.getText());
                    spellTextView.setText(spellText);
                    spellList.add(newSpell);
                }
            }
        }
    }

    public static ArrayList<Spellbook> getSpellList() {
        return spellList;
    }

    public void setSpellList(ArrayList<Spellbook> spellList) {
        this.spellList = spellList;
    }
}