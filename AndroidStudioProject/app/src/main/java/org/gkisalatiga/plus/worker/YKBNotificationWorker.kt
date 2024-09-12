/**
 * AGPL-3.0-licensed
 * Copyright (C) GKI Salatiga 2024
 * Written by Samarthya Lykamanuella (github.com/groaking)
 */

package org.gkisalatiga.plus.worker

import android.content.Context
import android.util.Log
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.gkisalatiga.plus.global.GlobalSchema
import org.gkisalatiga.plus.lib.Tags
import org.gkisalatiga.plus.services.NotificationService
import org.gkisalatiga.plus.services.WorkScheduler

/**
 * Creates a specific-action worker that gets triggered by a WorkManager.
 * SOURCE: https://medium.com/@ifr0z/workmanager-notification-date-and-time-pickers-aad1d938b0a3
 */
class YKBNotificationWorker(private val context: Context, private val params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        if (GlobalSchema.DEBUG_ENABLE_LOG_CAT_WORKER) Log.d("Groaker-Worker", "[YKBNotificationWorker.doWork] Carrying out the YKBNotificationWorker ...")

        // Perform the work.
        NotificationService.showYKBHarianNotification(context)

        // Prevents multiple firings of work trigger. (?)
        // WorkManager.getInstance(context).cancelAllWorkByTag(Tags.NAME_YKB_WORK)

        if (GlobalSchema.DEBUG_ENABLE_LOG_CAT_WORKER) Log.d("Groaker-Worker", "[YKBNotificationWorker.doWork] What do we have here? ${params.tags}")

        // Carry out the rescheduling.
        WorkScheduler.scheduleYKBReminder(context)

        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        if (GlobalSchema.DEBUG_ENABLE_LOG_CAT_WORKER) Log.d("Groaker-Worker", "[YKBNotificationWorker.onStopped] This process has been stopped.")
    }
}
