package dev.csse.jcisne23.gurucalc.ui
import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.calcDataStore by preferencesDataStore(name = "calc_prefs")
val INPUT_KEY = stringPreferencesKey("input")
val OUTPUT_KEY = stringPreferencesKey("output")

class CalcRepository(private val context: Context) {

    val inputFlow: Flow<String> = context.calcDataStore.data.map { prefs ->
        prefs[INPUT_KEY] ?: ""
    }

    val outputFlow: Flow<String> = context.calcDataStore.data.map { prefs ->
        prefs[OUTPUT_KEY] ?: ""
    }

    suspend fun saveInput(input: TextFieldValue) {
        context.calcDataStore.edit { prefs ->
            prefs[INPUT_KEY] = input.toString()
        }
    }

    suspend fun saveOutput(output: String) {
        context.calcDataStore.edit { prefs ->
            prefs[OUTPUT_KEY] = output
        }
    }
}