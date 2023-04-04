package com.example.yourrpg.ui.dashboard;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
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

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private Button goToAddSpell;
    private ArrayList<Spellbook> spellList;
    private TextView spellTextView;
    public static final int NEW_SPELL = 222;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        spellTextView = (TextView) root.findViewById(R.id.spellTextView);
        goToAddSpell = (Button) root.findViewById(R.id.goToAddSpellButton);
        goToAddSpell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getParentFragment() != null;
                startActivityForResult(new Intent(getContext(), NewSpellbookActivity.class), NEW_SPELL);
            }
        });


        return root;
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
                    spellTextView.setText(String.valueOf(newSpell.getText()));
                    //spellList.add(newSpell);
                }
            }
        }
    }
}