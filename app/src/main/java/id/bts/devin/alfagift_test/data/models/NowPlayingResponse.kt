package id.bts.devin.alfagift_test.data.models

import com.google.gson.annotations.SerializedName
import id.bts.devin.alfagift_test.BuildConfig
import id.bts.devin.alfagift_test.data.utils.replaceIfNull
import id.bts.devin.alfagift_test.domain.models.NowPlayingModel

data class NowPlayingResponse(
	@SerializedName("results")
	val results: List<NowPlayingResultResponse>? = null
): BaseResponse()

data class NowPlayingResultResponse(
	@SerializedName("overview")
	val overview: String? = null,

	@SerializedName("original_language")
	val originalLanguage: String? = null,

	@SerializedName("original_title")
	val originalTitle: String? = null,

	@SerializedName("video")
	val video: Boolean? = null,

	@SerializedName("title")
	val title: String? = null,

	@SerializedName("genre_ids")
	val genreIds: List<Int>? = null,

	@SerializedName("poster_path")
	val posterPath: String? = null,

	@SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@SerializedName("release_date")
	val releaseDate: String? = null,

	@SerializedName("popularity")
	val popularity: Double? = null,

	@SerializedName("vote_average")
	val voteAverage: Double? = null,

	@SerializedName("id")
	val id: Long? = null,

	@SerializedName("adult")
	val adult: Boolean? = null,

	@SerializedName("vote_count")
	val voteCount: Int? = null
) {
	companion object {
		fun transform(data: NowPlayingResultResponse?): NowPlayingModel {
			val backdropPath = BuildConfig.BACKDROP_BASE_URL + data?.backdropPath.replaceIfNull()
			val posterPath = BuildConfig.POSTER_BASE_URL + data?.posterPath.replaceIfNull()
			return NowPlayingModel(
				overview = data?.overview.replaceIfNull(),
				originalLanguage = data?.originalLanguage.replaceIfNull(),
				originalTitle = data?.originalTitle.replaceIfNull(),
				video = data?.video.replaceIfNull(),
				title = data?.title.replaceIfNull(),
				genreIds = data?.genreIds ?: listOf(),
				posterPath = posterPath,
				backdropPath = backdropPath,
				releaseDate = data?.releaseDate.replaceIfNull(),
				popularity = data?.popularity.replaceIfNull(),
				voteAverage = data?.voteAverage.replaceIfNull(),
				id = data?.id.replaceIfNull(),
				adult = data?.adult.replaceIfNull(),
				voteCount = data?.voteCount.replaceIfNull()
			)
		}
	}
}
