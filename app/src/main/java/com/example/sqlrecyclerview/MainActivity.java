package com.example.sqlrecyclerview;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase mdatabase;
    private GroveryAdapter mAdapter;
    private EditText medittextname;
    private TextView mTextViewAmount;
    private int mAmount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GroceryDBHelper dbHelper = new GroceryDBHelper(this);
        mdatabase = dbHelper.getWritableDatabase();
        medittextname = findViewById(R.id.edttext);
        mTextViewAmount = findViewById(R.id.tv_amount);
        RecyclerView recyclerView = findViewById(R.id.rvitem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GroveryAdapter(this,getAllItems());
        recyclerView.setAdapter(mAdapter);
        Button buttonup = findViewById(R.id.btnup);
        Button buttondown = findViewById(R.id.btndown);
        Button btnadd = findViewById(R.id.btn_add);
        buttonup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase();
            }
        });
        buttondown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrease();
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    private void increase() {
        mAmount++;
        mTextViewAmount.setText(String.valueOf(mAmount));
    }
    private void decrease() {
        if(mAmount >0){
        mAmount--;
        mTextViewAmount.setText(String.valueOf(mAmount));}
    }
    private void addItem() {
        if (medittextname.getText().toString().trim().length() == 0 || mAmount == 0){
            return;
        }
        String name = medittextname.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(GroceryContract.GroceryEntry.COLUMN_NAME, name);
        cv.put(GroceryContract.GroceryEntry.COLUMN_AMOUNT, mAmount);
        mdatabase.insert(GroceryContract.GroceryEntry.TABLE_NAME,null,cv);
        mAdapter.swapcursor(getAllItems());
        medittextname.getText().clear();
    }
    private Cursor getAllItems(){
        return mdatabase.query(GroceryContract.GroceryEntry.TABLE_NAME, null, null, null, null, null, GroceryContract.GroceryEntry.COLUMN_TIMESTAMP + " DESC");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menutambah) {
            Intent intent = new Intent(MainActivity.this, UploadBukuActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }else if (item.getItemId()==R.id.menuprofil) {
            startActivity(new Intent(MainActivity.this, ProfilActivity.class));
        }else if (item.getItemId()==R.id.menuhome){
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        }
        return true;
    }
}
