package es.unex.giiis.marvelbook.data.api

import com.google.gson.annotations.SerializedName


data class PrecioComic (

    @SerializedName("type"  ) var type  : String? = null,
    @SerializedName("price" ) var price : Float? = null

)
