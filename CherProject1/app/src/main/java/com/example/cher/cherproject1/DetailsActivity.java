package com.example.cher.cherproject1;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    TextView titleListNameTextView;
    EditText itemsEditText;
    ListView itemsListView;
    ArrayList<String> mItemArrayList;
    ArrayAdapter<String> mItemArrayAdapter;
    Boolean strike = true;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleListNameTextView = (TextView) findViewById(R.id.titleListNameTextView_id);
        itemsEditText = (EditText) findViewById(R.id.items_editText_id);
        itemsListView = (ListView) findViewById(R.id.items_listView_id);

        mItemArrayList = getDataList();
        index = getDataIndex();

        String extra = getIntent().getStringExtra("newTitleString");
        titleListNameTextView.setText(extra);

        mItemArrayList = new ArrayList<>();
        mItemArrayList = getIntent().getStringArrayListExtra(MainActivity.DATA_KEY);

        mItemArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mItemArrayList);
        itemsListView.setAdapter(mItemArrayAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userInputItems = itemsEditText.getText().toString();

                if (userInputItems.isEmpty()){
                    Snackbar.make(view, "Please enter an item", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else{
                    mItemArrayList.add(userInputItems);
                    mItemArrayAdapter.notifyDataSetChanged();
                    itemsEditText.getText().clear();
                }
            }
        });

        itemsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mItemArrayList.remove(position);
                mItemArrayAdapter.notifyDataSetChanged();
                return true;
            }
        });

        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView itemStrikeTextView = (TextView) view;
                if(!strike){
                    itemStrikeTextView.setPaintFlags(0);
                    mItemArrayAdapter.notifyDataSetChanged();
                    strike = true;

                } else{
                    itemStrikeTextView.setPaintFlags(itemStrikeTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    mItemArrayAdapter.notifyDataSetChanged();
                    strike = false;
                }
            }
        });
    }

    private ArrayList<String> getDataList(){
        Intent intent2 = getIntent();
        if (intent2 == null){
            return null;
        }
        return intent2.getStringArrayListExtra(MainActivity.DATA_KEY);
    }

    private int getDataIndex(){
        Intent intent2 = getIntent();
        if (intent2 == null){
            return MainActivity.ERROR_INDEX;
        }
        return intent2.getIntExtra(MainActivity.DATA_INDEX_KEY, MainActivity.ERROR_INDEX);
    }
    private void sendNewListBack(){
        Intent intent3 = getIntent();
        if (intent3 == null){
            return;
        }
        Log.d("DetailsActivity", "sendNewListBack");
        intent3.putExtra(MainActivity.DATA_KEY, mItemArrayList);
        intent3.putExtra(MainActivity.DATA_INDEX_KEY, index);
        setResult(RESULT_OK, intent3);
        finish();
    }


    @Override
    public void onBackPressed() {
        sendNewListBack();
        Log.d("DetailsActivity", "onBackPressed");
    }
}
