package id.bts.devin.alfagift_test.domain.models

data class MovieReviewsModel(
	val authorDetails: AuthorDetailsModel,
	val updatedAt: String,
	val author: String,
	val createdAt: String,
	val id: String,
	val content: String,
	val url: String
)

data class AuthorDetailsModel(
	val avatarPath: String,
	val name: String,
	val rating: Int,
	val username: String
)

