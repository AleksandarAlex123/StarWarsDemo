package rs.alexandar.startwars.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RedirectInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response = chain.proceed(chain.request())
        if (response.code() == 307) {
            request = request.newBuilder()
                .url(response.header("Location"))
                .build()
            response = chain.proceed(request)
        }
        return response
    }
}