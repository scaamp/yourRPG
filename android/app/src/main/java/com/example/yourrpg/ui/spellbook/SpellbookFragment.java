package com.example.yourrpg.ui.spellbook;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourrpg.MainActivity;
import com.example.yourrpg.activity.NewSpellActivity;
import com.example.yourrpg.R;
import com.example.yourrpg.adapter.HistoryRemover;
import com.example.yourrpg.databinding.FragmentSpellbookBinding;
import com.example.yourrpg.model.Spellbook;
import com.example.yourrpg.adapter.ViewHolderAdaptable;
import com.example.yourrpg.persistency.SharedPreferencesSaver;
import com.example.yourrpg.adapter.SpellbookAdapter;

import java.util.ArrayList;

public class SpellbookFragment extends Fragment implements HistoryRemover {

    public static final int NEW_SPELL = 222;
    private static ArrayList<Spellbook> spellList;

    private SpellbookViewModel dashboardViewModel;
    private FragmentSpellbookBinding binding;
    private Button goToAddSpell;
    private TextView nullSpellListTextView;
    private String spellText;

    private RecyclerView.LayoutManager historyLayoutManager;
    private ArrayList<ViewHolderAdaptable> allItems;
    private RecyclerView historyRecyclerView;
    private RecyclerView.Adapter spellbookAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(SpellbookViewModel.class);

        binding = FragmentSpellbookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        nullSpellListTextView = (TextView) root.findViewById(R.id.nullSpellListTextView);
        historyRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);

        goToAddSpell = (Button) root.findViewById(R.id.goToAddSpellButton);
        goToAddSpell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getParentFragment() != null;
                startActivityForResult(new Intent(getContext(), NewSpellActivity.class), NEW_SPELL);
            }
        });
        initSpellList();
        initRecyclerView();
        return root;
    }

    private void initSpellList() {
        ArrayList<Spellbook> newSpellList = SharedPreferencesSaver.loadSpellbookFrom(getActivity().getSharedPreferences("SPELLBOOK_PREF", MODE_PRIVATE));
        if (newSpellList.size() != 0) {
            spellList = newSpellList;
            nullSpellListTextView.setVisibility(View.INVISIBLE);
            //spellTextView.setText(spellList.get(0).getText());
        } else {
            spellList = new ArrayList<>();
            nullSpellListTextView.setVisibility(View.VISIBLE);
            nullSpellListTextView.setText("Your spellbook is empty... \nPlease add your first spell");
        }
    }

    private void initRecyclerView() {
        historyLayoutManager = new LinearLayoutManager(getContext());
        historyRecyclerView.setLayoutManager(historyLayoutManager);

        historyRecyclerView.setHasFixedSize(true);
        updateAllHistoryItems();
        spellbookAdapter = new SpellbookAdapter(allItems, this, getContext()); //TODO usuwanie
        historyRecyclerView.setAdapter(spellbookAdapter);
    }

    private ArrayList<ViewHolderAdaptable> updateAllHistoryItems() {
        allItems = new ArrayList<>();
        allItems.addAll(spellList);
        return allItems;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferencesSaver.saveSpellbookTo(spellList, getActivity().getSharedPreferences("SPELLBOOK_PREF", MODE_PRIVATE));
        //initSpellList();
        initRecyclerView();
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
                    Spellbook newSpell = (Spellbook) data.getExtras().get(NewSpellActivity.NEW_SPELL);
                    spellText = String.valueOf(newSpell.getText());
                    //spellTextView.setText(spellText);
                    spellList.add(newSpell);
                }
            }
        }
    }

    public static ArrayList<Spellbook> getSpellList() {
        return spellList;
    }

    @Override
    public void remove(ViewHolderAdaptable viewHolderAdaptable) {
        spellList.remove(viewHolderAdaptable);
        allItems.remove(viewHolderAdaptable);
        spellbookAdapter.notifyDataSetChanged();
        SharedPreferencesSaver.saveSpellbookTo(spellList, getActivity().getSharedPreferences("SPELLBOOK_PREF", MODE_PRIVATE));
    }
}