package com.shashankbhat.studenttable.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.shashankbhat.studenttable.utils.Constants.SEARCH_HISTORY_TABLE;
import static com.shashankbhat.studenttable.utils.Constants.SEARCH_ID;

/**
 * Created by SHASHANK BHAT on 05-Sep-20.
 */
@Entity(tableName = SEARCH_HISTORY_TABLE)
public class SearchHistory {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = SEARCH_ID)
    private int id;

    private String searchString;

    public SearchHistory(String searchString) {
        this.searchString = searchString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
