package id.bts.devin.alfagift_test.data.models

import com.google.gson.annotations.SerializedName
import id.bts.devin.alfagift_test.data.utils.replaceIfNull
import id.bts.devin.alfagift_test.domain.models.MovieVideosModel

data class MovieVideosResponse(
	@SerializedName("results")
	val results: List<MovieVideosResultResponse>? = null
): BaseResponse()

data class MovieVideosResultResponse(

	@SerializedName("site")
	val site: String? = null,

	@SerializedName("size")
	val size: Int? = null,

	@SerializedName("iso_3166_1")
	val iso31661: String? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("official")
	val official: Boolean? = null,

	@SerializedName("id")
	val id: String? = null,

	@SerializedName("type")
	val type: String? = null,

	@SerializedName("published_at")
	val publishedAt: String? = null,

	@SerializedName("iso_639_1")
	val iso6391: String? = null,

	@SerializedName("key")
	val key: String? = null
) {
	companion object {
		fun transform(data: MovieVideosResultResponse?): MovieVideosModel {
			return MovieVideosModel(
				site = data?.site.replaceIfNull(),
				size = data?.size.replaceIfNull(),
				iso31661 = data?.iso31661.replaceIfNull(),
				name = data?.name.replaceIfNull(),
				official = data?.official.replaceIfNull(),
				id = data?.id.replaceIfNull(),
				type = data?.type.replaceIfNull(),
				publishedAt = data?.publishedAt.replaceIfNull(),
				iso6391 = data?.iso6391.replaceIfNull(),
				key = data?.key.replaceIfNull()
			)
		}
		fun transformList(data: List<MovieVideosResultResponse>?): List<MovieVideosModel> {
			return data?.map { transform(it) } ?: listOf()
		}
	}
}
