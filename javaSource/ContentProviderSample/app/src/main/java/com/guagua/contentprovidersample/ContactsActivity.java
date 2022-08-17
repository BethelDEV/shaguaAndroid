package com.guagua.contentprovidersample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private TextView tv;
    private final List<String> contractsList = new LinkedList<>();

    private EditText nameEt, phoneEt, mailEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        tv = findViewById(R.id.contactsTv);
        nameEt = findViewById(R.id.nameEt);
        phoneEt = findViewById(R.id.phoneEt);
        mailEt = findViewById(R.id.mailEt);
        Button insertBtn = findViewById(R.id.insertBtn);

        insertBtn.setOnClickListener(view -> mayWriteTheContacts());

        mayReadTheContacts();
    }

    private void mayWriteTheContacts() {
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CONTACTS
        )
        ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, 202);
        } else {
            writeTheContacts();
        }
    }

    /**
     * 写入联系人数据
     */
    private void writeTheContacts() {
        String name = nameEt.getText().toString();
        String phone = phoneEt.getText().toString();
        String mail = mailEt.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) return;

        // 插入一个空数据，获取其在系统中的 id，接下来插入数据的步骤都需要用到这个 id.
        ContentValues contentValues = new ContentValues();
        Uri contentUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
        long contentID = ContentUris.parseId(contentUri);

        // 插入联系人的名字
        contentValues.clear();
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contentID);
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contentValues);

        // 插入电话号码
        contentValues.clear();
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contentID);
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contentValues);

        // 插入邮箱
        if (!TextUtils.isEmpty(mail)) {
            contentValues.clear();
            contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contentID);
            contentValues.put(ContactsContract.CommonDataKinds.Email.DATA, mail);
            contentValues.put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
            contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contentValues);
        }
        Toast.makeText(this, " 联系人数据添加完成 ", Toast.LENGTH_SHORT).show();

        // 更新界面
        nameEt.setText("");
        phoneEt.setText("");
        mailEt.setText("");
        // 读取联系人数据，检查一下是否添加成功
        mayReadTheContacts();
    }

    private void mayReadTheContacts() {
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

    /**
     * 读取联系人数据
     */
    private void readTheContacts() {
        contractsList.clear();
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        // 遍历每一条联系人的数据
        while (cursor.moveToNext()) {
            // 读取联系人数据在系统中的id，以及联系人名称
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            // 读取联系人的电话号码
            StringBuilder numbers = new StringBuilder();
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
            while (phones.moveToNext()) {
                String num = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                numbers.append(num).append("  ");
            }
            phones.close();

            // 读取联系人的邮箱
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

        // 界面显示联系人的数据
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
                if (grantResults.length > 0 && PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                    readTheContacts();
                } else {
                    Toast.makeText(this, " 你拒绝了读取联系人权限 ", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case 202:
                if (grantResults.length > 0 && PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                    writeTheContacts();
                } else {
                    Toast.makeText(this, " 你拒绝了写联系人权限 ", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }
}