package ru.bosslike.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

inline infix fun <T> Call<BaseResponseModel<T?>>.enqueueThread(crossinline fn: (response: BaseResponseModel<T?>) -> Unit) {
    enqueue(object : Callback<BaseResponseModel<T?>> {
        override fun onFailure(call: Call<BaseResponseModel<T?>>, t: Throwable) {
            t.printStackTrace()
            fn(BaseResponseModel(-1, false, null))
        }

        override fun onResponse(call: Call<BaseResponseModel<T?>>, response: Response<BaseResponseModel<T?>>) {
            if (response.body() != null)
                fn(response.body()!!)
            else fn.invoke(BaseResponseModel(-2, false, null))
        }
    })
}
