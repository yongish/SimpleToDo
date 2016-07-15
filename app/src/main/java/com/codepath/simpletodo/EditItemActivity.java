package com.codepath.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EditItemActivity extends AppCompatActivity {
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String text = getIntent().getStringExtra("text");
        String priority = getIntent().getStringExtra("priority");
        position = getIntent().getIntExtra("pos", 0);

        RadioGroup radioPriorityGroup = (RadioGroup) findViewById(R.id.radioGroup);
        switch (priority) {
            case "Low":
                radioPriorityGroup.check(R.id.radioLow);
                break;
            case "Medium":
                radioPriorityGroup.check(R.id.radioMed);
                break;
            case "High":
                radioPriorityGroup.check(R.id.radioHigh);
        }


        EditText editItem = (EditText) findViewById(R.id.editName);
        editItem.setText(text);
        editItem.setSelection(editItem.getText().length());
        editItem.requestFocus();
    }

    public void onSubmit(View v) {
        EditText editItem = (EditText) findViewById(R.id.editName);

        RadioGroup radioPriorityGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int selectedId = radioPriorityGroup.getCheckedRadioButtonId();
        RadioButton radioPriorityButton = (RadioButton) findViewById(selectedId);

        Intent data = new Intent();
        data.putExtra("text", editItem.getText().toString());
        data.putExtra("priority", radioPriorityButton.getText());
        data.putExtra("pos", position);

        setResult(RESULT_OK, data);
        finish();
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
