package com.example.yourrpg.ui.character;

import static android.content.Context.MODE_PRIVATE;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.yourrpg.MainActivity;
import com.example.yourrpg.R;
import com.example.yourrpg.databinding.FragmentCharacterBinding;
import com.example.yourrpg.model.Character;


public class CharacterFragment extends Fragment {

    private CharacterViewModel homeViewModel;
    private FragmentCharacterBinding binding;
    private TextView strengthPoints;
    private TextView agilityPoints;
    private static CharacterFragment instance = null;
    private ProgressBar mProgress;
    private Handler handler = new Handler();
    private TextView progressTextView;
    private int pStatus = 0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(CharacterViewModel.class);

        binding = FragmentCharacterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        strengthPoints = (TextView) root.findViewById(R.id.strengthPointsHome);
        agilityPoints = (TextView) root.findViewById(R.id.agilityPoints);
        instance = this;
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.custom_progressbar_drawable);
        mProgress = (ProgressBar) root.findViewById(R.id.progressBar);
        mProgress.setProgress(80);   // Main Progress
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
        progressTextView = (TextView) root.findViewById(R.id.txtProgress);

//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                while (pStatus < 100) {
//                    pStatus += 1;
//
//                    handler.post(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // TODO Auto-generated method stub
//                            mProgress.setProgress(pStatus);
//                            progressTextView.setText(pStatus + "%");
//
//                        }
//                    });
//                    try {
//                        // Sleep for 200 milliseconds.
//                        // Just to display the progress slowly
//                        Thread.sleep(200); //thread will take approx 1.5 seconds to finish
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
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
        pStatus = character.getStrength()*10;
        mProgress.setProgress(pStatus);
        progressTextView.setText(pStatus + "%");
        //SharedPreferencesSaver.saveTo(mainActivity.getCharacterList(), getActivity().getSharedPreferences("CHARACTER_PREF", MODE_PRIVATE));
        //SharedPreferencesSaver.saveSpellbookTo(SpellbookFragment.getSpellList(), getActivity().getSharedPreferences("SPELLBOOK_PREF", MODE_PRIVATE));
    }


}