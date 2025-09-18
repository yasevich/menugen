package com.github.yasevich.menugen.feature.upload

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.github.yasevich.menugen.R
import com.github.yasevich.menugen.ui.theme.MenuGenTheme

@Composable
fun UploadScreen(
    navigateToMenu: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UploadViewModel = hiltViewModel(),
) {
    val pickVisualMedia = rememberLauncherForActivityResult(contract = PickVisualMedia()) { uri ->
        if (uri != null) {
            viewModel.upload(uri)
            navigateToMenu(uri.toString())
        }
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(
            onClick = {
                pickVisualMedia.launch(
                    input = PickVisualMediaRequest(
                        mediaType = PickVisualMedia.ImageOnly,
                    ),
                )
            }
        ) {
            Text(text = stringResource(R.string.feature_upload_from_gallery_cta))
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun UploadScreenPreview() {
    MenuGenTheme {
        UploadScreen(navigateToMenu = {})
    }
}
