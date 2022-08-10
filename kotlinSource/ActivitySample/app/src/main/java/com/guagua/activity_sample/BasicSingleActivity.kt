package com.guagua.activity_sample

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 *
 * @ProjectName:    Activity_Sample
 * @Package:        com.guagua.activity_sample
 * @ClassName:      BasicSingleActivity
 * @Description:     java类作用描述
 * @Author:         Bethel
 * @CreateDate:     2022/8/6 08:04
 * @UpdateUser:     更新者：
 * @UpdateDate:     2022/8/6 08:04
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
open class BasicSingleActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_single)
        Log.i("launchMode_Activity","onCreate() task: $taskId, instance: ${this.toString()}")

        val standardBtn: Button = findViewById(R.id.standardBtn)
        standardBtn.setOnClickListener{
            val intent = Intent(this, StandardActivity::class.java)
            startActivity(intent)
        }
        val singleTaskBtn: Button = findViewById(R.id.singleTaskBtn)
        singleTaskBtn.setOnClickListener{
            val intent = Intent(this, SingleTaskActivity::class.java)
            startActivity(intent)
        }
        val singleTopBtn: Button = findViewById(R.id.singleTopBtn)
        singleTopBtn.setOnClickListener{
            val intent = Intent(this, SingleTopActivity::class.java)
            startActivity(intent)
        }
        val singleInstanceBtn: Button = findViewById(R.id.singleInstanceBtn)
        singleInstanceBtn.setOnClickListener{
            val intent = Intent(this, SingleInstanceActivity::class.java)
            startActivity(intent)
        }
    }
}