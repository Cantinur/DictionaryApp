package com.example.dictionaryapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import java.io.IOException
import java.io.InputStream

class StartScreenActivity : AppCompatActivity() {
    private var dictionary = HashMap<String, String>()
    //MARK:- Life cycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
        readFile("words.txt")
    }

    //MARK:- Private methods
    private fun readFile(fileName: String) {
        try {
            applicationContext.assets.open(fileName).bufferedReader().use{ line ->
                line.forEachLine {content ->
                    val parts = content.split("-")
                    dictionary[parts.first()] = parts.last()
                }
            }
        } catch (e:IOException) {
            println("File $fileName does't exist")
        }
    }

    //MARK:- Methods called by UI
    fun startGame(view: View) {
        val intent = Intent(this, PlayGameActivity::class.java)
        intent.putExtra("dictionary", dictionary)
        startActivity(intent)
    }

    fun addNewWord(view: View) {
        startActivityForResult(Intent(this, AddWordActivity::class.java), 1234)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1234) {
            val newWord = data?.getStringExtra("newWord")
            val newDefinition = data?.getStringExtra("newDefinition")

            if (newWord != null && newDefinition != null) dictionary[newWord] = newDefinition

            Toast.makeText(this,
                if (newWord != null && newDefinition != null)
                    "$newWord is added to dictionary"
                else
                    "Can't add empty values",
            Toast.LENGTH_SHORT).show()

        } else
            Toast.makeText(this, "WORD WAS NOT ADDED", Toast.LENGTH_SHORT).show()
    }

    fun getFullDictionary(view: View) {
        val intent = Intent(this, DictionaryActivity::class.java)
        intent.putExtra("dictionary", dictionary)
        startActivity(intent)
    }
}
