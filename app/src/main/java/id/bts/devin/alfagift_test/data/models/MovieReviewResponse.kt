package id.bts.devin.alfagift_test.data.models

import com.google.gson.annotations.SerializedName
import id.bts.devin.alfagift_test.BuildConfig
import id.bts.devin.alfagift_test.data.utils.replaceIfNull
import id.bts.devin.alfagift_test.domain.models.AuthorDetailsModel
import id.bts.devin.alfagift_test.domain.models.MovieReviewsModel

data class MovieReviewResponse(
	@SerializedName("results")
	val results: List<MovieReviewResultResponse>? = null
): BaseResponse()

data class MovieReviewResultResponse(

	@SerializedName("author_details")
	val authorDetails: AuthorDetailsResponse? = null,

	@SerializedName("updated_at")
	val updatedAt: String? = null,

	@SerializedName("author")
	val author: String? = null,

	@SerializedName("created_at")
	val createdAt: String? = null,

	@SerializedName("id")
	val id: String? = null,

	@SerializedName("content")
	val content: String? = null,

	@SerializedName("url")
	val url: String? = null
) {
	companion object {
		fun transform(data: MovieReviewResultResponse?): MovieReviewsModel {
			val authorDetails = AuthorDetailsResponse.transform(data?.authorDetails)
			return MovieReviewsModel(
				authorDetails = authorDetails,
				updatedAt = data?.updatedAt.replaceIfNull(),
				author = data?.author.replaceIfNull(),
				createdAt = data?.createdAt.replaceIfNull(),
				id = data?.id.replaceIfNull(),
				content = data?.content.replaceIfNull(),
				url = data?.url.replaceIfNull()
			)
		}
	}
}

data class AuthorDetailsResponse(

	@SerializedName("avatar_path")
	val avatarPath: String? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("rating")
	val rating: Int? = null,

	@SerializedName("username")
	val username: String? = null
) {
	companion object {
		fun transform(data: AuthorDetailsResponse?): AuthorDetailsModel {
			return AuthorDetailsModel(
				avatarPath = BuildConfig.POSTER_BASE_URL + data?.avatarPath.replaceIfNull(),
				name = data?.name.replaceIfNull(),
				rating = data?.rating.replaceIfNull(),
				username = data?.username.replaceIfNull()
			)
		}
	}
}
