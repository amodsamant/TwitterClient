package com.twitterclient.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.twitterclient.R;
import com.twitterclient.adapters.DraftsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DraftsFragment extends DialogFragment {

    public interface DraftsFragmentListener {
        void onFinishDraft(String tweet);
    }


    ListView lvDrafts;
    DraftsAdapter adapter;
    List<String> drafts;

    public DraftsFragment() {
    }

    public static DraftsFragment getInstance() {
        DraftsFragment draftsFragment = new DraftsFragment();
        Bundle args = new Bundle();
        draftsFragment.setArguments(args);
        return draftsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.drafts_frag, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drafts = getDrafts();
        lvDrafts = (ListView) view.findViewById(R.id.lvDrafts);
        adapter = new DraftsAdapter(getContext(), drafts);

        lvDrafts.setAdapter(adapter);

        lvDrafts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SharedPreferences sharedPreferences = getActivity()
                        .getSharedPreferences("Drafts", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(adapter.getItem(i));
                editor.apply();

                sendBackResult(adapter.getItem(i));
            }
        });

    }

    private List<String> getDrafts() {

       SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences("Drafts", Context.MODE_PRIVATE);

        Map<String,String> draftsMap = (Map<String, String>) sharedPreferences.getAll();
        List<String> drafts = new ArrayList<>(draftsMap.values());
        if(drafts!=null && !drafts.isEmpty()) {
            Log.d("DEBUG", drafts.get(0));
            Toast.makeText(getContext(),drafts.get(0),Toast.LENGTH_LONG).show();
        }

        return drafts;
    }
    @Override
    public void onResume() {

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    public void sendBackResult(String tweetDraft) {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        DraftsFragmentListener listener = (DraftsFragmentListener) getTargetFragment();
        listener.onFinishDraft(tweetDraft);
        dismiss();
    }


}
