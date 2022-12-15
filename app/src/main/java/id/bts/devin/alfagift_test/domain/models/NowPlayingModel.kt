package id.bts.devin.alfagift_test.domain.models

data class NowPlayingModel(
	val overview: String,
	val originalLanguage: String,
	val originalTitle: String,
	val video: Boolean,
	val title: String,
	val genreIds: List<Int>,
	val posterPath: String,
	val backdropPath: String,
	val releaseDate: String,
	val popularity: Double,
	val voteAverage: Double,
	val id: Long,
	val adult: Boolean,
	val voteCount: Int
)

