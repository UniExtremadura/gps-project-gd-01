package es.unex.giiis.marvelbook.data.api


import com.google.gson.annotations.SerializedName



data class ComicMarvel (

    @SerializedName("id"                 ) var id                 : Int?                    = null,
    @SerializedName("digitalId"          ) var digitalId          : Int?                    = null,
    @SerializedName("title"              ) var title              : String?                    = null,
    @SerializedName("issueNumber"        ) var issueNumber        : Double?                    = null,
    @SerializedName("variantDescription" ) var variantDescription : String?                    = null,
    @SerializedName("description"        ) var description        : String?                    = null,
    @SerializedName("modified"           ) var modified           : String?                    = null,
    @SerializedName("isbn"               ) var isbn               : String?                    = null,
    @SerializedName("upc"                ) var upc                : String?                    = null,
    @SerializedName("diamondCode"        ) var diamondCode        : String?                    = null,
    @SerializedName("ean"                ) var ean                : String?                    = null,
    @SerializedName("issn"               ) var issn               : String?                    = null,
    @SerializedName("format"             ) var format             : String?                    = null,
    @SerializedName("pageCount"          ) var pageCount          : Int?                    = null,
    @SerializedName("textObjects"        ) var textObjects        : ArrayList<TextObjects>     = arrayListOf(),
    @SerializedName("resourceURI"        ) var resourceURI        : String?                    = null,
    @SerializedName("urls"               ) var urls               : ArrayList<UrlComic>            = arrayListOf(),
    @SerializedName("series"             ) var series             : SerieComic?                    = SerieComic(),
    @SerializedName("variants"           ) var variants           : ArrayList<VarianteComic>        = arrayListOf(),
    @SerializedName("collections"        ) var collections        : ArrayList<ColeccionComic>     = arrayListOf(),
    @SerializedName("collectedIssues"    ) var collectedIssues    : ArrayList<ColeccionEdicionesComic> = arrayListOf(),
    @SerializedName("dates"              ) var dates              : ArrayList<FechaComic>           = arrayListOf(),
    @SerializedName("prices"             ) var prices             : ArrayList<PrecioComic>          = arrayListOf(),
    @SerializedName("thumbnail"          ) var thumbnail          : Thumbnail?                 = Thumbnail(),
    @SerializedName("images"             ) var images             : ArrayList<ImagenComic>          = arrayListOf(),
    @SerializedName("creators"           ) var creators           : CreadorComic?                  = CreadorComic(),
    @SerializedName("characters"         ) var characters         : PersonajeComic?                = PersonajeComic(),
    @SerializedName("stories"            ) var stories            : CuentoComic?                   = CuentoComic(),
    @SerializedName("events"             ) var events             : EventoComic?                    = EventoComic()

)