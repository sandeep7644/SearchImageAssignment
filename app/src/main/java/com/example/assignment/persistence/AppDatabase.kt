package com.example.assignment.persistence


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignment.models.CommentItem
import com.example.assignment.models.ImageItem
import com.example.assignment.models.ImageRemotePageKeys


@Database(entities = [ImageItem::class, ImageRemotePageKeys::class,CommentItem::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

  abstract fun imageDao(): ImageDao
  abstract fun remoteImagePageKeysDao(): ImageRemotePageKeysDao
  abstract fun commentDao(): CommentDao
}
