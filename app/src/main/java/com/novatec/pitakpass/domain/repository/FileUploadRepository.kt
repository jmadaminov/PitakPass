package com.novatec.pitakpass.domain.repository

import com.novatec.pitakpass.util.ResultWrapper
import com.novatec.pitakpass.domain.model.PhotoBody
import java.io.File

interface FileUploadRepository {

    suspend fun uploadPhoto(file: File): ResultWrapper<PhotoBody>

}