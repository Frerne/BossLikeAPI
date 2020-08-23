package ru.bosslike.api

import com.google.gson.annotations.SerializedName

data class BaseResponseModel<T>(
    val status: Int,
    val success: Boolean,
    val data: T?
)

data class RegisterResponseModel(
    val token: TokenModel,
    val user: UserModel
)

data class TokenModel(
    val key: String,
    @SerializedName("expire_at")
    val expireAt: Int?,
    val type: String /* Example: Apikey */
)

data class UserModel(
    val id: Int,
    val login: String,
    val email: String,
    @SerializedName("email_confirm")
    val emailConfirm: Boolean,
    val name: String,
    val point: Int,
    val money: Int
)

data class UserSocialModel(
    val id: Int,
    @SerializedName("social_id")
    val socialId: String,
    @SerializedName("social_type")
    val socialType: Int,
    val name: String,
    val username: String,
    val url: String,
    val image: String,
    val status: Int
)

data class SimpleMessageModel(
    val message: String
)

data class LinkByLikeCheckProfileResponseModel(
    val message: String,
    val token: String,
    val social: UserSocialModel
)

data class LinkFromAuthByLikeModel(
    val message: String,
    val token: String,
    val url: String,
    @SerializedName("service_type")
    val serviceType: Int,
    @SerializedName("task_typ")
    val taskType: Int,
    val action: String
)

data class CheckLikeResponseModel(
    val message: String,
    val social: UserSocialModel
)

data class AuthByPhoneFirstStageModel(
    val message: String,
    val token: String
)

data class TasksResponseModel(
    val items: ArrayList<TaskResponseModel>,
    val limit: Int
)

data class TaskResponseModel(
    val id: Int,
    val name: TaskNameModel,
    val image: String,
    val task_type: Int,
    val service_type: Int,
    val price: TaskPriceModel
)

data class TaskNameModel(
    val full: String,
    val action: String,
    val short_action: String,
    @SerializedName("object")
    val obj: String
)

data class TaskPriceModel(
    val description: String,
    val value: Int,
    val text: String
)

data class TasksInitResponseModel(
    val url: String,
    val task_type: Int,
    val service_type: Int,
    val seconds: Int,
    val action: String,
    val user_price: Int,
    val comment: String?,
    val answer: TaskAnswerModel
)

data class TaskAnswerModel(
    val value: Int?,
    val text: String?
)

data class TaskCheckModel(
    val message: String,
    val user_price: Int,
    val user: UserPointModel
)

data class UserPointModel(
    val point: Int
)

data class TaskHideResponseModel(
    val message: String,
    val code: Int
)

data class CouponModel(
    val code: String,
    val point: Int
)