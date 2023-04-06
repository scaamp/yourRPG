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

import com.example.yourrpg.R;
import com.example.yourrpg.databinding.FragmentCharacterBinding;
import com.example.yourrpg.persistency.SharedPreferencesSaver;


public class CharacterFragment extends Fragment {

    private CharacterViewModel homeViewModel;
    private FragmentCharacterBinding binding;
    private TextView strengthPoints;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(CharacterViewModel.class);

        binding = FragmentCharacterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        strengthPoints = (TextView) root.findViewById(R.id.strengthPointsHome);

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
    }
}