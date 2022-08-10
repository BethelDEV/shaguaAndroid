package com.guagua.contentprovidersample

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.StringBuilder
import java.util.*

class ContactsActivity : AppCompatActivity() {

    private lateinit var tv: TextView
    private val contractsList = LinkedList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        tv = findViewById(R.id.contactsTv)

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

    private fun readTheContacts() {
        contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )?.apply {
            while (moveToNext()) {
                val id = getString(getColumnIndex(ContactsContract.Contacts._ID))
                val name = getString(getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

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
        }
    }
}