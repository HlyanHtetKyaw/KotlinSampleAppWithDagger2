package com.example.testforcodigo.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "wonder_table")
class WonderDbData {

    @PrimaryKey
    var id: Long = 0

    var location: String? = null

    var description: String? = null

    var image: String? = null

    var lattitude: String? = null

    var longtitude: String? = null

}