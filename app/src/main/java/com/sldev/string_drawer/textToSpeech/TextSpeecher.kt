package com.sldev.string_drawer.textToSpeech

import android.content.Context
import android.speech.tts.TextToSpeech
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class TextSpeaker @Inject constructor(@ApplicationContext context: Context) : TextToSpeech.OnInitListener {
    private val textToSpeech: TextToSpeech = TextToSpeech(context, this)

    override fun onInit(status: Int) {
        if (status != TextToSpeech.SUCCESS) {
            return
        }

        textToSpeech.language = Locale.ENGLISH
        textToSpeech.setPitch(0.9f)
        textToSpeech.setSpeechRate(0.9f)
    }

    fun speakOut(text: String) {
        try {
            textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, "")
        } catch (e: Exception){
            e.printStackTrace()
        }
    }


}