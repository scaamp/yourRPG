package com.example.yourrpg.character;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.yourrpg.MainActivity;
import com.example.yourrpg.R;
import com.example.yourrpg.databinding.FragmentCharacterBinding;
import com.example.yourrpg.model.Character;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Angle;
import nl.dionsegijn.konfetti.core.PartyFactory;
import static nl.dionsegijn.konfetti.core.Position.Relative;
import nl.dionsegijn.konfetti.core.Spread;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.xml.KonfettiView;


public class CharacterFragment extends Fragment {

    private CharacterViewModel homeViewModel;
    private FragmentCharacterBinding binding;
    private TextView strengthPoints;
    private TextView agilityPoints;
    private TextView nickName;
    private TextView level;
    private TextView expTextView;
    private static CharacterFragment instance = null;
    private ProgressBar mProgress;
    private Handler handler = new Handler();
    private TextView progressTextView;
    private int exp = 0;
    private int progress;
    private double ratio = 0;
    private KonfettiView konfettiView = null;
    private Shape.DrawableShape drawableShape = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(CharacterViewModel.class);

        binding = FragmentCharacterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setHasOptionsMenu(true);

        nickName = (TextView) root.findViewById(R.id.yourNickTextView);
        level = (TextView) root.findViewById(R.id.yourLevelTextView);
        expTextView = (TextView) root.findViewById(R.id.yourExpTextView);
        strengthPoints = (TextView) root.findViewById(R.id.strengthPointsHome);
        agilityPoints = (TextView) root.findViewById(R.id.agilityPoints);
        progressTextView = (TextView) root.findViewById(R.id.txtProgress);
        instance = this;

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.custom_progressbar_drawable);
        mProgress = (ProgressBar) root.findViewById(R.id.progressBar);
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
        final Drawable drawableHeart = ContextCompat.getDrawable(getContext(), R.drawable.ic_heart);
        drawableShape = new Shape.DrawableShape(drawableHeart, true);
        konfettiView = root.findViewById(R.id.konfettiView);
        return root;
    }

    public void explode() {
        EmitterConfig emitterConfig = new Emitter(100L, TimeUnit.MILLISECONDS).max(100);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .spread(360)
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(0f, 30f)
                        .position(new Relative(0.5, 0.3))
                        .build()
        );
    }

    public void parade() {
        EmitterConfig emitterConfig = new Emitter(5, TimeUnit.SECONDS).perSecond(30);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .angle(Angle.RIGHT - 45)
                        .spread(Spread.SMALL)
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(10f, 30f)
                        .position(new Relative(0.0, 0.5))
                        .build(),
                new PartyFactory(emitterConfig)
                        .angle(Angle.LEFT + 45)
                        .spread(Spread.SMALL)
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(10f, 30f)
                        .position(new Relative(1.0, 0.5))
                        .build()
        );
    }

    public void rain() {
        EmitterConfig emitterConfig = new Emitter(5, TimeUnit.SECONDS).perSecond(100);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .angle(Angle.BOTTOM)
                        .spread(Spread.ROUND)
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(0f, 15f)
                        .position(new Relative(0.0, 0.0).between(new Relative(1.0, 0.0)))
                        .build()
        );
    }

    public void setCharacter()
    {
        MainActivity mainActivity = (MainActivity) getActivity();
        Character character = mainActivity.getCurrentCharacter();

        strengthPoints.setText(String.valueOf(character.getStrength()));
        agilityPoints.setText(String.valueOf(character.getAgility()));
        level.setText(String.valueOf(character.getLevel()));
        nickName.setText(String.valueOf(character.getName()));

        ratio = 100 * character.getLevel() * 0.34;
        exp = character.getStrength() * 10;
        expTextView.setText(String.valueOf(exp));
        character.setExp(exp);
        progress = (int) (exp%ratio);
        mProgress.setProgress(progress);
        if (exp >= ratio * character.getLevel() && exp != 0) { // level up
            parade();
            explode();
            rain();
            character.setLevel(character.getLevel()+1);
            level.setText(String.valueOf(character.getLevel()));
        }
        progressTextView.setText(String.valueOf(progress) + "%");
    }

    public void levelUp()
    {

    }

    @Override
    public void onResume() {
        super.onResume();
        setCharacter();
        //SharedPreferencesSaver.saveTo(mainActivity.getCharacterList(), getActivity().getSharedPreferences("CHARACTER_PREF", MODE_PRIVATE));
        //SharedPreferencesSaver.saveSpellbookTo(SpellbookFragment.getSpellList(), getActivity().getSharedPreferences("SPELLBOOK_PREF", MODE_PRIVATE));
    }


}