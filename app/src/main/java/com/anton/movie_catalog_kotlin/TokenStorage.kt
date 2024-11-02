import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

interface TokenStorage {
    var apiAuthToken: String?
}

class SecureTokenStorage(context: Context): TokenStorage {
    private val prefs: EncryptedSharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "token_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences
    }

    override var apiAuthToken: String?
        get() = prefs.getString("apiAuthToken", null)
        set(value) { prefs.edit().putString("apiAuthToken", value).apply() }
}