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
    public static final String DATA_KEY = "myDataKey";
    public static final int MAIN_REQUEST_CODE = 22;
    ArrayList<String> mDummyListArrayList;
    ArrayList<ArrayList<String>> mMasterDataArrayList;
    public static final String DATA_INDEX_KEY = "mydataIndexKey";
    public static final int ERROR_INDEX = -7;
    private static int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appTitle = (TextView) findViewById(R.id.appTitle_id);
        listEditText = (EditText) findViewById(R.id.list_editText_id);
        listListView = (ListView) findViewById(R.id.list_listView_id);

        mListArrayList = new ArrayList<>();
        mDummyListArrayList = new ArrayList<>();

        mMasterDataArrayList = new ArrayList<>();


        mListArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListArrayList);

        listListView.setAdapter(mListArrayAdapter);

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

        listListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mListArrayList.remove(position);
                mListArrayAdapter.notifyDataSetChanged();
                return true;
            }
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAIN_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                if (data != null){
                    mDummyListArrayList = data.getStringArrayListExtra(DATA_KEY);
                    mMasterDataArrayList.set(currentPosition, mDummyListArrayList);
//                    int index = data.getIntExtra(DATA_INDEX_KEY, ERROR_INDEX);
//                    if (index != ERROR_INDEX){

                    }
                }
            } else if (requestCode == RESULT_CANCELED){
                Log.w("Main", "Failed ot get new list back");
            }
        }
    }

