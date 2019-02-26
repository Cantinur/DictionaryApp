package com.example.dictionaryapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
class DictionaryActivity : AppCompatActivity() {
    private var dictionary = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)
        this.dictionary = intent.getSerializableExtra("dictionary") as HashMap<String, String>
        val listView = findViewById<ListView>(R.id.dictionary_list)
        setListContent(ArrayList(dictionary.keys.sorted()), listView)
    }

    private fun setListContent(content: ArrayList<String>, listView: ListView) {
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, content)

        listView.setOnItemClickListener { parent, _, position, _ ->
            val intent = Intent(this, DetailWordActivity::class.java)
            intent.putExtra("word", parent.getItemAtPosition(position).toString())
            intent.putExtra("definition", this.dictionary[parent.getItemAtPosition(position).toString()])
            startActivity(intent)
        }
    }
}
