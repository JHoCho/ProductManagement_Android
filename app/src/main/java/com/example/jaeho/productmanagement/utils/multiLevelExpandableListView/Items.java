package com.example.jaeho.productmanagement.utils.multiLevelExpandableListView;

import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 9. 4..
 */

/**
 * 첫번째 레벨 아이템
 */
public class Items {

    private String pName;

    private ArrayList<SubCategory> mSubCategoryList;

    public Items(String pName, ArrayList<SubCategory> mSubCategoryList) {
        super();
        this.pName = pName;
        this.mSubCategoryList = mSubCategoryList;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public ArrayList<SubCategory> getmSubCategoryList() {
        return mSubCategoryList;
    }

    public void setmSubCategoryList(ArrayList<SubCategory> mSubCategoryList) {
        this.mSubCategoryList = mSubCategoryList;
    }

    /**
     *
     * 두번째 레벨 아이템
     *
     */

    public static class SubCategory {

        private String pSubCatName;
        private ArrayList<ItemList> mItemListArray;

        public SubCategory(String pSubCatName,
                           ArrayList<ItemList> mItemListArray) {
            super();
            this.pSubCatName = pSubCatName;
            this.mItemListArray = mItemListArray;
        }

        public String getpSubCatName() {
            return pSubCatName;
        }

        public void setpSubCatName(String pSubCatName) {
            this.pSubCatName = pSubCatName;
        }

        public ArrayList<ItemList> getmItemListArray() {
            return mItemListArray;
        }

        public void setmItemListArray(ArrayList<ItemList> mItemListArray) {
            this.mItemListArray = mItemListArray;
        }

        /**
         *
         * 세번째 레벨 아이템
         *
         */
        public static class ItemList {

            private String itemName;
            private String itemPrice;

            public ItemList(String itemName, String itemPrice) {
                super();
                this.itemName = itemName;
                this.itemPrice = itemPrice;
            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getItemPrice() {
                return itemPrice;
            }

            public void setItemPrice(String itemPrice) {
                this.itemPrice = itemPrice;
            }

        }

    }

}