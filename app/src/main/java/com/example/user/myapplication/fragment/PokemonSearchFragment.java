package com.example.user.myapplication.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapplication.R;
import com.example.user.myapplication.adapter.PokemonSearchListViewAdapter;
import com.example.user.myapplication.model.PokemonType;
import com.example.user.myapplication.model.SearchPokemonInfo;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/8/11.
 */
public class PokemonSearchFragment extends Fragment implements DialogInterface.OnClickListener{

    AlertDialog searchDialog;
    DialogViewHolder dialogViewHolder = null;
    View fragmentView;
    TextView infoText;
    public ArrayList<String> typeList = null;
    ArrayList<SearchPokemonInfo> searchResult = new ArrayList<>();
    ListView listView;
    PokemonSearchListViewAdapter adapter;

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
        adapter = new PokemonSearchListViewAdapter(getActivity(),
                R.layout.search_row_view,
                searchResult,
                this);
        PokemonType.getQuery().getFirstInBackground(new GetCallback<PokemonType>() {
            @Override
            public void done(PokemonType object, ParseException e) {
                if (e == null) {
                    typeList = object.getTypeArray();
                    if (typeList != null) {
                        setMenuVisibility(true);
                        //this callback is called after onCreateView
                        if (dialogViewHolder != null) {
                            dialogViewHolder.setTypeList(0, typeList);
                            dialogViewHolder.setTypeList(1, typeList);
                        }
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
            listView.setAdapter(adapter);
            infoText = (TextView)fragmentView.findViewById(R.id.infoText);
        }

        hideOrShowInfoText(searchResult);

        if(searchDialog == null) {
            View dialogView = inflater.inflate(R.layout.search_form, null);
            dialogViewHolder = new DialogViewHolder(dialogView);

            //PokemonType callback is called before onCreateView
            if(typeList != null) {
                dialogViewHolder.setTypeList(0, typeList);
                dialogViewHolder.setTypeList(1, typeList);
            }
            searchDialog = new AlertDialog.Builder(getActivity()).setView(dialogView)
                                        .setNegativeButton("取消", this)
                                        .setPositiveButton("搜尋", this)
                                        .create();
        }

        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_search) {
            searchDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if(which == AlertDialog.BUTTON_POSITIVE) { //positive button was pressed
            startSearching();
        }
        else if(which == AlertDialog.BUTTON_NEGATIVE) {

        }

    }

    private void startSearching() {
        ParseQuery<SearchPokemonInfo> query = SearchPokemonInfo.getQuery();
        CustomizedFindCallback findCallback = new CustomizedFindCallback(searchResult,
                                                                        this);
        if(dialogViewHolder.conditionIsUsed(0)) {
            query = query.whereContains(SearchPokemonInfo.nameKey,
                    dialogViewHolder.getInputName());
        }

        if(dialogViewHolder.conditionIsUsed(1)) {
            if(dialogViewHolder.constrainedByLeftInterval()) {
                query = query.whereGreaterThan(SearchPokemonInfo.hpKey, dialogViewHolder.getLeftIntervalVal());
            }

            if(dialogViewHolder.constrainedByRightInterval()) {
                query = query.whereLessThan(SearchPokemonInfo.hpKey, dialogViewHolder.getRightIntervalVal());
            }
            query = query.addAscendingOrder(SearchPokemonInfo.hpKey);
        }

        if(dialogViewHolder.conditionIsUsed(2)) {
            ArrayList<Integer> typesCondition = new ArrayList<>();
            for(int i = 0;i < 2;i++) {
                int selectType = dialogViewHolder.getSelectType(i);
                if(selectType != -1) {
                    typesCondition.add(selectType);
                }

            }

            findCallback.numTypesInCondition = typesCondition.size();
            query = query.whereContainsAll(SearchPokemonInfo.typesKey, typesCondition);
        }

        query.findInBackground(findCallback);
    }

    private static class CustomizedFindCallback implements FindCallback<SearchPokemonInfo> {

        public int numTypesInCondition = -1;
        private ArrayList<SearchPokemonInfo> searchResult;
        private PokemonSearchFragment searchFragment;

        CustomizedFindCallback(ArrayList<SearchPokemonInfo> resultBuffer,
                               PokemonSearchFragment fragment) {

            searchResult = resultBuffer;
            searchFragment = fragment;

        }

        @Override
        public void done(List<SearchPokemonInfo> objects, ParseException e) {
            searchResult.clear();
            if(numTypesInCondition != -1) {
                for(SearchPokemonInfo searchPokemonInfo : objects) {
                    ArrayList<Integer> typeIndices = searchPokemonInfo.getTypeIndices();
                    if(typeIndices.size() == numTypesInCondition) {
                        searchResult.add(searchPokemonInfo);
                    }
                }
            }
            else {
                searchResult.addAll(objects);
            }

            searchFragment.hideOrShowInfoText(searchResult);
            searchFragment.adapter.notifyDataSetChanged();
        }
    }




    public static class DialogViewHolder {

        View dialogView;

        CheckBox[] conditionBoxes = new CheckBox[3];
        CheckBox leftIntervalBox;
        CheckBox rightIntervalBox;

        EditText nameText;
        EditText leftIntervalText;
        EditText rightIntervalText;
        Spinner[] typeSelectors = new Spinner[2];

        DialogViewHolder(View dialogView) {

            this.dialogView = dialogView;
            conditionBoxes[0] = (CheckBox)dialogView.findViewById(R.id.conditionBox1);
            conditionBoxes[1] = (CheckBox)dialogView.findViewById(R.id.conditionBox2);
            conditionBoxes[2] = (CheckBox)dialogView.findViewById(R.id.conditionBox3);
            leftIntervalBox = (CheckBox)dialogView.findViewById(R.id.leftIntervalConditionBox);
            rightIntervalBox = (CheckBox)dialogView.findViewById(R.id.rightIntervalConditionBox);

            nameText = (EditText)dialogView.findViewById(R.id.nameText);
            leftIntervalText = (EditText)dialogView.findViewById(R.id.leftInterval);
            rightIntervalText = (EditText)dialogView.findViewById(R.id.rightInterval);

            typeSelectors[0] = (Spinner)dialogView.findViewById(R.id.type1Selector);
            typeSelectors[1] = (Spinner)dialogView.findViewById(R.id.type2Selector);

        }


        public String getInputName() {
            return nameText.getText().toString();
        }

        public boolean constrainedByLeftInterval() {
            return leftIntervalBox.isChecked();
        }

        public boolean constrainedByRightInterval() {
            return rightIntervalBox.isChecked();
        }

        public float getLeftIntervalVal() {
            return Float.valueOf(leftIntervalText.getText().toString());
        }

        public float getRightIntervalVal() {
            return Float.valueOf(rightIntervalText.getText().toString());
        }

        public boolean conditionIsUsed(int index) {
            if(index < 3) {
                return conditionBoxes[index].isChecked();
            }
            else {
                return false;
            }
        }

        public int getSelectType(int typeIndex) {
            int selectPos = typeSelectors[typeIndex].getSelectedItemPosition();
            if(selectPos == 0) {
                return -1;
            }
            else {
                return selectPos - 1;
            }
        }

        public void setTypeList(int typeIndex, ArrayList<String> typeList) {
            if(!typeList.contains("none")) {
                typeList.add(0, "none");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(dialogView.getContext(), android.R.layout.simple_spinner_item, typeList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSelectors[typeIndex].setAdapter(adapter);

        }

    }

}
