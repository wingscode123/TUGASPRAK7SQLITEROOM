package com.example.tugasprak7sqliteroom.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactRepository {
    private ContactDAO contactDao;
    private LiveData<List<ContactEntity>> allContacts;

    public ContactRepository(Application application) {
        ContactDatabase database = ContactDatabase.getInstance(application);
        contactDao = database.contactDao();
        allContacts = (LiveData<List<ContactEntity>>) contactDao.getAllContacts();
    }

    public LiveData<List<ContactEntity>> getAllContacts() {
        return allContacts;
    }

    public void insertContact(ContactEntity contactEntity) {
        ContactDatabase.databaseWriteExecutor.execute(() -> {
            contactDao.insertContact(contactEntity);
        });
    }
}

