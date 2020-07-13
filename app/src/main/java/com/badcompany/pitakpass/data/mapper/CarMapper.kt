package com.badcompany.pitakpass.data.mapper

import com.badcompany.pitakpass.data.model.CarEntity
import com.badcompany.pitakpass.data.model.PhotoEntity
import com.badcompany.pitakpass.data.model.UserEntity
import com.badcompany.pitakpass.domain.domainmodel.Car
import com.badcompany.pitakpass.domain.domainmodel.PhotoBody
import com.badcompany.pitakpass.domain.domainmodel.User
import javax.inject.Inject


/**
 * Map a [UserEntity] to and from a [User] instance when data is moving between
 * this later and the Domain layer
 */
open class CarMapper @Inject constructor() : Mapper<CarEntity, Car> {

    /**
     * Map a [UserEntity] instance to a [User] instance
     */
    override fun mapFromEntity(type: CarEntity): Car {

        val images = arrayListOf<PhotoBody>()
        type.imageList?.forEach { images.add(PhotoBody(it.id)) }

        return Car(type.id,
                   type.modelId,
                   type.imageId,
                   type.fuelType,
                   type.colorId,
                   type.carNumber,
                   type.carYear,
                   type.airConditioner!!,
                   images)
    }

    /**
     * Map a [User] instance to a [UserEntity] instance
     */
    override fun mapToEntity(type: Car): CarEntity {
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


}