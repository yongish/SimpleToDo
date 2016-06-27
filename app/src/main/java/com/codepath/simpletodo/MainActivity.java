package com.codepath.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class MainActivity extends AppCompatActivity {
    static SQLiteDatabase db;
    ArrayList<String> itemNameArray;
    ArrayList<Item> itemArray;
    //CustomItemsAdapter itemAdapter;
    ArrayAdapter<String> itemAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.lvItems);
        readItems();
        itemAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, itemNameArray);
        listView.setAdapter(itemAdapter);

        //populateUsersList();

        setupListViewListener();
    }

    private final int REQUEST_CODE = 20;
    private void setupListViewListener() {
        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        Item i = itemArray.get(pos);
                        cupboard().withDatabase(db).delete(Item.class, i.get_id());
                        itemArray.remove(pos);
                        itemNameArray.remove(pos);
                        itemAdapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                Intent editItem = new Intent(MainActivity.this, EditItemActivity.class);
                String itemText = (String) listView.getItemAtPosition(pos);
                editItem.putExtra("text", itemText);
                editItem.putExtra("pos", pos);
                startActivityForResult(editItem, REQUEST_CODE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String text = data.getExtras().getString("text");
            int pos = data.getExtras().getInt("pos");

            Item i = itemArray.get(pos);
            i.setName(text);
            itemNameArray.set(pos, text);
            cupboard().withDatabase(db).put(i);
            itemAdapter.notifyDataSetChanged();
        }
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();

        Item i = new Item(itemText);
        cupboard().withDatabase(db).put(i);
        itemArray.add(i);
        itemAdapter.add(i.getName());
        itemAdapter.notifyDataSetChanged();

        etNewItem.setText("");
    }

    private static List<Item> getListFromQueryResultIterator(QueryResultIterable<Item> iter) {

        final List<Item> items = new ArrayList<Item>();
        for (Item i : iter) {
            items.add(i);
        }
        iter.close();

        return items;
    }

    private void readItems() {
        final QueryResultIterable<Item> iter = cupboard().withDatabase(db).query(Item.class).query();
        itemArray = (ArrayList<Item>) getListFromQueryResultIterator(iter);

        for (Item i : itemArray) {
            itemNameArray.add(i.getName());
        }
        itemAdapter.notifyDataSetChanged();
/*
        // Create the adapter to convert the array to views
        itemAdapter = new CustomItemsAdapter(this, itemArray);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(itemAdapter);*/
    }

    public void showSoftKeyboard(View view){
        if(view.requestFocus()){
            InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
