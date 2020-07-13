package com.badcompany.pitakpass.data.mapper

import com.badcompany.pitakpass.data.model.UserCredentialsEntity
import com.badcompany.pitakpass.domain.domainmodel.User
import com.badcompany.pitakpass.data.model.UserEntity
import com.badcompany.pitakpass.domain.domainmodel.UserCredentials
import javax.inject.Inject


/**
 * Map a [UserEntity] to and from a [User] instance when data is moving between
 * this later and the Domain layer
 */
open class UserCredentialsMapper @Inject constructor(): Mapper<UserCredentialsEntity, UserCredentials> {

    /**
     * Map a [UserEntity] instance to a [User] instance
     */
    override fun mapFromEntity(type: UserCredentialsEntity): UserCredentials {
        return UserCredentials(type.phoneNum, type.password)
    }

    /**
     * Map a [User] instance to a [UserEntity] instance
     */
    override fun mapToEntity(type: UserCredentials): UserCredentialsEntity {
        return UserCredentialsEntity(type.phoneNum, type.password)
    }


}