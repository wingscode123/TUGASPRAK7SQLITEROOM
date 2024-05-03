package com.example.tugasprak7sqliteroom.database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ContactEntity.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {
    public static ExecutorService databaseWriteExecutor =
            Executors.newSingleThreadExecutor();

    public static ContactDatabase instance;

    public abstract ContactDAO contactDao();

    public static synchronized ContactDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ContactDatabase.class, "contact_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Inisialisasi data awal database
            databaseWriteExecutor.execute(() -> {
                ContactDAO dao = instance.contactDao();
                dao.insertContact(new ContactEntity("John Doe", "1234567890", "john_doe", "Work"));
                dao.insertContact(new ContactEntity("Jane Smith", "0987654321", "jane_smith", "Home"));
            });
        }
    };
}


