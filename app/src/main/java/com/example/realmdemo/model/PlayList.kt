package com.example.realmdemo.model

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class PlayList: RealmObject() {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()

    var name: String = ""

    var mediaIds: String = ""

    var createdAt: Date = Date()

    var deletedAt: Date? = null

    companion object {

        fun insertOrUpdate(data: PlayList) {
            Realm.getDefaultInstance().executeTransaction {
                it.insertOrUpdate(data)
            }
        }

        fun findAll(): List<PlayList> =
            Realm.getDefaultInstance().use { realm ->
                realm.where(PlayList::class.java)
                    .isNull(PlayList::deletedAt.name)
                    .findAll()
                    .let { realm.copyFromRealm(it) }
            }

        fun findBy(id: String): PlayList? =
            Realm.getDefaultInstance().use { realm ->
                realm.where(PlayList::class.java)
                    .equalTo(PlayList::id.name, id) // PlayList::id.name = "id"
                    .isNull(PlayList::deletedAt.name)
                    .findFirst()
                    ?.let { realm.copyFromRealm(it) }
            }

        fun delete(id: String) {
            findBy(id)?.also {
                insertOrUpdate(it.apply {
                    deletedAt = Date()
                })
            }
        }
    }
}