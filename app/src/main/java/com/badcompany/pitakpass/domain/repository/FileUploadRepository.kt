package com.badcompany.pitakpass.domain.repository

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.domainmodel.Car
import com.badcompany.pitakpass.domain.domainmodel.CarColorBody
import com.badcompany.pitakpass.domain.domainmodel.CarModelBody
import com.badcompany.pitakpass.domain.domainmodel.PhotoBody
import java.io.File

interface FileUploadRepository {

    suspend fun uploadPhoto(file: File): ResultWrapper<PhotoBody>

}