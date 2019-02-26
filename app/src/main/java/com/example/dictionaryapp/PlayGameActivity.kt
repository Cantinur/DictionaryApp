package com.example.dictionaryapp


import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.*
class PlayGameActivity : AppCompatActivity() {
    private var dictionary = HashMap<String, String>()
    private var points = 0
    private var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)
        this.dictionary = intent.getSerializableExtra("dictionary") as HashMap<String, String>
        val listView = findViewById<ListView>(R.id.list_of_words)
        wordGame(listView)
        player = MediaPlayer.create(this, R.raw.jeopardy_song)
        letsListen()

        if (savedInstanceState != null) points = savedInstanceState.getInt("savedPoints")
        else Log.i("Points", "Points were never saved")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.putInt("savedPoints", points)
        super.onSaveInstanceState(savedInstanceState)
        Log.i("Points", "Points saved as $points")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        points = savedInstanceState?.getInt("savedPoints") ?: 0
        findViewById<TextView>(R.id.points_view).text = "Points: $points"
        Log.i("Points", "Points are set to $points")
    }

    override fun onStop() {
        super.onStop()
        player?.release()
    }

    fun playOrPauseMusic(view: View) {
        letsListen()
    }

    private fun letsListen() = if (player!!.isPlaying) {
        player!!.pause()
        findViewById<ImageButton>(R.id.musicIcon).setImageResource(R.drawable.play)
    } else {
        player!!.start()
        findViewById<ImageButton>(R.id.musicIcon).setImageResource( R.drawable.pause)
    }


    private fun wordGame(listView: ListView) {
        val list = ArrayList(dictionary.keys.shuffled())
        val theWord = list.first()
        val definition = dictionary[theWord]
        val subList = ArrayList(dictionary.values.shuffled()).subList(0, 4)

        if (!subList.contains(definition)) subList.add(definition)

        subList.shuffle()
        findViewById<TextView>(R.id.the_word).text = theWord
        findViewById<TextView>(R.id.points_view).setText("Points: $points")
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, subList)

        listView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, if (subList[position] == definition) "Correct!!" else "Wrong.", Toast.LENGTH_SHORT).show()
            points = if (subList[position] == definition) points + 10 else points - 5
            wordGame(listView)
        }
    }
}