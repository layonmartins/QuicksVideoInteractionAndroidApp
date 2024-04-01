package com.layon.quicksvideointeractionandroidapp.util

import com.layon.quicksvideointeractionandroidapp.model.VideoFiles
import com.layon.quicksvideointeractionandroidapp.ui.screen.viewmodel.HomeScreenViewModel
import junit.framework.TestCase
import org.junit.Test

class GetBestVideoFileQualityLinkTest {

    @Test
    fun getBestVideoFileQualityLinkTest_Success() {

        val videoFileList = arrayListOf(
            VideoFiles(id = 1, quality = "hd", link = "link1"),
            VideoFiles(id = 2, quality = "sd", link = "link2"),
            VideoFiles(id = 3, quality = "hd", link = "link3"),
            VideoFiles(id = 4, quality = "uhd", link = "link4"),
            VideoFiles(id = 5, quality = "uhd", link = "link5"),
        )

        HomeScreenViewModel.Companion.apply {
            TestCase.assertEquals("link4", getBestVideoFileQualityLink(videoFileList))

            videoFileList[3].quality = "sd"
            /**
            VideoFiles(id = 1, quality = "hd", link = "link1"),
            VideoFiles(id = 2, quality = "sd", link = "link2"),
            VideoFiles(id = 3, quality = "hd", link = "link3"),
            VideoFiles(id = 4, quality = "sd2", link = "link4"),
            VideoFiles(id = 5, quality = "uhd", link = "link5")
             */
            TestCase.assertEquals("link5", getBestVideoFileQualityLink(videoFileList))

            videoFileList[4].quality = "sd"
            /**
            VideoFiles(id = 1, quality = "hd", link = "link1"),
            VideoFiles(id = 2, quality = "sd", link = "link2"),
            VideoFiles(id = 3, quality = "hd", link = "link3"),
            VideoFiles(id = 4, quality = "sd2", link = "link4"),
            VideoFiles(id = 5, quality = "sd3", link = "link5")
             */
            TestCase.assertEquals("link1", getBestVideoFileQualityLink(videoFileList))

            videoFileList[0].quality = "sd"
            /**
            VideoFiles(id = 1, quality = "sd", link = "link1"),
            VideoFiles(id = 2, quality = "sd", link = "link2"),
            VideoFiles(id = 3, quality = "hd", link = "link3"),
            VideoFiles(id = 4, quality = "sd", link = "link4"),
            VideoFiles(id = 5, quality = "sd", link = "link5")
             */
            TestCase.assertEquals("link3", getBestVideoFileQualityLink(videoFileList))

            videoFileList[2].quality = ""
            /**
            VideoFiles(id = 1, quality = "sd", link = "link1"),
            VideoFiles(id = 2, quality = "sd", link = "link2"),
            VideoFiles(id = 3, quality = "", link = "link3"),
            VideoFiles(id = 4, quality = "sd", link = "link4"),
            VideoFiles(id = 5, quality = "sd", link = "link5")
             */
            TestCase.assertEquals("link1", getBestVideoFileQualityLink(videoFileList))

        }

    }

}