package id.bts.devin.alfagift_test.domain.models


data class MovieDetailsModel(
	val originalLanguage: String,
	val imdbId: String,
	val video: Boolean,
	val title: String,
	val backdropPath: String,
	val revenue: Long,
	val genres: List<GenresModel>,
	val popularity: Double,
	val productionCountries: List<ProductionCountriesModel>,
	val id: Long,
	val voteCount: Int,
	val budget: Int,
	val overview: String,
	val originalTitle: String,
	val runtime: Int,
	val posterPath: String,
	val spokenLanguages: List<SpokenLanguagesModel>,
	val productionCompanies: List<ProductionCompaniesModel>,
	val releaseDate: String,
	val voteAverage: Double,
	val tagline: String,
	val adult: Boolean,
	val homepage: String,
	val status: String
)

data class SpokenLanguagesModel(
	val name: String,
	val iso6391: String
)

data class ProductionCountriesModel(
	val iso31661: String,
	val name: String
)

data class GenresModel(
	val name: String,
	val id: Int
)

data class ProductionCompaniesModel(
	val logoPath: String,
	val name: String,
	val id: Int,
	val originCountry: String
)

