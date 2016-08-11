package com.example.user.myapplication.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapplication.R;
import com.example.user.myapplication.model.PokemonType;
import com.example.user.myapplication.model.SearchPokemonInfo;
import com.parse.GetCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/8/11.
 */
public class PokemonSearchFragment extends Fragment {

    AlertDialog searchDialog;
    View fragmentView;
    TextView infoText;
    public ArrayList<String> typeList = null;
    ArrayList<SearchPokemonInfo> searchResult = new ArrayList<>();
    ListView listView;

    public static PokemonSearchFragment newInstance() {

        Bundle args = new Bundle();
        PokemonSearchFragment fragment = new PokemonSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setMenuVisibility(false);

        PokemonType.getQuery().getFirstInBackground(new GetCallback<PokemonType>() {
            @Override
            public void done(PokemonType object, ParseException e) {
                if (e == null) {
                    typeList = object.getTypeArray();
                    if (typeList != null) {
                        setMenuVisibility(true);
                    } else {
                        setMenuVisibility(false);
                        Toast.makeText(getActivity(), "沒抓到屬性列表,確保網路是開啟的", Toast.LENGTH_LONG).show();
                    }
                } else { //error happened
                    setMenuVisibility(false);
                    Toast.makeText(getActivity(), "沒抓到屬性列表,確保網路是開啟的", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void hideOrShowInfoText(ArrayList<SearchPokemonInfo> result) {
        if(infoText != null) {
            if(result.size() == 0) {
                infoText.setVisibility(View.VISIBLE);
            }
            else {
                infoText.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_search, container, false);
            listView = (ListView)fragmentView.findViewById(R.id.listView2);
            //TODO: set adapter
            infoText = (TextView)fragmentView.findViewById(R.id.infoText);
        }

        hideOrShowInfoText(searchResult);

        if(searchDialog == null) {

        }

        return fragmentView;
    }
}
