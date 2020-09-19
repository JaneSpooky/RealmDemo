package com.example.realmdemo.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Media: RealmObject() {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()

    var name: String = ""

    var uri: String = ""
}