package es.unex.giiis.marvelbook.data.api

import com.google.gson.annotations.SerializedName

data class PersonajeDatos (

    @SerializedName("offset"  ) var offset  : Int?               = null,
    @SerializedName("limit"   ) var limit   : Int?               = null,
    @SerializedName("total"   ) var total   : Int?               = null,
    @SerializedName("count"   ) var count   : Int?               = null,
    @SerializedName("results" ) var results : ArrayList<PersonajeMarvel> = arrayListOf()

)