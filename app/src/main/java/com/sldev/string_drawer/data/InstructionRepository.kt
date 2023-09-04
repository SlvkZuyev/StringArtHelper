package com.sldev.string_drawer.data

import android.content.SharedPreferences
import com.sldev.string_drawer.models.InstructionProgress
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import javax.inject.Inject


class InstructionRepository @Inject constructor(private val preferences: SharedPreferences) {
    private val moshi = Moshi.Builder().build()
    private val jsonAdapter: JsonAdapter<InstructionProgress> =
        moshi.adapter(InstructionProgress::class.java)

    companion object {
        const val KEY_INSTRUCTION_PROGRESS = "instruction_progress"
    }

    fun saveInstructionProgress(progress: InstructionProgress) {
        val jsonString = jsonAdapter.toJson(progress)

        preferences.edit().apply {
            putString(KEY_INSTRUCTION_PROGRESS, jsonString)
        }.apply()
    }

    fun getLastSavedInstructionProgress(): InstructionProgress{
        val jsonString = preferences.getString(KEY_INSTRUCTION_PROGRESS, "")
        if(jsonString?.isEmpty() == false){
            try {
                val progress = jsonAdapter.fromJson(jsonString)
                return progress ?: InstructionProgress()
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
        return InstructionProgress()
    }

}