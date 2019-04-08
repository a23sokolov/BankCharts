package a23sokolov.bankcharts.network

import a23sokolov.bankcharts.BuildConfig
import android.annotation.SuppressLint
import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Created by a.v.sokolov
 */
object NetworkService {
    private const val TIMEOUT_S = 60L
    private const val CACHE_SIZE = 5 * 1024 * 1024L // 5 MB

    private var bankApi: BankApi? = null

    //todo переделать на даггер, чтобы был singleton и для взятия bankApi не приходилось тащить context во ViewModel
    fun init(context: Context) {
        val okHttpClient = createOkhttp(context)
        val restAdapter = createRestAdapter(okHttpClient)
        bankApi = restAdapter.create(BankApi::class.java)
    }

    fun getBankApi(): BankApi {
        return bankApi!!
    }

    private fun createRestAdapter(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BANK_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createOkhttp(context: Context): OkHttpClient {
        val certs = getCerts()
        val sslSocketFactory = getSocketFactory(certs)

        return OkHttpClient.Builder()
            .cache(Cache(context.getCacheDir(), CACHE_SIZE))
            .connectTimeout(TIMEOUT_S, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_S, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_S, TimeUnit.SECONDS)
            .sslSocketFactory(sslSocketFactory, certs[0] as X509TrustManager)
            .addInterceptor(getNetworkInterceptor(context))
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }

    @SuppressLint("TrustAllX509TrustManager")
    private fun getCerts(): Array<TrustManager> {
        return arrayOf(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit
            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) = Unit
            override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
        })
    }

    private fun getSocketFactory(trustAllCerts: Array<TrustManager>): SSLSocketFactory {
        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        return sslContext.socketFactory
    }

    private fun getNetworkInterceptor(context: Context): Interceptor {
        val connectionCheckManager = ConnectionCheckManager(context)
        return Interceptor { chain ->
            connectionCheckManager.checkConnected()
            try {
                return@Interceptor chain.proceed(chain.request())
            } catch (e: Exception) {
                connectionCheckManager.checkConnected()
                throw e
            }
        }
    }

}