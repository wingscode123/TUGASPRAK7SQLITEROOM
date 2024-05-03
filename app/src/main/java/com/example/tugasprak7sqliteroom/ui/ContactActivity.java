package com.example.tugasprak7sqliteroom.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasprak7sqliteroom.R;
import com.example.tugasprak7sqliteroom.database.ContactEntity;

public class ContactActivity extends AppCompatActivity {
    private ContactViewModel contactViewModel;
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private EditText etName, etNumber, etInstagram, etGroup;
    private LinearLayout layAddContact;
    private Button btnClear, btnSubmit;
    private TextView option;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        recyclerView = findViewById(R.id.recycle_contact);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        etName = findViewById(R.id.et_name);
        etNumber = findViewById(R.id.et_number);
        etInstagram = findViewById(R.id.et_instagram);
        etGroup = findViewById(R.id.et_group);
        btnClear = findViewById(R.id.btn_clear);
        btnSubmit = findViewById(R.id.btn_submit);
        layAddContact = findViewById(R.id.layout_add);
        option = findViewById(R.id.tv_option);

        option.setOnClickListener(v -> {
            if (recyclerView.getVisibility() == View.VISIBLE) {
                recyclerView.setVisibility(View.GONE);
                layAddContact.setVisibility(View.VISIBLE);
                clearData();
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                layAddContact.setVisibility(View.GONE);
            }
        });

        btnClear.setOnClickListener(v -> {
            clearData();
        });

        btnSubmit.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String number = etNumber.getText().toString();
            String instagram = etInstagram.getText().toString();
            String group = etGroup.getText().toString();

            if (name.isEmpty() || number.isEmpty() || instagram.isEmpty() || group.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                contactViewModel.insertContact(new ContactEntity(name, number, group, instagram));

                Toast.makeText(this, "Contact added successfully", Toast.LENGTH_SHORT).show();

                clearData();
            }
        });

        contactAdapter = new ContactAdapter(this);
        recyclerView.setAdapter(contactAdapter);

        contactViewModel.getAllContacts().observe(this, contactEntities -> {
            contactAdapter.setContacts(contactEntities);
            contactAdapter.notifyDataSetChanged();
        });
    }

    private void clearData() {
        etName.setText("");
        etNumber.setText("");
        etInstagram.setText("");
        etGroup.setText("");
    }
}
