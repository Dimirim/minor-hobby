package com.dimirim.minorhobby.ui.hobby_detail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dimirim.minorhobby.R;
import com.dimirim.minorhobby.ui.main.MainActivity;

import java.util.ArrayList;

public class HobbyDetailActivity extends AppCompatActivity {
    HobbyPopupWindow popupWindow;
    TextView searchView;
    private boolean isSearchAll;
    private ArrayList<String> tagList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby_detail);

        searchView = (TextView) findViewById(R.id.tagShowTextView);
        popupWindow = new HobbyPopupWindow(HobbyDetailActivity.this);
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
