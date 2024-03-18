package com.layon.quicksvideointeractionandroidapp.ui.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.layon.quicksvideointeractionandroidapp.ui.util.textfield.ExpandableInputSearchField


@Composable
fun topBarSearchInput() {
    Box(modifier = Modifier.background(Color.Transparent)) {
        ExpandableInputSearchField()
   }
}
