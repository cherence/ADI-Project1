package com.example.cher.cherproject1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
                    mListArrayAdapter.notifyDataSetChanged();
                    listEditText.getText().clear();
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
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                String newTitleString = (String)parent.getAdapter().getItem(position);
                intent.putExtra("newTitleString", newTitleString);
                startActivity(intent);
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
}
