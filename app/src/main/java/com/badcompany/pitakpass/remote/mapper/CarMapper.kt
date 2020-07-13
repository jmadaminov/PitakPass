package com.badcompany.pitakpass.remote.mapper

import com.badcompany.pitakpass.data.model.CarDetailsEntity
import com.badcompany.pitakpass.data.model.CarEntity
import com.badcompany.pitakpass.data.model.PhotoEntity
import com.badcompany.pitakpass.data.model.UserEntity
import com.badcompany.pitakpass.remote.model.CarDetails
import com.badcompany.pitakpass.remote.model.CarModel
import com.badcompany.pitakpass.remote.model.PhotoUploadModel
import com.badcompany.pitakpass.remote.model.UserModel
import javax.inject.Inject

/**
 * Map a [UserModel] to and from a [UserEntity] instance when data is moving between
 * this later and the Data layer
 */
open class CarMapper @Inject constructor() : EntityMapper<CarModel, CarEntity> {

    override fun mapToEntity(type: CarModel): CarEntity {

        val images = arrayListOf<PhotoEntity>()
        type.imageList?.forEach { images.add(PhotoEntity(it.id)) }

        return CarEntity(type.id,
                         type.modelId,
                         type.imageId,
                         type.fuelType,
                         type.colorId,
                         type.carNumber,
                         type.carYear,
                         type.airConditioner,
                         images)

    }

    override fun mapFromEntity(type: CarEntity): CarModel {
        val images = arrayListOf<PhotoUploadModel>()
        type.imageList?.forEach { images.add(PhotoUploadModel(it.id)) }
        return CarModel(type.id,
                        type.modelId,
                        type.imageId,
                        type.fuelType,
                        type.colorId,
                        type.carNumber,
                        type.carYear,
                        type.airConditioner,
                        images)
    }


}