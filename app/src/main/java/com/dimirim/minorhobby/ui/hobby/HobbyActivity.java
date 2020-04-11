package com.dimirim.minorhobby.ui.hobby;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dimirim.minorhobby.R;
import com.dimirim.minorhobby.ui.hobby_detail.HobbyDetailActivity;

import java.util.ArrayList;

public class HobbyActivity extends AppCompatActivity {
    HobbyPopupWindow popupWindow;
    TextView searchView;
    TextView hobbyNameTextView;
    private boolean isSearchAll;
    private String hobbyName;
    private ArrayList<String> tagList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby);

        hobbyNameTextView = (TextView) findViewById(R.id.hobbyName);
        if (getIntent().getStringExtra("hobbyName") != null) {
            hobbyName = getIntent().getStringExtra("hobbyName");
            hobbyNameTextView.setText(hobbyName);
        }

        searchView = (TextView) findViewById(R.id.tagShowTextView);
        popupWindow = new HobbyPopupWindow(HobbyActivity.this);
        ConstraintLayout hobbySearch = (ConstraintLayout) findViewById(R.id.hobbySearchLayout);
        hobbySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.callFunction(searchView);
//                isSearchAll = popupWindow.isSearchAll;
            }
        });
    }
}
