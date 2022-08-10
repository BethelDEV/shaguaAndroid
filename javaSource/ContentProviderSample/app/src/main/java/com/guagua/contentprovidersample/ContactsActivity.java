package com.guagua.contentprovidersample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private TextView tv;
    private List<String> contractsList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        tv = findViewById(R.id.contactsTv);

        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
        )
        ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 101);
        } else {
            readTheContacts();
        }
    }

    private void readTheContacts() {
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            StringBuilder numbers = new StringBuilder();
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
            while (phones.moveToNext()) {
                String num = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                numbers.append(num).append("  ");
            }
            phones.close();

            StringBuilder address = new StringBuilder();
            Cursor mails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id, null, null);
            while (mails.moveToNext()) {
                String mail = mails.getString(mails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                address.append(mail).append("  ");
            }
            mails.close();
            contractsList.add(String.format("[%s] %s\n%s\n%s", id, name, numbers.toString(), address.toString()));
        }
        cursor.close();

        updateUI();
    }

    private void updateUI() {
        tv.setText("");
        for (String s : contractsList) {
            tv.append(s);
            tv.append("\n\n");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:
                if (grantResults != null && grantResults.length > 0 && PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                    readTheContacts();
                } else {
                    Toast.makeText(this, " 你拒绝了读取联系人权限 ", Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;
        }
    }
}