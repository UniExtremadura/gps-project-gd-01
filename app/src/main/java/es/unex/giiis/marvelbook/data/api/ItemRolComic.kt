package es.unex.giiis.marvelbook.data.api

import com.google.gson.annotations.SerializedName


data class ItemRolComic (

    @SerializedName("resourceURI" ) var resourceURI : String? = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("role"        ) var role        : String? = null

)