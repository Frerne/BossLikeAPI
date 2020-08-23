package ru.bosslike.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

object Users {
    fun create(
        email: String,
        password: String,
        refererId: Int? = null,
        lambda: BaseResponseModel<RegisterResponseModel?>.() -> Unit
    ) {
        api.register(email, password, password, refererId) enqueueThread { response ->
            lambda.invoke(response)
        }
    }

    fun get(apiKey: String, lambda: BaseResponseModel<UserModel?>.() -> Unit) {
        api.me(apiKey) enqueueThread { response ->
            lambda.invoke(response)
        }
    }

}

object UserSocial {

    fun all(apiKey: String, lambda: BaseResponseModel<ArrayList<UserSocialModel>?>.() -> Unit) {
        api.getLinkedSocials(apiKey) enqueueThread { response ->
            lambda.invoke(response)
        }
    }

    fun linkAccountByLike(
        apiKey: String,
        url: String,
        type: Int,
        lambda: BaseResponseModel<LinkByLikeCheckProfileResponseModel?>.() -> Unit
    ) {
        if (type != 1 || type != 3 || type != 5)
            throw RequestError("Social type is wrong, can be only 1 (Vkontakte), 3 (Instagram) or 5 (Twitter)")
        api.authByLikeCheckProfile(apiKey, url, type) enqueueThread { response ->
            lambda.invoke(response)
        }
    }

    fun getTaskForConfirmation(
        apiKey: String,
        token: String,
        lambda: BaseResponseModel<LinkFromAuthByLikeModel?>.() -> Unit
    ) {
        api.getUrlFromAuthByLike(apiKey, token) enqueueThread { response ->
            lambda.invoke(response)
        }
    }

    fun checkLike(apiKey: String, token: String, lambda: BaseResponseModel<CheckLikeResponseModel?>.() -> Unit) {
        api.checkLikeInAuthByLike(apiKey, token) enqueueThread { response ->
            lambda.invoke(response)
        }
    }

    fun linkAccountByPhone(
        apiKey: String,
        serviceType: Int,
        phoneNumber: String,
        lambda: BaseResponseModel<AuthByPhoneFirstStageModel?>.() -> Unit
    ) {
        api.authByPhone(apiKey, serviceType, phoneNumber) enqueueThread { response ->
            lambda.invoke(response)
        }
    }

    fun checkByCode(
        apiKey: String,
        token: String,
        code: String,
        lambda: BaseResponseModel<AuthByPhoneCheckModel?>.() -> Unit
    ) {
        api.checkAuthByPhone(apiKey, token, code) enqueueThread { response ->
            lambda.invoke(response)
        }
    }

}

object Coupon {
    fun get(apiKey: String, code: String, lambda: BaseResponseModel<CouponModel?>.() -> Unit) {
        api.getCouponInfo(apiKey, code) enqueueThread { response ->
            lambda.invoke(response)
        }
    }
}

object Tasks {

    fun all(serviceType: Int, taskType: Int, lambda: BaseResponseModel<TasksResponseModel?>.() -> Unit) {
        api.getTasks(serviceType, taskType) enqueueThread { response ->
            lambda.invoke(response)
        }
    }

    fun hide(taskId: Int, apiKey: String, lambda: BaseResponseModel<TaskHideResponseModel?>.() -> Unit) {
        api.hideTask(apiKey, taskId) enqueueThread { response ->
            lambda.invoke(response)
        }
    }

    fun initialize(taskId: Int, apiKey: String, lambda: BaseResponseModel<TasksInitResponseModel?>.() -> Unit) {
        api.initTask(taskId, apiKey) enqueueThread { response ->
            lambda.invoke(response)
        }
    }

    fun check(taskId: Int, apiKey: String, lambda: BaseResponseModel<TaskCheckModel?>.() -> Unit) {
        api.checkTask(taskId, apiKey) enqueueThread { response ->
            lambda.invoke(response)
        }
    }

}

data class RequestError(override val message: String) : Exception(message)


private val api = BotsAPI.retrofit.create(BotsAPI.API::class.java)

private object BotsAPI {

    private const val BASE_URL = "https://api-public.bosslike.ru/v1/bots/"

    private val gsonConverter: GsonConverterFactory = GsonConverterFactory.create()

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }
    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .addInterceptor(interceptor)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(gsonConverter)
        .build()

    interface API {

        @FormUrlEncoded
        @POST("users/register")
        fun register(
            @Field("email") email: String,
            @Field("password") password: String,
            @Field("password_repeat") passwordRepeat: String,
            @Field("referer_id") refererId: Int?
        ): Call<BaseResponseModel<RegisterResponseModel?>>

        @GET("users/me")
        fun me(@Header("X-Api-Key") apiKey: String): Call<BaseResponseModel<UserModel?>>

        @GET("users/me/socials")
        fun getLinkedSocials(@Header("X-Api-Key") apiKey: String): Call<BaseResponseModel<ArrayList<UserSocialModel>?>>

        @DELETE("users/me/social")
        fun deleteLinkedSocial(
            @Header("X-Api-Key") apiKey: String,
            type: Int
        ): Call<BaseResponseModel<SimpleMessageModel?>>

        @POST("users/me/social/auth/like/check-profile")
        fun authByLikeCheckProfile(
            @Header("X-Api-Key") apiKey: String,
            url: String,
            type: Int
        ): Call<BaseResponseModel<LinkByLikeCheckProfileResponseModel?>>

        @GET("users/me/social/auth/like/show-like")
        fun getUrlFromAuthByLike(
            @Header("X-Api-Key") apiKey: String,
            @Query("token") token: String
        ): Call<BaseResponseModel<LinkFromAuthByLikeModel?>>

        @GET("users/me/social/auth/like/check-like")
        fun checkLikeInAuthByLike(
            @Header("X-Api-Key") apiKey: String,
            @Query("token") token: String
        ): Call<BaseResponseModel<CheckLikeResponseModel?>>

        @FormUrlEncoded
        @POST("users/me/social/auth/phone")
        fun authByPhone(
            @Header("X-Api-Key") apiKey: String,
            @Query("service_type") serviceType: Int,
            @Query("phone_number") phoneNumber: String
        ): Call<BaseResponseModel<AuthByPhoneFirstStageModel?>>

        @POST("users/me/social/auth/phone")
        fun checkAuthByPhone(
            @Header("X-Api-Key") apiKey: String,
            @Query("token") token: String,
            @Query("code") code: String
        ): Call<BaseResponseModel<AuthByPhoneCheckModel?>>

        @GET("tasks")
        fun getTasks(
            @Query("service_type") serviceType: Int,
            @Query("task_type") taskType: Int
        ): Call<BaseResponseModel<TasksResponseModel?>>

        @GET("tasks/{id}/do")
        fun initTask(
            @Path("id") id: Int,
            @Header("X-Api-Key") apiKey: String
        ): Call<BaseResponseModel<TasksInitResponseModel?>>

        @GET("tasks/{id}/check")
        fun checkTask(
            @Path("id") id: Int,
            @Header("X-Api-Key") apiKey: String
        ): Call<BaseResponseModel<TaskCheckModel?>>

        @POST("tasks/{id}/hide")
        fun hideTask(
            @Header("X-Api-Key") apiKey: String,
            @Path("id") id: Int
        ): Call<BaseResponseModel<TaskHideResponseModel?>>

        @GET("coupon/{code}/")
        fun getCouponInfo(
            @Header("X-Api-Key") apiKey: String,
            @Path("code") code: String
        ): Call<BaseResponseModel<CouponModel?>>


    }
}