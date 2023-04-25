package dev.remaker.sketchubx.net

import dev.remaker.sketchubx.models.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

private const val BASE_URL = "https://sketchub.in"

interface SketchubApiService {

    @POST("/api/v3/get_project_list.php")
    @FormUrlEncoded
    suspend fun getProjectList(@Field("api_key") apiKey: String, @Field("scope") scope: String = "recent"): ProjectResponse

    @POST("/api/v3/get_news.php")
    @FormUrlEncoded
    suspend fun getAnnouncementList(@Field("api_key") apiKey: String): AnnouncementResponse

    companion object {
        private val okHttpClient = OkHttpClient.Builder().build()

        fun create(): SketchubApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SketchubApiService::class.java)
        }
    }
}
