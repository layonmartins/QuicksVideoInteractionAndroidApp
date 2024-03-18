package com.layon.quicksvideointeractionandroidapp.ui.util.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Composable functions to add an TextField and a Search Button
 * to show and hide the TextField visibility
 */
@Composable
fun ExpandableInputSearchField() {
    var showTextField by remember { mutableStateOf(false)}
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        horizontalArrangement = Arrangement.End
    ) {
        OutLineTextFieldSample(showTextField)
        SmallFloatingActionButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = {
                showTextField = !showTextField
                      },
            shape = CircleShape,
        ) {
            Icon(Icons.Filled.Search, "Seach")
        }
    }
}

@Composable
fun OutLineTextFieldSample(isVisible: Boolean) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val focusRequester = remember { FocusRequester() }

    AnimatedVisibility(isVisible) {
        OutlinedTextField(
            modifier = Modifier
                .padding(end = 10.dp)
                .fillMaxWidth(0.85F)
                .background(Color.Black.copy(alpha = 0.4f))
                .focusRequester(focusRequester),
            value = text,
            label = { Text(text = "Search Quicks") },
            textStyle = TextStyle(color = Color.White),
            onValueChange = {
                text = it
            },
            singleLine = true
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

@Preview
@Composable
fun seachInputFieldPreview() {
    ExpandableInputSearchField()
}