package com.example.assignment.persistence


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment.models.ImageRemotePageKeys

@Dao
interface ImageRemotePageKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(imageRemotePageKey: List<ImageRemotePageKeys>)

    @Query("SELECT * FROM ImageRemotePageKeys WHERE repoId = :repoId")
    suspend fun remoteKeysRepoId(repoId: String): ImageRemotePageKeys?

    @Query("DELETE FROM ImageRemotePageKeys")
    suspend fun clearRemoteKeys()
}

