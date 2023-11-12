package es.unex.giiis.marvelbook.data.api


import com.google.gson.annotations.SerializedName


data class UrlComic (

    @SerializedName("type" ) var type : String? = null,
    @SerializedName("url"  ) var url  : String? = null

)