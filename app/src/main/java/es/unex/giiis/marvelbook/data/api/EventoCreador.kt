package es.unex.giiis.marvelbook.data.api

import com.google.gson.annotations.SerializedName

data class EventoCreador (

    @SerializedName("available"     ) var available     : Int?              = null,
    @SerializedName("collectionURI" ) var collectionURI : String?           = null,
    @SerializedName("items"         ) var items         : ArrayList<Item> = arrayListOf(),
    @SerializedName("returned"      ) var returned      : Int?              = null

)