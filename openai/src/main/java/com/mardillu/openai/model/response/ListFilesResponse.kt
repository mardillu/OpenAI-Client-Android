package com.mardillu.openai.model.response

data class ListFilesResponse(
    val data: List<FileObject>,
    val `object`: String
)
