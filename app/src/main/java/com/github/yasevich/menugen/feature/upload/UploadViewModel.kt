package com.github.yasevich.menugen.feature.upload

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.yasevich.menugen.data.UploadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val uploadRepository: UploadRepository,
) : ViewModel() {

    fun upload(uri: Uri) {
        viewModelScope.launch {
            uploadRepository.uploadMenu(uri)
        }
    }
}
