package com.codepath.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class MainActivity extends AppCompatActivity {

    static SQLiteDatabase db;

    ListView lvItems;

    CustomUsersAdapter itemsAdapter;
    ArrayList<Item> itemArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);

        PracticeDatabaseHelper dbHelper = new PracticeDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        getAllItems();

        itemsAdapter = new CustomUsersAdapter(this, itemArray);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    public void getAllItems() {
        final QueryResultIterable<Item> iter = cupboard().withDatabase(db).query(Item.class).query();
        itemArray = (ArrayList<Item>) getListFromQueryResultIterator(iter);
    }

    private static List<Item> getListFromQueryResultIterator(QueryResultIterable<Item> iter) {

        final List<Item> bunnies = new ArrayList<Item>();
        for (Item bunny : iter) {
            bunnies.add(bunny);
        }
        iter.close();

        return bunnies;
    }

    private final int REQUEST_CODE = 20;
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        Item i = itemArray.get(pos);
                        cupboard().withDatabase(db).delete(i);
                        itemArray.remove(pos);
                        itemsAdapter.notifyDataSetChanged();

                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                Intent editItem = new Intent(MainActivity.this, EditItemActivity.class);
                Item i = (Item) lvItems.getItemAtPosition(pos);
                editItem.putExtra("text", i.getName());
                editItem.putExtra("priority", i.getPriority());
                editItem.putExtra("pos", pos);
                startActivityForResult(editItem, REQUEST_CODE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String text = data.getExtras().getString("text");
            String priority = data.getExtras().getString("priority");
            int pos = data.getExtras().getInt("pos");

            Item i = itemArray.get(pos);
            Item toUpdate = cupboard().withDatabase(db).get(i);

            i.setName(text);
            i.setPriority(priority);
            itemArray.set(pos, i);

            toUpdate.setName(text);
            toUpdate.setPriority(priority);
            cupboard().withDatabase(db).put(toUpdate);
            itemsAdapter.notifyDataSetChanged();
        }
    }

    public void onAddItem(View v) {
        Item i = new Item("", "Low");
        cupboard().withDatabase(db).put(i);
        itemArray.add(i);

        Intent editItem = new Intent(MainActivity.this, EditItemActivity.class);
        editItem.putExtra("text", "");
        editItem.putExtra("priority", "Low");
        editItem.putExtra("pos", itemArray.size() - 1);
        startActivityForResult(editItem, REQUEST_CODE);
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
