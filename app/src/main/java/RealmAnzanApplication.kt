package app.sakai.tororoimo.anzan

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmAnzanApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfig)
    }
}