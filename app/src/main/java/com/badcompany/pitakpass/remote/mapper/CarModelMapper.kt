package com.badcompany.pitakpass.remote.mapper

import com.badcompany.pitakpass.data.model.CarModelEntity
import com.badcompany.pitakpass.data.model.UserEntity
import com.badcompany.pitakpass.remote.model.CarModelModel
import com.badcompany.pitakpass.remote.model.UserModel
import javax.inject.Inject

/**
 * Map a [UserModel] to and from a [UserEntity] instance when data is moving between
 * this later and the Data layer
 */
open class CarModelMapper @Inject constructor() : EntityMapper<CarModelModel, CarModelEntity> {

    override fun mapToEntity(type: CarModelModel): CarModelEntity {
        return CarModelEntity(type.id, type.name)

    }

    override fun mapFromEntity(type: CarModelEntity): CarModelModel {
        return CarModelModel(type.id, type.name)
    }


}