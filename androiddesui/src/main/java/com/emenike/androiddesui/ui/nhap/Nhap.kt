package com.emenike.androiddesui.ui.nhap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Nhap(
    modifier: Modifier = Modifier,
    passwords: List<String> = arrayListOf(),
    onCopyText: () -> Unit,
    isSelectedDelete: Boolean = true,
) {
    // TODO UI Rendering
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(passwords) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    onClick = onCopyText,
                    shape = RectangleShape
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                        Text(modifier = Modifier.padding(10.dp), text = it)
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun PreviewNek() {
    MaterialTheme {
        Nhap(
            modifier = Modifier.fillMaxSize(),
            passwords = listOf("aosiguaosiudg", "asoiguqoweweeee"),
            onCopyText = {/* no-op */ }
        )
    }
}