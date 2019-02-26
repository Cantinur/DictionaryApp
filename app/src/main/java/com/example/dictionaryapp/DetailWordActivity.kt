package com.example.dictionaryapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailWordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_word)
        findViewById<TextView>(R.id.word_view).text = intent.getStringExtra("word")
        findViewById<TextView>(R.id.definition_text_view).text = intent.getStringExtra("definition")
    }
}
