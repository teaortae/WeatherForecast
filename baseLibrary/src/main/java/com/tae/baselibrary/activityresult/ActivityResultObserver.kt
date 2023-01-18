package com.tae.baselibrary.activityresult

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

open class ActivityResultObserver(
    private val registry: ActivityResultRegistry,
    private val result: ActivityResultService
) :
    DefaultLifecycleObserver {
    lateinit var getContent: ActivityResultLauncher<Intent>

    override fun onCreate(owner: LifecycleOwner) {
        getContent = registry.register("NJob", owner, StartActivityForResult()) {
            result.onActivityResult(it)
        }
    }
}

interface ActivityResultService {
    fun onActivityResult(activityResult: ActivityResult)
}
