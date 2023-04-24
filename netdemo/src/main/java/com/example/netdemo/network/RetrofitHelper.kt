package com.example.netdemo.network

import com.example.netdemo.cookieTest.RetrofitService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//单例类
object RetrofitHelper {

    private const val TAG = "RetrofitHelper"
    private const val CONTENT_PRE = "OkHttp: "
    private const val SAVE_USER_LOGIN_KEY = "user/login"
    private const val SAVE_USER_REGISTER_KEY = "user/register"
    private const val SET_COOKIE_KEY = "set-cookie"
    private const val COOKIE_NAME = "Cookie"
    private const val CONNECT_TIMEOUT = 30L
    private const val READ_TIMEOUT = 10L

    val retrofitService: RetrofitService =
        getService(Constant.REQUEST_BASE_URL, RetrofitService::class.java)


    /**
     * create Retrofit
     */
    private fun create(url: String): Retrofit {
        //okHttpClientBuilder
        val okHttpClientBuilder = OkHttpClient().newBuilder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            //get response cookie通过拦截器
            addInterceptor {
                val request = it.request()
                val response = it.proceed(request)
                val requestUrl = request.url.toString()
                val domain = request.url.host
                //set-cookie maybe has multi,login to save cookie
                if ((requestUrl.contains(SAVE_USER_LOGIN_KEY) || requestUrl.contains(
                        SAVE_USER_REGISTER_KEY
                    )) && !response.headers(SET_COOKIE_KEY).isEmpty()
                ) {
                    val cookies = response.headers(SET_COOKIE_KEY)
                    val cookie = encodeCookie(cookies)
                    saveCookie(requestUrl, domain, cookie)//登录后保存cookie
                }
                response
            }
            //set request cookie
            addInterceptor {
                val request = it.request()
                val builder = request.newBuilder()
                val domain = request.url.host
                // get domain cookie//获取domain字段中的cookie并添加到请求头
                if (domain.isNotEmpty()) {
                    val spDomain: String by Preference(domain, "")
                    val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""//调用委托类Preferences的getValue()方法
                    if (cookie.isNotEmpty()) {
                        builder.addHeader(COOKIE_NAME, cookie)//添加cookie请求头
                    }
                }
                it.proceed(builder.build())
            }
//            //add log print
//            addInterceptor(KtHttpLogInterceptor {//如果添加的是NetWorkInterceptor获取到的只有post请求没有get请求
//                logLevel(KtHttpLogInterceptor.LogLevel.BODY)
//            })

        }
        return RetrofitBuild(
            url = url,
            client = okHttpClientBuilder.build(),
            gsonFactory = GsonConverterFactory.create(),

        ).retrofit
    }


    /**
     * get ServiceApi
     */
    private fun <T> getService(url: String, service: Class<T>): T = create(url).create(service)


    /**
     * save cookie to SharedPreferences
     */
    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    private fun saveCookie(url: String?, domain: String?, cookies: String) {
        url ?: return
        var spUrl: String by Preference(url, cookies)//属性委托
        @Suppress("UNUSED_VALUE")
        spUrl = cookies//赋值通过委托会调用Preference的setValue方法将cookies存入SharedPreferences
        domain ?: return
        var spDomain: String by Preference(domain, cookies)
        @Suppress("UNUSED_VALUE")
        spDomain = cookies
    }


    /**
     * save cookie string
     */
    fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies
            .map { cookie ->
                cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            }
            .forEach {
                it.filterNot { set.contains(it) }.forEach { set.add(it) }
            }

        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }

        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }

        return sb.toString()
    }


    /**
     * create retrofit build
     */
    class RetrofitBuild(
        url: String, client: OkHttpClient,
        gsonFactory: GsonConverterFactory,

    ) {
        val retrofit: Retrofit = Retrofit.Builder().apply {
            baseUrl(url)
            client(client)
            addConverterFactory(gsonFactory)

        }.build()

    }
}
