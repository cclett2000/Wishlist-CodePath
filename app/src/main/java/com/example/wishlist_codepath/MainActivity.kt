package com.example.wishlist_codepath

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.math.BigInteger
import java.security.MessageDigest
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // EE
        var enableReset = false
        val eeImg = findViewById<ImageView>(R.id.ee_img)
        eeImg.visibility = View.GONE

        // input vars
        val addItemToList = findViewById<Button>(R.id.btn_add_item)
        val itemName = findViewById<EditText>(R.id.item_name_input)
        val storeName = findViewById<EditText>(R.id.input_store_info)
        val price = findViewById<EditText>(R.id.input_price_info)

       // to invoke easter egg type this into item: Here's that Easter Egg For You PAPI! <3

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)   // get recyclerView
        val listData = ArrayList<Model>()                                  // ArrayList of model class
        recyclerView.layoutManager = LinearLayoutManager(this)      // vertical layout manager

        addItemToList.setOnClickListener{
            // reset
            if (enableReset){
                finish()
                startActivity(intent)
            }

            // easter egg!
            if (generateMD5(itemName.text.toString()) == "068d7cff69ed27389110a0bba52bbc67"){
                @SuppressLint("SetTextI18n")
                fun hideSoftKeyboard(view: View) {
                    val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    manager.hideSoftInputFromWindow(view.windowToken, 0)
                    enableReset = true
                    addItemToList.text = "Reset!"
                }

                eeImg.visibility = View.VISIBLE
                hideSoftKeyboard(it)

            }
            else {
                // empty field check
                if (itemName.text.toString() != "" && storeName.text.toString() != "" && price.text.toString() != "") {
                    listData.add(
                        Model(
                            itemName.text.toString(),
                            storeName.text.toString(),
                            price.text.toString().toFloat()
                        )
                    )

                    val adapter = CustomAdapter(listData)   // pass listData to customAdapter
                    recyclerView.adapter = adapter          // set adapter with recyclerView
                    recyclerView.scrollToPosition(listData.size - 1)

                } else {
                    // THE BEAR SPEAKETH
                    Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    fun generateMD5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}