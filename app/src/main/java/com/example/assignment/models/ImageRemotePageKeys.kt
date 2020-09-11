package com.example.assignment.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageRemotePageKeys(
        @PrimaryKey val repoId: String,
        val prevKey: Int?,
        val nextKey: Int?
)
