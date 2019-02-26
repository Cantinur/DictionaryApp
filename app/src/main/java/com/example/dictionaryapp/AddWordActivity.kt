package com.example.dictionaryapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import java.io.PrintStream
class AddWordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
    }

    fun addNewWord(view: View){
        val returnIntent = Intent(this, StartScreenActivity::class.java).also {
            it.putExtra("newWord", findViewById<EditText>(R.id.word_text).text.toString())
            it.putExtra("newDefinition", findViewById<EditText>(R.id.definition_text).text.toString())
        }
        this.setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
