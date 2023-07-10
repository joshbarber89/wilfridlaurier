package com.example.androidassignment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ChatDBHelperTest {

    @BeforeClass
    public static void beforeClass() {
        InstrumentationRegistry.getTargetContext().deleteDatabase("Messages.db");
    }

    @Test
    public void createDatabase() {
        ChatDatabaseHelper cdh = new ChatDatabaseHelper(ApplicationProvider.getApplicationContext());
        SQLiteDatabase db = cdh.getReadableDatabase();
        assertTrue(db.isDatabaseIntegrityOk());
    }

    @Test
    public void onUpgrade() {
        ChatDatabaseHelper cdh = new ChatDatabaseHelper(ApplicationProvider.getApplicationContext());
        SQLiteDatabase dbWrite = cdh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ChatDatabaseHelper.KEY_MESSAGE, "test");
        dbWrite.insert(ChatDatabaseHelper.TABLE_NAME, null, cv);

        SQLiteDatabase dbRead = cdh.getReadableDatabase();

        Cursor cursor = dbRead.rawQuery("SELECT * FROM messages", null);
        cursor.moveToFirst();

        String result = "";
        while(!cursor.isAfterLast() ) {
            int columnIndex = cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);
            if (columnIndex > -1) {
                result = cursor.getString(columnIndex);

            }
            cursor.moveToNext();
        }
        assertEquals(result, "test");
        if (result.equals("test")) {
            cdh.onUpgrade(dbRead, 1, 2);
            Cursor c = dbRead.rawQuery("SELECT * FROM messages", null);
            c.moveToFirst();
            String r = "";
            while(!cursor.isAfterLast() ) {
                int columnIndex = cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);
                if (columnIndex > -1) {
                    r = cursor.getString(columnIndex);

                }
                cursor.moveToNext();
            }
            assertEquals(r, "");
        }
    }
}
