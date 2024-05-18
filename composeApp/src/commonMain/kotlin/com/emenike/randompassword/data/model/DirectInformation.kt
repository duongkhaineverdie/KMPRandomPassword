
package com.emenike.randompassword.data.model

import kotlinx.serialization.*

@Serializable
data class DirectInformation (
    @SerialName("title")
    val title: String? = null,
)
