package es.unex.giiis.marvelbook.data.api


import com.google.gson.annotations.SerializedName



data class FechaComic (

@SerializedName("type" ) var type : String? = null,
@SerializedName("date" ) var date : String? = null

)