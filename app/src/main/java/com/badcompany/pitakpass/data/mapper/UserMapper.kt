package com.badcompany.pitakpass.data.mapper

import com.badcompany.pitakpass.domain.domainmodel.User
import com.badcompany.pitakpass.data.model.UserEntity
import javax.inject.Inject


/**
 * Map a [UserEntity] to and from a [User] instance when data is moving between
 * this later and the Domain layer
 */
open class UserMapper @Inject constructor(): Mapper<UserEntity, User> {

    /**
     * Map a [UserEntity] instance to a [User] instance
     */
    override fun mapFromEntity(type: UserEntity): User {
        return User(type.phoneNum, type.name, type.surname, type.role)
    }

    /**
     * Map a [User] instance to a [UserEntity] instance
     */
    override fun mapToEntity(type: User): UserEntity {
        return UserEntity(type.phoneNum, type.name, type.surname, type.role)
    }


}