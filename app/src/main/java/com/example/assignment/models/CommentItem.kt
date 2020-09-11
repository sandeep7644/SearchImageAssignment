package com.example.assignment.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class CommentItem
    (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var date_time : String,
    val date_time_in_millis : Long,
    val image_id: String,
    val comment_text: String
) : Parcelable {

}