package com.example.assignment.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class ImageItem(

	@field:SerializedName("id")
	val id: String ,

	@PrimaryKey(autoGenerate = true)
	val pk: Int ,


	@field:SerializedName("title")
	val title: String ,


	@field:SerializedName("datetime")
	val datetime: Int,

	@field:SerializedName("views")
	val views: Int,

	@field:SerializedName("link")
	var link: String?=null,

	@field:SerializedName("ups")
	val ups: Int,

	@field:SerializedName("downs")
	val downs: Int,

	@field:SerializedName("points")
	val points: Int,

	@field:SerializedName("score")
	val score: Int,

	@field:SerializedName("images_count")
	val imagesCount: Int,


	var imageWidth : Int? =null,

	var imageHeight : Int ? =null

) : Parcelable{
	@Ignore
	@field:SerializedName("images")
	val images: List<ImagesItem>? = null
}