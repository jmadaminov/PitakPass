package com.badcompany.pitakpass.remote.mapper

import com.badcompany.pitakpass.data.model.CarColorEntity
import com.badcompany.pitakpass.data.model.UserEntity
import com.badcompany.pitakpass.remote.model.CarColorModel
import com.badcompany.pitakpass.remote.model.UserModel
import javax.inject.Inject

/**
 * Map a [UserModel] to and from a [UserEntity] instance when data is moving between
 * this later and the Data layer
 */
open class CarColorMapper @Inject constructor() : EntityMapper<CarColorModel, CarColorEntity> {

    override fun mapToEntity(type: CarColorModel): CarColorEntity {
        return CarColorEntity(type.id, type.hex, type.nameEn, type.nameUz, type.nameRu)

    }

    override fun mapFromEntity(type: CarColorEntity): CarColorModel {
        return CarColorModel(type.id, type.hex, type.nameEn, type.nameUz, type.nameRu)
    }


}