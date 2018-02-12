/*
 * Copyright (C) 2018 Oleg Kan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simplaapliko.learn.firebase.remoteconfig

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.simplaapliko.learn.firebase.R
import com.simplaapliko.learn.firebase.repository.FirebaseSettingsRepository
import com.simplaapliko.learn.firebase.repository.SettingsRepository
import kotlinx.android.synthetic.main.activity_remote_config.*

class RemoteConfigActivity : AppCompatActivity() {

    private val listener: OnCompleteListener<Void> =
            OnCompleteListener { task ->
                onRefreshCompleted()
                if (task.isSuccessful) {
                    Toast.makeText(this@RemoteConfigActivity, "Fetch Succeeded",
                            Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@RemoteConfigActivity, "Fetch Failed",
                            Toast.LENGTH_SHORT).show()
                }
            }

    private var settings: SettingsRepository = FirebaseSettingsRepository(listener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_config)
        bindViews()
        bindEvents()
    }

    private fun bindViews() {
        val isConfigEnabled = settings.isRemoteConfigEnabled();
        remote_config_enabled.isChecked = isConfigEnabled
    }

    private fun bindEvents() {
        refresh.setOnClickListener({
            onRefreshClick()
        })
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE;
    }

    private fun hideProgress() {
        progress.visibility = View.GONE;
    }

    private fun onRefreshClick() {
        showProgress()
        settings = FirebaseSettingsRepository(listener)
    }

    private fun onRefreshCompleted() {
        hideProgress()
        bindViews()
    }
}
