package com.example.guesssong

import android.content.Context
import android.media.MediaPlayer
import com.example.guesssong.model.Song
import com.example.guesssong.model.SongOption
import org.json.JSONObject

fun loadSongs(context: Context, count: Int): List<Song> {
    val rawResources = context.resources.obtainTypedArray(R.array.song_resources)
    val songs = mutableListOf<Song>()

    repeat(rawResources.length()) { index ->
        val resourceId = rawResources.getResourceId(index, 0)
        val songKey = context.resources.getResourceEntryName(resourceId)
        val songName = context.getString(context.resources.getIdentifier(songKey, "string", context.packageName))
        val artist = songName.substringBefore(" - ")
        val title = songName.substringAfter(" - ")
        songs.add(Song(title, artist, resourceId))
    }

    rawResources.recycle()
    return songs.shuffled().take(count)
}


fun loadAnswerOptionsFromJson(context: Context): List<SongOption> {
    val jsonString = context.resources.openRawResource(R.raw.options).bufferedReader().use { it.readText() }
    val jsonObject = JSONObject(jsonString)
    val jsonArray = jsonObject.getJSONArray("songs")
    val options = mutableListOf<SongOption>()

    for (i in 0 until jsonArray.length()) {
        val songJson = jsonArray.getJSONObject(i)
        val artist = songJson.getString("artist")
        val title = songJson.getString("title")
        options.add(SongOption(artist, title))
    }

    return options
}

fun generateAnswerOptions(context: Context, correctSong: Song): List<String> {
    val allOptions = loadAnswerOptionsFromJson(context)
        .filterNot { it.artist == correctSong.artist && it.title == correctSong.title }

    val correctOption = "${correctSong.artist} - ${correctSong.title}"
    val options = mutableSetOf(correctOption)

    while (options.size < 4) {
        val randomOption = allOptions.random()
        val randomOptionDisplay = "${randomOption.artist} - ${randomOption.title}"
        options.add(randomOptionDisplay)
    }

    return options.shuffled()
}




fun playSong(context: Context, resourceId: Int): MediaPlayer {
    val mediaPlayer = MediaPlayer.create(context, resourceId)
    mediaPlayer.start()
    return mediaPlayer
}