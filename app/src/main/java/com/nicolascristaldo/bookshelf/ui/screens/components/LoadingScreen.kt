package com.nicolascristaldo.bookshelf.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.nicolascristaldo.bookshelf.R

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(dimensionResource(id = R.dimen.loading_image_size)),
        painter = painterResource(R.drawable.ic_loading),
        contentDescription = null
    )
}