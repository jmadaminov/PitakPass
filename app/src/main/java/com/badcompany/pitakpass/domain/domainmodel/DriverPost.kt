package com.badcompany.pitakpass.domain.domainmodel

import com.badcompany.pitakpass.util.Constants

data class DriverPost(val id: Long? = null,
                      val from: Place,
                      val to: Place,
                      val price: Int,
                      val departureDate: String,
                      val finishedDate: String?=null,
                      val timeFirstPart: Boolean,
                      val timeSecondPart: Boolean,
                      val timeThirdPart: Boolean,
                      val timeFourthPart: Boolean,
                      val carId: Long?=null,
                      val car: CarInPost?=null,
                      val remark: String,
                      val seat: Int,
                      val postType: String = Constants.DRIVER_POST_SIMPLE)

