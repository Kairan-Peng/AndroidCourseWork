package com.example.homework3;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends ListActivity {

    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new ArrayList<String>();
        for (int i = 1; i <= 20; i++) {
            data.add("第" + i + "个item");
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item_list_activity, R.id.item_tv, data);
        setListAdapter(arrayAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(MyListActivity.this, "点中了" + data.get(position), Toast.LENGTH_SHORT).show();
    }
}
