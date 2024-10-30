/**
 * AGPL-3.0-licensed
 * Copyright (C) GKI Salatiga 2024
 * Written by Samarthya Lykamanuella (github.com/groaking)
 * ---
 * This class allows to extract zip archives.
 */

package org.gkisalatiga.plus.lib

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat.startActivityForResult
import org.gkisalatiga.plus.global.GlobalCompanion
import java.io.OutputStream
import java.net.UnknownHostException
import java.util.concurrent.Executors


/**
 * SOURCE: https://www.techotopia.com/index.php/A_Kotlin_Android_Storage_Access_Framework_Example
 */
class GallerySaver {
    fun saveImageFromURL(ctx: Context, imageURL: String, imageName: String) {
        GlobalCompanion.targetGoogleDrivePhotoURL = imageURL
        GlobalCompanion.targetSaveFilename = imageName

        // Create a new intent.
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)

        // Open the SAF dialog.
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_TITLE, imageName)
        startActivityForResult(ctx as Activity, intent, GlobalCompanion.GALLERY_SAVER_CODE, null)
        Logger.logTest({}, "Is this block executed? (2)")
    }

    fun onSAFPathReceived(outputStream: OutputStream) {
        // Avoid "NetworkOnMainThread" exception.
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {

            // Display the download progress circle.
            GlobalCompanion.showScreenGaleriViewDownloadProgress.value = true

            try {
                // Opening the file download stream.
                Logger.logDump({}, GlobalCompanion.targetGoogleDrivePhotoURL)
                val streamIn = java.net.URL(GlobalCompanion.targetGoogleDrivePhotoURL).openStream()

                // Coverting input stream (bytes) to string.
                // SOURCE: http://stackoverflow.com/questions/49467780/ddg#49468129
                val decodedData: ByteArray = streamIn.readBytes()

                // Writing into the file.
                outputStream.flush()
                outputStream.write(decodedData)
                outputStream.close()

                // Show some successful alert.
                GlobalCompanion.txtScreenGaleriViewAlertDialogTitle = "File Terunduh!"
                GlobalCompanion.txtScreenGaleriViewAlertDialogSubtitle = "Berhasil mengunduh \"${GlobalCompanion.targetSaveFilename}\""
                GlobalCompanion.showScreenGaleriViewAlertDialog.value = true

            } catch (e: UnknownHostException) {
                Logger.log({}, "Network unreachable when downloading the gallery data: $e", LoggerType.ERROR)

                // Show some failure alert.
                GlobalCompanion.txtScreenGaleriViewAlertDialogTitle = "Gagal Mengunduh!"
                GlobalCompanion.txtScreenGaleriViewAlertDialogSubtitle = "Koneksi terputus. Silahkan periksa sambungan internet perangkat Anda."
                GlobalCompanion.showScreenGaleriViewAlertDialog.value = true
            }

            // Break free from this thread.
            GlobalCompanion.showScreenGaleriViewDownloadProgress.value = false
            executor.shutdown()
        }
    }
}