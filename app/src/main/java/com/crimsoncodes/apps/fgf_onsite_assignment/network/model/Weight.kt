package com.crimsoncodes.apps.fgf_onsite_assignment.network.model

import com.google.gson.annotations.SerializedName

data class Weight(
    @SerializedName("imperial")
    val imperial: String? = "",
    @SerializedName("metric")
    val metric: String? = ""
)