package es.unex.giiis.marvelbook.data.api

import com.google.gson.annotations.SerializedName

data class CreadorComic (

    @SerializedName("available"     ) var available     : Int?          = null,
    @SerializedName("returned"      ) var returned      : Int?          = null,
    @SerializedName("collectionURI" ) var collectionURI : String?          = null,
    @SerializedName("items"         ) var items         : ArrayList<ItemRolComic> = arrayListOf()

)