package es.unex.giiis.marvelbook.data.api
import com.google.gson.annotations.SerializedName

data class ComicCabecera (

    @SerializedName("code"            ) var code            : Int? = null,
    @SerializedName("status"          ) var status          : String? = null,
    @SerializedName("copyright"       ) var copyright       : String? = null,
    @SerializedName("attributionText" ) var attributionText : String? = null,
    @SerializedName("attributionHTML" ) var attributionHTML : String? = null,
    @SerializedName("data"            ) var data            : ComicDatos?   = ComicDatos(),
    @SerializedName("etag"            ) var etag            : String? = null

)