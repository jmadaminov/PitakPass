package com.badcompany.pitakpass.domain.model

data class PassengerOffer(val postId: Long,
                          val price: Int? = null,
                          val message: String? = null,
                          val seat: Int? = null,
                          val repliedPostId: Int? = null)