package id.bts.devin.alfagift_test.data.models

import com.google.gson.annotations.SerializedName
import id.bts.devin.alfagift_test.BuildConfig
import id.bts.devin.alfagift_test.data.utils.replaceIfNull
import id.bts.devin.alfagift_test.domain.models.*

data class MovieDetailsResponse(

	@SerializedName("original_language")
	val originalLanguage: String? = null,

	@SerializedName("imdb_id")
	val imdbId: String? = null,

	@SerializedName("video")
	val video: Boolean? = null,

	@SerializedName("title")
	val title: String? = null,

	@SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@SerializedName("revenue")
	val revenue: Long? = null,

	@SerializedName("genres")
	val genres: List<GenresResponse?>? = null,

	@SerializedName("popularity")
	val popularity: Double? = null,

	@SerializedName("production_countries")
	val productionCountries: List<ProductionCountriesResponse?>? = null,

	@SerializedName("id")
	val id: Long? = null,

	@SerializedName("vote_count")
	val voteCount: Int? = null,

	@SerializedName("budget")
	val budget: Int? = null,

	@SerializedName("overview")
	val overview: String? = null,

	@SerializedName("original_title")
	val originalTitle: String? = null,

	@SerializedName("runtime")
	val runtime: Int? = null,

	@SerializedName("poster_path")
	val posterPath: String? = null,

	@SerializedName("spoken_languages")
	val spokenLanguages: List<SpokenLanguagesResponse?>? = null,

	@SerializedName("production_companies")
	val productionCompanies: List<ProductionCompaniesResponse?>? = null,

	@SerializedName("release_date")
	val releaseDate: String? = null,

	@SerializedName("vote_average")
	val voteAverage: Double? = null,

	@SerializedName("belongs_to_collection")
	val belongsToCollection: Any? = null,

	@SerializedName("tagline")
	val tagline: String? = null,

	@SerializedName("adult")
	val adult: Boolean? = null,

	@SerializedName("homepage")
	val homepage: String? = null,

	@SerializedName("status")
	val status: String? = null
) {
	companion object {
		fun transform(data: MovieDetailsResponse?): MovieDetailsModel {
			val genres = data?.genres?.map { GenresResponse.transform(it) }.replaceIfNull()
			val productionCountries = data?.productionCountries
				?.map { ProductionCountriesResponse.transform(it) }.replaceIfNull()
			val spokenLanguages = data?.spokenLanguages
				?.map { SpokenLanguagesResponse.transform(it) }.replaceIfNull()
			val productionCompanies = data?.productionCompanies
				?.map { ProductionCompaniesResponse.transform(it) }.replaceIfNull()
			val backdropPath = BuildConfig.BACKDROP_BASE_URL + data?.backdropPath.replaceIfNull()
			val posterPath = BuildConfig.POSTER_BASE_URL + data?.posterPath.replaceIfNull()
			return MovieDetailsModel(
				originalLanguage = data?.originalLanguage.replaceIfNull(),
				imdbId = data?.imdbId.replaceIfNull(),
				video = data?.video.replaceIfNull(),
				title = data?.title.replaceIfNull(),
				backdropPath = backdropPath,
				revenue = data?.revenue.replaceIfNull(),
				genres = genres,
				popularity = data?.popularity.replaceIfNull(),
				productionCountries = productionCountries,
				id = data?.id.replaceIfNull(),
				voteCount = data?.voteCount.replaceIfNull(),
				budget = data?.budget.replaceIfNull(),
				overview = data?.overview.replaceIfNull(),
				originalTitle = data?.originalTitle.replaceIfNull(),
				runtime = data?.runtime.replaceIfNull(),
				posterPath = posterPath,
				spokenLanguages = spokenLanguages,
				productionCompanies = productionCompanies,
				releaseDate = data?.releaseDate.replaceIfNull(),
				voteAverage = data?.voteAverage.replaceIfNull(),
				tagline = data?.tagline.replaceIfNull(),
				adult = data?.adult.replaceIfNull(),
				homepage = data?.homepage.replaceIfNull(),
				status = data?.status.replaceIfNull()
			)
		}
	}
}

data class ProductionCountriesResponse(

	@SerializedName("iso_3166_1")
	val iso31661: String? = null,

	@SerializedName("name")
	val name: String? = null
) {
	companion object {
		fun transform(data: ProductionCountriesResponse?): ProductionCountriesModel {
			return ProductionCountriesModel(
				iso31661 = data?.iso31661.replaceIfNull(),
				name = data?.name.replaceIfNull()
			)
		}
	}
}

data class GenresResponse(

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("id")
	val id: Int? = null
) {
	companion object {
		fun transform(data: GenresResponse?): GenresModel {
			return GenresModel(
				name = data?.name.replaceIfNull(),
				id = data?.id.replaceIfNull()
			)
		}
	}
}

data class ProductionCompaniesResponse(

	@SerializedName("logo_path")
	val logoPath: String? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("origin_country")
	val originCountry: String? = null
) {
	companion object {
		fun transform(data: ProductionCompaniesResponse?): ProductionCompaniesModel {
			return ProductionCompaniesModel(
				logoPath = data?.logoPath.replaceIfNull(),
				name = data?.name.replaceIfNull(),
				id = data?.id.replaceIfNull(),
				originCountry = data?.originCountry.replaceIfNull()
			)
		}
	}
}

data class SpokenLanguagesResponse(

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("iso_639_1")
	val iso6391: String? = null
) {
	companion object {
		fun transform(data: SpokenLanguagesResponse?): SpokenLanguagesModel {
			return SpokenLanguagesModel(
				name = data?.name.replaceIfNull(),
				iso6391 = data?.iso6391.replaceIfNull()
			)
		}
	}
}
