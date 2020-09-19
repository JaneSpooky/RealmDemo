package com.example.realmdemo

import android.app.Application
import io.realm.Realm

class RealmDemoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        initRealm()
    }

    private fun initRealm() {
        Realm.init(this)
    }
}