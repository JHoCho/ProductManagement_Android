package com.example.jaeho.productmanagement.utils.multiLevelExpandablelist;

import android.content.Context;
import android.widget.ExpandableListView;

/**
 * Created by jaeho on 2017. 9. 18..
 */

public class CustomExpendableListView extends ExpandableListView {
    int groupPosition,childPosition,groupId;
    public CustomExpendableListView(Context context) {
        super(context);
    }
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        //999999 는 픽셀사이즈 입니다. ExpandableListView requires a maximum height in order to do measurement calculations.
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec); }
}
