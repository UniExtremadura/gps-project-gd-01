package es.unex.giiis.marvelbook.data.api

import com.google.gson.annotations.SerializedName


data class TextObjects (

    @SerializedName("type"     ) var type     : String? = null,
    @SerializedName("language" ) var language : String? = null,
    @SerializedName("text"     ) var text     : String? = null

)