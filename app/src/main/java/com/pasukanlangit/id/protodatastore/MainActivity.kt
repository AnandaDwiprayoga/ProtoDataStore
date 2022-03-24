package com.pasukanlangit.id.protodatastore

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.dataStore
import com.pasukanlangit.id.protodatastore.ui.theme.ProtoDataStoreTheme
import kotlinx.coroutines.launch

val Context.dataStore by dataStore("app-settings.json", AppSettingsSerializer)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProtoDataStoreTheme {
                val appSetting = dataStore.data.collectAsState(initial = AppSettings()).value
                val scope = rememberCoroutineScope()
            
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val languages = Language.values()
                    languages.map { language ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = appSetting.language == language,
                                onClick = {
                                    scope.launch {
                                        updateLanguage(language)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = language.toString(),
                                modifier = Modifier.clickable(
                                    onClick = {
                                        scope.launch {
                                            updateLanguage(language)
                                        }
                                    }
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }

    private suspend fun updateLanguage(language: Language) {
        dataStore.updateData {
            it.copy(
                language = language
            )
        }
    }


}
