package com.example.yourrpg.ui.oracle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.yourrpg.R;
import com.example.yourrpg.databinding.FragmentOracleBinding;
import com.example.yourrpg.model.MessageModal;
import com.example.yourrpg.retrofit.RetrofitAPI;
import com.example.yourrpg.retrofit.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class OracleFragment extends Fragment {

    private OracleViewModel homeViewModel;
    private FragmentOracleBinding binding;
    private RetrofitClient retrofitClient;
    private RecyclerView chatsRV;
    private ImageButton sendMsgIB;
    private EditText userMsgEdt;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";

    // creating a variable for
    // our volley request queue.
    private RequestQueue mRequestQueue;

    // creating a variable for array list and adapter class.
    private ArrayList<MessageModal> messageModalArrayList;
    private MessageRVAdapter messageRVAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(OracleViewModel.class);

        binding = FragmentOracleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        chatsRV = root.findViewById(R.id.idRVChats);
        sendMsgIB = root.findViewById(R.id.idIBSend);
        userMsgEdt = root.findViewById(R.id.idEdtMessage);

        // below line is to initialize our request queue.
//        mRequestQueue = Volley.newRequestQueue(getContext());
//        mRequestQueue.getCache().clear();

        // creating a new array list
        messageModalArrayList = new ArrayList<>();

        // adding on click listener for send message button.
        sendMsgIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking if the message entered
                // by user is empty or not.
                if (userMsgEdt.getText().toString().isEmpty()) {
                    // if the edit text is empty display a toast message.
                    Toast.makeText(getActivity(), "Please enter your message..", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getActivity(), "Please wait a moment...", Toast.LENGTH_SHORT).show();
                // calling a method to send message
                // to our bot to get response.
                sendMessage(userMsgEdt.getText().toString());

                // below line we are setting text in our edit text as empty
                userMsgEdt.setText("");
            }
        });

        messageRVAdapter = new MessageRVAdapter(messageModalArrayList, getContext());

        // below line we are creating a variable for our linear layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        // below line is to set layout
        // manager to our recycler view.
        chatsRV.setLayoutManager(linearLayoutManager);

        // below line we are setting
        // adapter to our recycler view.
        chatsRV.setAdapter(messageRVAdapter);
        //return root;
        return root;
    }

    private void sendMessage(String userMsg) {
        // below line is to pass message to our
        // array list which is entered by the user.
        messageModalArrayList.add(new MessageModal(userMsg, USER_KEY));
        messageRVAdapter.notifyDataSetChanged();
        retrofitClient = new RetrofitClient(RetrofitAPI.CHARACTER_URL + "oracle/");
        Call<String> call = retrofitClient.getMyRetrofitAPI().getAnswerFromOracle(userMsg);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String oracleResponse = response.body();
                String newResponsePL = oracleResponse.replace("sztuczna inteligencja", "wyrocznia");
                String newResponse = newResponsePL.replace("AI language model", "oracle");
                String newResponsePL1 = newResponse.replace("AI", "wyrocznia");
                messageModalArrayList.add(new MessageModal(newResponsePL1, BOT_KEY));
                messageRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                messageModalArrayList.add(new MessageModal("Sorry no response found", BOT_KEY));
                Toast.makeText(getContext(), "No response from the bot..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}