package com.decagon.android.sq007

import com.google.firebase.database.IgnoreExtraProperties
@IgnoreExtraProperties

data class PartnerLocationModel(
    var Latitude: Double? = 0.0,
    var Longitude: Double? = 0.0
)