package com.example.tugasprak7sqliteroom.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.tugasprak7sqliteroom.R;
import com.example.tugasprak7sqliteroom.database.ContactEntity;

public class DetailContactActivity extends
        AppCompatActivity {
    TextView tvName, tvNumber, tvInstagram, tvGroup, back_link;
    TextView btnCall, btnMessage, btnInstagram;
    LinearLayout layWhatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        back_link = findViewById(R.id.back_link);
        tvName = findViewById(R.id.tv_name);
        tvNumber = findViewById(R.id.tv_number);
        tvInstagram = findViewById(R.id.tv_instagram);
        tvGroup = findViewById(R.id.tv_group);
        btnCall = findViewById(R.id.btn_call);
        btnMessage = findViewById(R.id.btn_message);
        btnInstagram =
                findViewById(R.id.btn_instagram);
        layWhatsapp =
                findViewById(R.id.layout_whatsapp);

        ContactViewModel contactViewModel = new ViewModelProvider(this).get(com.example.tugasprak7sqliteroom.ui.ContactViewModel.class);
// get intent extra
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            String getCName =
                    bundle.getString("cname");
            String getCNumber =
                    bundle.getString("cnumber");
            String getCInstagram =
                    bundle.getString("cinstagram");
            String getCGroup =
                    bundle.getString("cgroup");
            tvName.setText(getCName);

            tvNumber.setText(getCNumber);
            tvInstagram.setText(getCInstagram);
            tvGroup.setText(getCGroup);
            btnCall.setOnClickListener(v -> {
                Intent intent = new
                        Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + getCNumber));
                if
                (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new
                            String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                startActivity(intent);
            });
            btnMessage.setOnClickListener(v -> {
                Intent intent = new
                        Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("sms:" + getCNumber));
                startActivity(intent);
            });
            layWhatsapp.setOnClickListener(v -> {
                Intent sendIntent = new
                        Intent("android.intent.action.MAIN");
                sendIntent.setAction(Intent.ACTION_VIEW);
                sendIntent.setPackage("com.whatsapp");

                String url =

                        "https://api.whatsapp.com/send?phone=" + getCNumber + "&text=";
                sendIntent.setData(Uri.parse(url));
                startActivity(sendIntent);
            });
            btnInstagram.setOnClickListener(v -> {
                startActivity(new
                        Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/" + getCInstagram)));
            });
        }
        back_link.setOnClickListener(v -> {
            setResult(RESULT_OK, null);

            finish();
        });
    }

    private void saveContactToDatabase(String name, String number, String instagram, String group) {
        ContactEntity contactEntity = new ContactEntity(name, number, instagram, group);
        ContactViewModel.insertContact(contactEntity);
    }

    // Metode untuk menangani tindakan pengguna saat menekan tombol submit
    private void handleSubmitButton() {
        String name = tvName.getText().toString();
        String number = tvNumber.getText().toString();
        String instagram = tvInstagram.getText().toString();
        String group = tvGroup.getText().toString();

        saveContactToDatabase(name, number, instagram, group);

    }
}