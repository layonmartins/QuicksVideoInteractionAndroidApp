package com.layon.quicksvideointeractionandroidapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//TODO remove unnecessary fields
@Serializable
data class QuickVideosListModel(
    @SerialName(value = "page") var page: Int? = null,
    @SerialName(value = "per_page") var perPage: Int? = null,
    @SerialName(value = "total_results") var totalResults: Int? = null,
    @SerialName(value = "url") var url: String? = null,
    @SerialName(value = "videos") var videos: ArrayList<Videos> = arrayListOf()
)

@Serializable
data class User(
    @SerialName(value = "id") var id: Int? = null,
    @SerialName(value = "name") var name: String? = null,
    @SerialName(value = "url") var url: String? = null
)

@Serializable
data class VideoFiles(
    @SerialName(value = "id") var id: Int? = null,
    @SerialName(value = "quality") var quality: String? = null,
    @SerialName(value = "file_type") var fileType: String? = null,
    @SerialName(value = "width") var width: Int? = null,
    @SerialName(value = "height") var height: Int? = null,
    @SerialName(value = "link") var link: String? = null
) : Comparable<VideoFiles> {
    override fun compareTo(other: VideoFiles): Int {
        //TODO add comparable to with fps, width and height
        if (this.quality == "sd" && other.quality == "hd") return -1
        if (this.quality == "sd" && other.quality == "ud") return -1
        if (this.quality == "hd" && other.quality == "sd") return 1
        if (this.quality == "hd" && other.quality == "uhd") return -1
        if (this.quality == "uhd" && other.quality == "sd") return 1
        if (this.quality == "uhd" && other.quality == "hd") return 1
        return 1
    }
}

@Serializable
data class VideoPictures(
    @SerialName(value = "id") var id: Int? = null,
    @SerialName(value = "picture") var picture: String? = null,
    @SerialName(value = "nr") var nr: Int? = null
)

@Serializable
data class Videos(
    @SerialName(value = "id") var id: Int? = null,
    @SerialName(value = "width") var width: Int? = null,
    @SerialName(value = "height") var height: Int? = null,
    @SerialName(value = "url") var url: String? = null,
    @SerialName(value = "image") var image: String? = null,
    @SerialName(value = "duration") var duration: Int? = null,
    @SerialName(value = "full_res") var fullRes: Int? = null,
    @SerialName(value = "avg_color") var avgColor: Boolean? = null,
    @SerialName(value = "tags") var tags: ArrayList<String> = arrayListOf(),
    @SerialName(value = "user") var user: User? = User(),
    @SerialName(value = "video_files") var videoFiles: ArrayList<VideoFiles> = arrayListOf(),
    @SerialName(value = "video_pictures") var videoPictures: ArrayList<VideoPictures> = arrayListOf()
)