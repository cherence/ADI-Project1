package com.example.cher.cherproject1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView appTitle;
    EditText listEditText;
    ListView listListView;
    ArrayList<String> mListArrayList;
    ArrayAdapter<String> mListArrayAdapter;
    ArrayList<String> mDummyListArrayList;
    ArrayList<ArrayList<String>> mMasterDataArrayList;
    protected static final String DATA_INDEX_KEY = "mydataIndexKey";
    protected static int currentPosition;
    protected static final String DATA_KEY = "myDataKey";
    protected static final int MAIN_REQUEST_CODE = 22;
    protected static final int ERROR_INDEX = -7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeArrayLists();
        initializeAndSetArrayAdapter();
        createAndSetFAB();
        createAndSetOnItemLongClickListener();
        createAndSetOnItemClickListener();
    }


    private void initializeViews(){
        appTitle = (TextView) findViewById(R.id.appTitle_id);
        listEditText = (EditText) findViewById(R.id.list_editText_id);
        listListView = (ListView) findViewById(R.id.list_listView_id);
    }

    private void initializeArrayLists(){
        mListArrayList = new ArrayList<>();
        mDummyListArrayList = new ArrayList<>();
        mMasterDataArrayList = new ArrayList<>();
    }

    private void initializeAndSetArrayAdapter(){
        mListArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListArrayList);
        listListView.setAdapter(mListArrayAdapter);
    }

    private void createAndSetFAB(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userInputLists = listEditText.getText().toString();

                if (userInputLists.isEmpty()){
                    Snackbar.make(view, "Please enter a new to do list", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else{
                    mListArrayList.add(userInputLists);
                    ArrayList<String> tempList = new ArrayList<String>();
                    mMasterDataArrayList.add(tempList);
                    mListArrayAdapter.notifyDataSetChanged();
                    listEditText.getText().clear();
                    Log.d("MainActivity", "FAB adds stuff");
                }
            }
        });
    }

    private void createAndSetOnItemLongClickListener(){
        listListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mListArrayList.remove(position);
                mMasterDataArrayList.remove(position);
                mListArrayAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void createAndSetOnItemClickListener(){
        listListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                String newTitleString = (String)parent.getAdapter().getItem(currentPosition);
                intent.putExtra("newTitleString", newTitleString);
                intent. putExtra(DATA_INDEX_KEY, currentPosition);
                intent.putExtra(DATA_KEY, mMasterDataArrayList.get(currentPosition));
                startActivityForResult(intent, MAIN_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAIN_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                if (data != null){
                    mDummyListArrayList = data.getStringArrayListExtra(DATA_KEY);
                    mMasterDataArrayList.set(currentPosition, mDummyListArrayList);
                    }
                }
            } else if (requestCode == RESULT_CANCELED){
                Log.w("Main", "Failed ot get new list back");
            }
        }
    }

