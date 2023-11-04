package es.unex.giiis.marvelbook.data.api

import com.google.gson.annotations.SerializedName

data class CreadorMarvel (

    @SerializedName("id"          ) var id          : Int?            = null,
    @SerializedName("firstName"   ) var firstName   : String?         = null,
    @SerializedName("middleName"  ) var middleName  : String?         = null,
    @SerializedName("lastName"    ) var lastName    : String?         = null,
    @SerializedName("suffix"      ) var suffix      : String?         = null,
    @SerializedName("fullName"    ) var fullName    : String?         = null,
    @SerializedName("modified"    ) var modified    : String?         = null,
    @SerializedName("thumbnail"   ) var thumbnail   : Thumbnail?      = Thumbnail(),
    @SerializedName("resourceURI" ) var resourceURI : String?         = null,
    @SerializedName("comics"      ) var comics      : ComicCreador?         = ComicCreador(),
    @SerializedName("series"      ) var series      : SerieCreador?         = SerieCreador(),
    @SerializedName("stories"     ) var stories     : CuentoCreador?        = CuentoCreador(),
    @SerializedName("events"      ) var events      : EventoCreador?         = EventoCreador(),
    @SerializedName("urls"        ) var urls        : ArrayList<UrlCreador> = arrayListOf()

)