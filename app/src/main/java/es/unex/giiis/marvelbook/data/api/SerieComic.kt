package es.unex.giiis.marvelbook.data.api

import com.google.gson.annotations.SerializedName
data class SerieComic (

    @SerializedName("resourceURI" ) var resourceURI : String? = null,
    @SerializedName("name"        ) var name        : String? = null

)