package com.guagua.contentprovidersample

import android.Manifest
import android.content.ContentUris
import android.content.ContentValues
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.StringBuilder
import java.util.*

class ContactsActivity : AppCompatActivity() {

    private lateinit var tv: TextView
    private val contractsList = LinkedList<String>()

    private lateinit var nameEt: EditText
    private lateinit var phoneEt: EditText
    private lateinit var mailEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        tv = findViewById(R.id.contactsTv)
        nameEt = findViewById(R.id.nameEt)
        phoneEt = findViewById(R.id.phoneEt)
        mailEt = findViewById(R.id.mailEt)
        val insertBtn = findViewById<Button>(R.id.insertBtn)

        insertBtn.setOnClickListener{
            mayWriteTheContacts()
        }
        mayReadTheContacts()
    }

    private fun mayWriteTheContacts() {
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CONTACTS
            )
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_CONTACTS),
                202
            )
        } else {
            writeTheContacts()
        }
    }

    /**
     * 写入联系人数据
     */
    private fun writeTheContacts() {
        val name = nameEt.text.toString()
        val phone = phoneEt.text.toString()
        val mail = mailEt.text.toString()
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) return

        // 插入一个空数据，获取其在系统中的 id，接下来插入数据的步骤都需要用到这个 id.
        val contentValues = ContentValues()
        val contentUri =
            contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, contentValues)
        val contentID = ContentUris.parseId(contentUri!!)

        // 插入联系人的名字
        contentValues.clear()
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contentID)
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name)
        contentValues.put(
            ContactsContract.Data.MIMETYPE,
            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
        )
        contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)

        // 插入电话号码
        contentValues.clear()
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contentID)
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
        contentValues.put(
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
        )
        contentValues.put(
            ContactsContract.Data.MIMETYPE,
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
        )
        contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)

        // 插入邮箱
        if (!TextUtils.isEmpty(mail)) {
            contentValues.clear()
            contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contentID)
            contentValues.put(ContactsContract.CommonDataKinds.Email.DATA, mail)
            contentValues.put(
                ContactsContract.CommonDataKinds.Email.TYPE,
                ContactsContract.CommonDataKinds.Email.TYPE_WORK
            )
            contentValues.put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
            )
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
        }
        Toast.makeText(this, " 联系人数据添加完成 ", Toast.LENGTH_SHORT).show()

        // 更新界面
        nameEt.setText("")
        phoneEt.setText("")
        mailEt.setText("")
        // 读取联系人数据，检查一下是否添加成功
        mayReadTheContacts()
    }

    private fun mayReadTheContacts() {
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            )
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 101)
        } else {
            readTheContacts()
        }
    }

    /**
     * 读取联系人数据
     */
    private fun readTheContacts() {
        contractsList.clear()
        contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )?.apply {
            // 遍历每一条联系人的数据
            while (moveToNext()) {
                // 读取联系人数据在系统中的id，以及联系人名称
                val id = getString(getColumnIndex(ContactsContract.Contacts._ID))
                val name = getString(getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                // 读取联系人的电话号码
                val numbers = StringBuilder()
                contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = $id", null, null
                )?.apply {
                    while (moveToNext()) {
                        val num =
                            getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        numbers.append(num).append("  ")
                    }
                    close()
                }

                // 读取联系人的邮箱
                val address = StringBuilder()
                contentResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = $id", null, null
                )?.apply {
                    while (moveToNext()) {
                        val mail =
                            getString(getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                        address.append(mail).append("  ")
                    }
                    close()
                }
                contractsList.add("[$id] $name\n$numbers\n$address")
            }
            close()

            // 界面显示联系人的数据
            updateUI()
        }
    }

    private fun updateUI() {
        tv.text = ""
        val it = contractsList.listIterator()
        while (it.hasNext()) {
            tv.append(it.next())
            tv.append("\n\n")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            101 -> {
                if (grantResults.isNotEmpty() && PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                    readTheContacts()
                } else {
                    Toast.makeText(this, " 你拒绝了读取联系人权限 ", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            202 ->{
                if (grantResults.isNotEmpty()&& PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                    writeTheContacts()
                } else {
                    Toast.makeText(this, " 你拒绝了写联系人权限 ", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}