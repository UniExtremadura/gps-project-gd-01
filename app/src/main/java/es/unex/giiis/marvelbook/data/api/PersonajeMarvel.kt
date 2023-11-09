package es.unex.giiis.marvelbook.data.api

import com.google.gson.annotations.SerializedName

data class PersonajeMarvel(

    @SerializedName("id"          ) var id          : Int?            = null,
    @SerializedName("name"        ) var name        : String?         = null,
    @SerializedName("description" ) var description : String?         = null,
    @SerializedName("modified"    ) var modified    : String?         = null,
    @SerializedName("thumbnail"   ) var thumbnail   : Thumbnail?      = Thumbnail(),
    @SerializedName("resourceURI" ) var resourceURI : String?         = null,
    @SerializedName("comics"      ) var comics      : ComicPersonaje? = ComicPersonaje(),
    @SerializedName("series"      ) var series      : SeriePersonaje? = SeriePersonaje(),
    @SerializedName("stories"     ) var stories     : CuentoPersonaje? = CuentoPersonaje(),
    @SerializedName("events"      ) var events      : EventoPersonaje?  = EventoPersonaje(),
    @SerializedName("urls"        ) var urls        : ArrayList<UrlPersonaje> = arrayListOf()

)
