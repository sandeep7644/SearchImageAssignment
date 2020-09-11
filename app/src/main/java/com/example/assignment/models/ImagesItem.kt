package com.example.assignment.models

import com.google.gson.annotations.SerializedName

data class ImagesItem(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: Any? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("datetime")
	val datetime: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("link")
	val link: String

)