package com.layon.quicksvideointeractionandroidapp.util

import com.layon.quicksvideointeractionandroidapp.model.VideoFiles

fun getBestVideoFileQualityLink(videoFiles: ArrayList<VideoFiles>) : String {
    var video : VideoFiles? = videoFiles.first()
    videoFiles.forEach { next ->
        if (video?.compareTo(next) == -1 && video?.link != null) {
            video = next
        }
    }
    return video?.link!!
}