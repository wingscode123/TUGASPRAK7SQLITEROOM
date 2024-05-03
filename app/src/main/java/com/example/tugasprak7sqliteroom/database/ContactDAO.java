package com.example.tugasprak7sqliteroom.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {
    @Insert
    void insertContact(ContactEntity contact);

    @Query("SELECT * FROM contacts")
    LiveData<List<ContactEntity>> getAllContacts();

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    ContactEntity getContactById(int contactId);

    @Update
    void updateContact(ContactEntity contact);

    @Query("DELETE FROM contacts WHERE id = :contactId")
    void deleteContact(int contactId);

}
