package com.layon.quicksvideointeractionandroidapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.layon.quicksvideointeractionandroidapp.R

/**
 * Composable functions to add an Views (Text, Buttons, Imagens etc)
 * in front of the video Scree to user interactions
 */

/**
 * Composable function to create Personalized FloatingActionButton
 */
@Composable
fun GenericInteractionButton(
    icon: @Composable () -> Unit,
    text: String,
    onClick: () -> Unit = {}
) {
    Column {
        SmallFloatingActionButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { onClick() },
            shape = CircleShape,
        ) {
            icon()
        }
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = text,
            color = Color.White,
            fontSize = 13.sp
        )
    }
}

/**
 * Composable function to create and group FloatingActionButton
 * as 1 - Favorite, 2 - Comments and 3 - Shared buttons
 */
@Composable
fun groupButtonQuickInteraction() {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .padding(bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        GenericInteractionButton(
            icon = { Icon(Icons.Filled.Favorite, "Favorite") },
            text = "10,5 mil"
        ) //FavoriteButton
        GenericInteractionButton(
            icon = { Icon(Icons.Filled.List, "Comments") },
            text = "3 mil"
        ) // CommentsButton
        GenericInteractionButton(
            icon = { Icon(Icons.Filled.Share, "Share") },
            text = "1 mil"
        ) // ShareButton
    }
}

/**
 * Composable function to create and group users info
 * as Photo and Profile name
 */
@Composable
fun groupUserInfo() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(width = 40.dp, height = 40.dp)
                .border(2.dp, Color.White, CircleShape),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.jovem_city_park),
            contentDescription = "audio album"
        )
        Text(
            text = "@UserProfileName",
            maxLines = 1,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
        OutlinedButton(
            onClick = { },
            border = BorderStroke(1.dp, Color.White),
            modifier = Modifier.size(width = 80.dp, height = 30.dp),
            shape = RoundedCornerShape(50), // = 50% percent
            // or shape = CircleShape
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
        ) {
            Text(text = "Seguir", fontSize = 11.sp)
        }
    }
}


/**
 * Composable function to create and group audio video references
 * as Music name and Album photo
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun groupAudioQuickInteraction() {
    Row(
        Modifier.background(Color.Black.copy(alpha = 0.5f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.baseline_music_note_24),
            contentDescription = "audio",
            tint = Color.White
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(0.88f)
                .basicMarquee(),
            text = LoremIpsum().values.first().take(100),
            maxLines = 1,
            color = Color.White,
            fontSize = 13.sp
        )
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(20))
                .size(width = 40.dp, height = 40.dp),
            painter = painterResource(id = R.drawable.ncs_album),
            contentDescription = "audio album"
        )
    }
}

/**
 * Composable function that groups all views interactions on the screen fillMaxSize()
 *  (Text, Buttons, Imagens etc)
 */
@Composable
fun interactionsVideoLayout() {
    Box(modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier.align(Alignment.TopCenter)) {
            ExpandableInputSearchField()
        }

        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            groupButtonQuickInteraction()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(10.dp)
                .padding(bottom = 100.dp),
        ) {
            groupUserInfo()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.BottomStart)
                .padding(10.dp)
                .padding(bottom = 40.dp),
        ) {
            ExpandableText(
                fontSize = 14.sp,
                text = LoremIpsum().values.first().take(1000)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(10.dp)
        ) {
            groupAudioQuickInteraction()
        }

    }
}


/**
 * Show the allviews interactions that will be shown
 * in front of the video
 */
@Preview
@Composable
fun interactionsVideoLayoutPreview() {
    interactionsVideoLayout()
}