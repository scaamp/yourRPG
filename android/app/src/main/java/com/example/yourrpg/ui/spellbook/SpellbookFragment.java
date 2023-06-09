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

import com.example.yourrpg.activity.NewCharacterActivity;
import com.example.yourrpg.model.Character;
import com.example.yourrpg.retrofit.RetrofitAPI;
import com.example.yourrpg.retrofit.RetrofitClient;
import com.example.yourrpg.activity.NewSpellActivity;
import com.example.yourrpg.R;
import com.example.yourrpg.spellbookAdapter.SpellbookRemover;
import com.example.yourrpg.spellbookAdapter.SpellbookViewHolderAdaptable;
import com.example.yourrpg.databinding.FragmentSpellbookBinding;
import com.example.yourrpg.model.Spellbook;
import com.example.yourrpg.persistency.SharedPreferencesSaver;
import com.example.yourrpg.spellbookAdapter.SpellbookAdapter;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpellbookFragment extends Fragment implements SpellbookRemover {

    public static final int NEW_SPELL = 222;
    public static final int SPELLS_COUNT = 2;
    private static ArrayList<Spellbook> spellList;
    private RetrofitClient retrofitClient;
    private SpellbookViewModel dashboardViewModel;
    private FragmentSpellbookBinding binding;
    private Button goToAddSpell;
    private TextView nullSpellListTextView;
    private RecyclerView.LayoutManager historyLayoutManager;
    private ArrayList<SpellbookViewHolderAdaptable> allItems;
    private RecyclerView historyRecyclerView;
    private RecyclerView.Adapter spellbookAdapter;
    private String spellText;
    private long spellsCount;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(SpellbookViewModel.class);

        binding = FragmentSpellbookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        nullSpellListTextView = (TextView) root.findViewById(R.id.nullSpellListTextView);
        historyRecyclerView = (RecyclerView) root.findViewById(R.id.spellbookRecyclerView);

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

        ArrayList newSpellList;
        //newSpellList.add(new Spellbook(1,"XD", "Jakub", "XD"));
        newSpellList = SharedPreferencesSaver.loadFrom(getActivity().getSharedPreferences("SPELLBOOK_PREF", MODE_PRIVATE), SharedPreferencesSaver.SPELLBOOK_PREF);
        if (newSpellList != null) {
            spellList = newSpellList;
            if (newSpellList.size() != 0) nullSpellListTextView.setVisibility(View.INVISIBLE);
            else
            {
                nullSpellListTextView.setVisibility(View.VISIBLE);
                nullSpellListTextView.setTextSize(32);
                nullSpellListTextView.setText("Your spellbook is empty... \nPlease add your first spell");
            }
        } else {
            spellList = new ArrayList<>();
        }
    }

    private void initRecyclerView() {
        historyLayoutManager = new LinearLayoutManager(getContext());
        historyRecyclerView.setLayoutManager(historyLayoutManager);

        historyRecyclerView.setHasFixedSize(true);
        updateAllHistoryItems();
        spellbookAdapter = new SpellbookAdapter(allItems, this, getContext());
        historyRecyclerView.setAdapter(spellbookAdapter);
    }

    private ArrayList<SpellbookViewHolderAdaptable> updateAllHistoryItems() {
        allItems = new ArrayList<>();
        allItems.addAll(spellList);
        return allItems;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferencesSaver.saveTo(spellList, getActivity().getSharedPreferences("SPELLBOOK_PREF", MODE_PRIVATE), SharedPreferencesSaver.SPELLBOOK_PREF);
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
                    spellList.add(newSpell);
                }
            }
        }
    }

    public static ArrayList<Spellbook> getSpellList() {
        return spellList;
    }

    @Override
    public void remove(SpellbookViewHolderAdaptable viewHolderAdaptable) {
        spellList.remove(viewHolderAdaptable);
        allItems.remove(viewHolderAdaptable);
        spellbookAdapter.notifyDataSetChanged();
        retrofitClient = new RetrofitClient(RetrofitAPI.SPELLBOOK_URL);
        Call<ResponseBody> deleteRequest = retrofitClient.getMyRetrofitAPI().deleteSpell(viewHolderAdaptable.getId());

        deleteRequest.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // use response.code, response.headers, etc.
                response.body();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // handle failure
                t.getMessage();
            }
        });
        SharedPreferencesSaver.saveTo(spellList, getActivity().getSharedPreferences("SPELLBOOK_PREF", MODE_PRIVATE), SharedPreferencesSaver.SPELLBOOK_PREF);
    }
}