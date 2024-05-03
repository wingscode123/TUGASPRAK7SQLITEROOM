package com.example.tugasprak7sqliteroom.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tugasprak7sqliteroom.database.ContactEntity;
import com.example.tugasprak7sqliteroom.database.ContactRepository;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private static ContactRepository contactRepository;
    private LiveData<List<ContactEntity>> allContacts;

    public ContactViewModel(Application application) {
        super(application);
        contactRepository = new ContactRepository(application);
        allContacts = contactRepository.getAllContacts();
    }

    public LiveData<List<ContactEntity>> getAllContacts() {
        return allContacts;
    }

    public static void insertContact(ContactEntity contactEntity) {
        contactRepository.insertContact(contactEntity);
    }
}
