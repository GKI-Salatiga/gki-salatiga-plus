/**
 * AGPL-3.0-licensed
 * Copyright (C) GKI Salatiga 2024
 * Written by Samarthya Lykamanuella (github.com/groaking)
 *
 * ---
 * Manages the application's database and information retrieval, whether stored in the APK
 * or downloaded online.
 */

package org.gkisalatiga.plus.db

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import org.gkisalatiga.plus.R
import org.gkisalatiga.plus.lib.Logger
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class Main(private val ctx: Context) {

    private var _parsedJSONString: String = ""

    /**
     * Loads a given JSON file (in the phone's absolute path, not the app's Android resource manager),
     * and parse them into string inside the class.
     * SOURCE: https://stackoverflow.com/a/45202002
     */
    private fun loadJSON(absolutePathToJSON: String) {
        // SOURCE: https://stackoverflow.com/a/45202002
        val file = File(absolutePathToJSON)
        val inputAsString = FileInputStream(file).bufferedReader().use { it.readText() }

        this._parsedJSONString = inputAsString
    }

    /**
     * Experimental; please only use this function in debug mode.
     * This function returns the parsed JSON's entire string content.
     * Could be dangerous.
     */
    fun getRawDumped(): String {
        return this._parsedJSONString
    }

    /**
     * Returns the attribution JSON file of this applications,
     * which open source works are used in this app.
     */
    fun getAttributions(): JSONObject {
        // Loading the local JSON file.
        // SOURCE: https://stackoverflow.com/a/2856501
        // SOURCE: https://stackoverflow.com/a/39500046
        val input: InputStream = ctx.resources.openRawResource(R.raw.app_attributions_open_source)
        val inputAsString: String = input.bufferedReader().use { it.readText() }

        // Return the fallback JSONObject, and then navigate to the "data" node.
        return JSONObject(inputAsString)
    }

    /**
     * Returns the fallback JSONObject stored and packaged within the app.
     * This is useful especially when the app has not yet loaded the refreshed JSON metadata
     * from the internet yet.
     */
    fun getFallbackMainData(): JSONObject {
        // Loading the local JSON file.
        // SOURCE: https://stackoverflow.com/a/2856501
        // SOURCE: https://stackoverflow.com/a/39500046
        val input: InputStream = ctx.resources.openRawResource(R.raw.fallback_main)
        val inputAsString: String = input.bufferedReader().use { it.readText() }

        // Return the fallback JSONObject, and then navigate to the "data" node.
        return JSONObject(inputAsString).getJSONObject("data")
    }

    fun getFallbackMainMetadata(): JSONObject {
        // Loading the local JSON file.
        // SOURCE: https://stackoverflow.com/a/2856501
        // SOURCE: https://stackoverflow.com/a/39500046
        val input: InputStream = ctx.resources.openRawResource(R.raw.fallback_main)
        val inputAsString: String = input.bufferedReader().use { it.readText() }

        // Return the fallback JSONObject, and then navigate to the "data" node.
        return JSONObject(inputAsString).getJSONObject("meta")
    }

    /**
     * Initializes the main data and assign the global variable that handles it.
     */
    fun initFallbackGalleryData() {
        MainCompanion.jsonRoot = getFallbackMainData()
    }

    /**
     * Parse the specified JSON string and serialize it, then
     * return a JSON object that reads the database's main data.
     * SOURCE: https://stackoverflow.com/a/50468095
     * ---
     * Assumes the JSON metadata has been initialized by the Downloader class.
     * Please run Downloader().initMetaData() before executing this function.
     */
    fun getMainData(): JSONObject {
        // Determines if we have already downloaded the JSON file.
        val JSONExists = File(MainCompanion.absolutePathToJSONFile).exists()

        // Load the downloaded JSON.
        // Prevents error-returning when this function is called upon offline.
        if (MainCompanion.mutableIsDataInitialized.value || JSONExists) {
            this.loadJSON(MainCompanion.absolutePathToJSONFile)

            // Debugger logging.
            Logger.logTest({}, "Reading local/downloaded JSON main data ...")

            // The JSONObject main data.
            val mainData = JSONObject(this._parsedJSONString).getJSONObject("data")
            MainCompanion.jsonRoot = mainData
            return mainData
        } else {
            Logger.logTest({}, "Reverting to the fallback data of the JSON schema ...")
            return getFallbackMainData()
        }

    }

    fun getMainMetadata(): JSONObject {
        // Determines if we have already downloaded the JSON file.
        val JSONExists = File(MainCompanion.absolutePathToJSONFile).exists()

        // Load the downloaded JSON.
        // Prevents error-returning when this function is called upon offline.
        if (MainCompanion.mutableIsDataInitialized.value || JSONExists) {
            this.loadJSON(MainCompanion.absolutePathToJSONFile)
            return JSONObject(this._parsedJSONString).getJSONObject("meta")
        } else {
            return getFallbackMainMetadata()
        }

    }

}

class MainCompanion : Application() {
    companion object {
        const val REMOTE_JSON_SOURCE = "https://raw.githubusercontent.com/gkisalatiga/gkisplus-data/main/v2/data/gkisplus-main.min.json"

        /* Back-end mechanisms. */
        var absolutePathToJSONFile: String = String()
        val mutableIsDataInitialized = mutableStateOf(false)
        val savedFilename = "gkisplus-main-v2.json"

        /* The JSON object that will be accessed by screens. */
        var jsonRoot: JSONObject? = null
    }
}