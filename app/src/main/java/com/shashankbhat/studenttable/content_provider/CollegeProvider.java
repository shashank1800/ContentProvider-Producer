package com.shashankbhat.studenttable.content_provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shashankbhat.studenttable.model.SearchHistory;
import com.shashankbhat.studenttable.room.CollegeDao;
import com.shashankbhat.studenttable.room.CollegeDatabase;

import static com.shashankbhat.studenttable.utils.Constants.AUTHORITY;
import static com.shashankbhat.studenttable.utils.Constants.DEPT_TABLE;
import static com.shashankbhat.studenttable.utils.Constants.SEARCH_HISTORY_TABLE;
import static com.shashankbhat.studenttable.utils.Constants.STUD_TABLE;

/**
 * Created by SHASHANK BHAT on 04-Sep-20.
 */
public class CollegeProvider extends ContentProvider {

    public static final String CONTENT_URI_1 = String.valueOf(Uri.parse("content://" + AUTHORITY + "/" + STUD_TABLE));
    public static final String CONTENT_URI_2 = String.valueOf(Uri.parse("content://" + AUTHORITY + "/" + DEPT_TABLE));
    public static final String CONTENT_URI_3 = String.valueOf(Uri.parse("content://" + AUTHORITY + "/" + SEARCH_HISTORY_TABLE));

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private CollegeDao collegeDao;

    static {
        /*
         * The calls to addURI() go here, for all of the content URI patterns that the provider
         * should recognize. For this snippet, only the calls for table 3 are shown.

         * Sets the integer value for multiple rows in table 3 to 1. Notice that no wildcard is used
         * in the path
         */
        uriMatcher.addURI(AUTHORITY, STUD_TABLE, 1);
        uriMatcher.addURI(AUTHORITY, DEPT_TABLE, 2);
        uriMatcher.addURI(AUTHORITY, SEARCH_HISTORY_TABLE, 5);

        /*
         * Sets the code for a single row to 2. In this case, the "#" wildcard is
         * used. "content://com.example.app.provider/table3/3" matches, but
         * "content://com.example.app.provider/table3 doesn't.
         */
        uriMatcher.addURI(AUTHORITY, STUD_TABLE+"/#", 3);
        uriMatcher.addURI(AUTHORITY, DEPT_TABLE+"/*", 4);
    }


    @Override
    public boolean onCreate() {
        collegeDao = CollegeDatabase.getInstance(getContext()).collegeDao();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projetion, @Nullable String selection, @Nullable String[] selectionArg, @Nullable String order) {

        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case 1:
                cursor = collegeDao.findAllStud();
                ContentResolver contentResolver = getContext().getContentResolver();
                cursor.setNotificationUri(contentResolver, uri);
                return cursor;
            case 2:
                cursor = collegeDao.findAllDept();
                if (getContext() != null ) {
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    return cursor;
                }

            case 4:
                Log.i("Search",selectionArg[0]);
                assert selectionArg != null;
                cursor = collegeDao.findAllDept(selectionArg[0]);
                collegeDao.insertSearch(new SearchHistory(selectionArg[0]));
//                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;

            case 5:
                cursor = collegeDao.findAllSearch();
                return cursor;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case 1:
                return "vnd.android.cursor.dir/"+STUD_TABLE;
            case 2:
                return "vnd.android.cursor.dir/"+DEPT_TABLE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int count;
        switch (uriMatcher.match(uri)) {
            case 3:
                if (getContext() != null) {
                    count = collegeDao.delete(ContentUris.parseId(uri));
                    getContext().getContentResolver().notifyChange(uri, null);
                    return count;
                }
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
