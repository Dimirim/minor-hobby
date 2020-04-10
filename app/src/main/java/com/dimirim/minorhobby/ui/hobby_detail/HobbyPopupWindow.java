package com.dimirim.minorhobby.ui.hobby_detail;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.dimirim.minorhobby.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class HobbyPopupWindow {
    Switch searchOption;
    protected String searchText = "";
    protected boolean isSearchAll = false;
    protected ArrayList<String> returnTagList = new ArrayList<>();
    private ArrayList<Boolean> isTagChecked = new ArrayList<>();
    private ArrayList<String> tagList = new ArrayList<>();
    private Context context;

    public HobbyPopupWindow(Context context) {
        this.context = context;
    }

    public void callFunction(final TextView searchView) {

        resetPopupWindow();

        final Dialog filterPopupWindow = new Dialog(context);
        filterPopupWindow.show();

        context.setTheme(R.style.MeterialComponents);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        filterPopupWindow.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filterPopupWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        filterPopupWindow.setContentView(R.layout.hobby_custom_popup_window);
        filterPopupWindow.getWindow().setGravity(Gravity.RIGHT | Gravity.TOP);

        Button submitBtn = (Button) filterPopupWindow.findViewById(R.id.popupSubmitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setText(getSearchbarText());
                filterPopupWindow.dismiss();
            }
        });

        Button cancleBtn = (Button) filterPopupWindow.findViewById(R.id.popupCancleBtn);
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPopupWindow.dismiss();
            }
        });

        searchOption = (Switch) filterPopupWindow.findViewById(R.id.searchOption);
        searchOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isSearchAll = true;
                } else {
                    isSearchAll = false;
                }
            }
        });

        ChipGroup mChipGroup = (ChipGroup) filterPopupWindow.findViewById(R.id.chipGroup);
        for (int i = 0; i < tagList.size(); i++) {
            final Chip chip = new Chip(context);

            //다중 선택 설정
            chip.setChipDrawable(ChipDrawable.createFromAttributes(context, null, 0, R.style.Widget_MaterialComponents_Chip_Filter));
            //체그 아이콘 없애기
            chip.setCheckedIconVisible(false);
            chip.setText("#" + tagList.get(i));
            isTagChecked.add(false);
            chip.setTag(i);
            chip.setRippleColor(null);
            chip.setChipStrokeWidth((float) (0.5 * dm.density));
            chip.setTypeface(ResourcesCompat.getFont(context, R.font.notosanscjkkrmedium));
            chip.setChipStrokeColor(ColorStateList.valueOf(Color.parseColor("#999999")));
            chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#ffffff")));
            chip.setTextColor(ColorStateList.valueOf(Color.parseColor("#999999")));
            chip.setOnClickListener(new Chip.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isTagChecked.get((int) (v.getTag()))) {
                        isTagChecked.set((int) (v.getTag()), true);
                        chip.setChipStrokeColor(ColorStateList.valueOf(Color.parseColor("#9d3cc8")));
                        chip.setTextColor(ColorStateList.valueOf(Color.parseColor("#9d3cc8")));
                    } else {
                        chip.setChipStrokeColor(ColorStateList.valueOf(Color.parseColor("#999999")));
                        chip.setTextColor(ColorStateList.valueOf(Color.parseColor("#999999")));
                        isTagChecked.set((int) (v.getTag()), false);
                    }
                }
            });
            mChipGroup.addView(chip);
        }
    }

    public String getSearchbarText() {
        for (int i = 0; i < tagList.size(); i++) {
            if (isTagChecked.get(i)) {
                searchText = searchText.concat("#");
                searchText = searchText.concat(tagList.get(i));
                searchText = searchText.concat(", ");
                returnTagList.add(tagList.get(i));
            }
        }

        if (searchText.length() > 0) {
            searchText = searchText.substring(0, searchText.length() - 2);
        }
        return searchText;
    }

    private void resetPopupWindow() {
        searchText = "";
        returnTagList.clear();
        isTagChecked.clear();
        tagList.clear();

        //임시 태그 입니다
        tagList.add("모임");
        tagList.add("입문가이드");
        tagList.add("강좌");
        tagList.add("중고거래");
        tagList.add("입문가이드");
        tagList.add("질문");
    }

}
