package id.bts.devin.alfagift_test.data.models

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("page")
    val page: Long? = null,
    @SerializedName("dates")
    val dates: BaseDateResponse? = null,
    @SerializedName("total_pages")
    val totalPages: Long? = null,
    @SerializedName("total_results")
    val totalResults: Long? = null
)

data class BaseDateResponse(
    @SerializedName("maximum")
    val maximum: String? = null,
    @SerializedName("minimum")
    val minimum: String? = null
)
