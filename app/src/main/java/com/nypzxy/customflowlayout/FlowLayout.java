package com.nypzxy.customflowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Rita on 3/25/2017.
 */

public class FlowLayout extends ViewGroup {


    private ArrayList<Row> rowList;

    public FlowLayout(Context context) {
        this(context,null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //1. get the Layout width with padding
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //2. get the layout without padding size
        int noPaddingWidth = width - getPaddingLeft() - getPaddingRight();

        rowList = new ArrayList<>();

        //3.create a row object to start add row view
        Row row = new Row();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);

            view.measure(0,0);
            if(row.viewList.size()==0){
                //means first view
                row.addView(view);
            }else if(view.getMeasuredWidth()+row.rowWidth+horizontalSpace>noPaddingWidth){//if view.width + row.width > noPaddingWidth then create another row to store

                //store the first row first
                rowList.add(row);

                row = new Row();
                row.addView(view);
            }else{
                row.addView(view);
            }

            //Note: if the view is the last child, it need to manually store the last row into the rowList
            //because there is no more view added and cannot calculate anymore
            if(i==(getChildCount()-1)){
                rowList.add(row);
            }
        }

        //After add View, Calculate the total width and height to measure the layout

        int height = getPaddingTop()+getPaddingBottom();
        for (int i = 0; i < rowList.size(); i++) {
            height+= rowList.get(i).rowHeight;
        }
        height += (rowList.size()-1)*verticalSpace;

        setMeasuredDimension(width,height);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //these are the first view layout
        int top = getPaddingTop();
        int left = getPaddingLeft();

        //search for each row
        for (int i=0;i<rowList.size();i++){
            Row row = rowList.get(i);

            if(i>0){
                top += rowList.get(i-1).rowHeight + verticalSpace;
            }

            ArrayList<View> viewList = row.viewList;
            for (int j = 0; j < viewList.size(); j++){

                View view = viewList.get(j);

                if(j==0){
                    view.layout(left,top,left+view.getMeasuredWidth(),top+view.getMeasuredHeight());
                }else{
                    View preView = viewList.get(j - 1);

                    int currentLeft = preView.getRight() + horizontalSpace;

                    view.layout(currentLeft,top,currentLeft+view.getMeasuredWidth(),preView.getBottom());
                }

            }
        }
    }

    /**
     * horizontalSpace used to set the view's horizontal space
     */
    private static int horizontalSpace = 15;

    public void setHorizontalSpace(int horizontalSpace){
        this.horizontalSpace = horizontalSpace;
    }

    private static int verticalSpace = 15;

    public void setVerticalSpace(int verticalSpace){
        this.verticalSpace = verticalSpace;
    }

    /**
     * Defined for each row of the FlowLayout
     *
     * Each row has its own Height, Width and the view
     *
     */
    public class Row{
        //viewList stores all view in a row
        public ArrayList<View> viewList = new ArrayList<>();
        public int rowWidth;
        public int rowHeight;

        /**
         * addView method to store row's view to the row which belongs to it
         * @param view
         *
         * After added view, the row has to re-calculate its width and height
         */
        public void addView(View view){
            viewList.add(view);
            if(viewList.size()==1){
                //means the added view is its first view
                rowWidth = view.getMeasuredWidth();
            }else{
                //means not the first one need to calculate width
                rowWidth += view.getMeasuredWidth() + horizontalSpace;
            }

            rowHeight = Math.max(rowHeight,view.getMeasuredHeight());
        }
    }


}
