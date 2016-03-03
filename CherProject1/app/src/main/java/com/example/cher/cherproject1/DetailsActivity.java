package com.example.cher.cherproject1;

import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleListNameTextView = (TextView) findViewById(R.id.titleListNameTextView_id);
        itemsEditText = (EditText) findViewById(R.id.items_editText_id);
        itemsListView = (ListView) findViewById(R.id.items_listView_id);

        String extra = getIntent().getStringExtra("newTitleString");
        titleListNameTextView.setText(extra);

        mItemArrayList = new ArrayList<>();

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

}
