package com.example.netdemo.network

import android.util.Log
import okhttp3.*
import java.lang.StringBuilder
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.*


//用于记录okHttp的网络日志的拦截器
class KtHttpLogInterceptor(block:(KtHttpLogInterceptor.()->Unit)?=null):Interceptor {

    private var logLevel: LogLevel = LogLevel.NONE//打印日期的标记
    private var colorLevel: ColorLevel = ColorLevel.DEBUG//默认是debug级别的logcat
    private var logTag= TAG//日志的Logcat的Tag

    init {
        block?.invoke(this)
    }

    /**
     * 设置LogLevel
     */
    fun logLevel(level: LogLevel): KtHttpLogInterceptor {
        logLevel=level
        return this
    }

    /**
     * 打印日志范围
     */
    enum class LogLevel{
        NONE,//不打印
        BASIC,//只打印行首，请求/响应
        HEADERS,//打印请求和响应的 所有 header
        BODY//打印所有
    }

    /**
     * Log颜色等级，应用于Android Logcat分为 v,d,i,w,e
     */
    enum class ColorLevel{
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    //记录请求日志
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d(TAG, "intercept: 静安里了")
        // 请求
        val request=chain.request()
        //响应
        return kotlin.runCatching {
            chain.proceed(request)
        }.onFailure {//打印异常信息
            it.printStackTrace()
            logIt(
                it.message.toString(),
                ColorLevel.ERROR
            )
        }.onSuccess {
            response->
            if(logLevel== LogLevel.NONE){
                return response
            }
            //记录请求日志
            logRequest(request,chain.connection())
            //记录响应日志
            logResponse(response)
        }.getOrThrow()

    }

    //记录请求日志
    private fun logRequest(request: Request,connection:Connection?){
        val sb= StringBuilder()
        sb.append("\r\n")
        sb.appendln("->->->->->->->->->->->->")
        when(logLevel){
            LogLevel.NONE ->{

            }
            LogLevel.BASIC ->{
                logBasicReq(sb, request, connection)
            }
            LogLevel.HEADERS ->{
                logHeadersReq(sb, request, connection)

            }
            LogLevel.BODY ->{
                logBodyReq(sb, request, connection)
            }
        }
        sb.appendln("->->->->->->->->->->")
        logIt(sb)
    }

    private fun logBodyReq(sb:StringBuilder,request: Request,connection: Connection?){
        logHeadersReq(sb, request, connection)
        sb.appendln("RequestBody:${request.body.toString()}")
    }
    private fun logHeadersReq(sb:StringBuilder,request: Request,connection: Connection?){
        logBasicReq(sb, request, connection)
       val headersStr=request.headers.joinToString(""){
           header->
           "请求 Header:{${header.first}=${header.second}}\n"
       }
        sb.appendln(headersStr)
    }

    private fun logBasicReq(sb:StringBuilder,request: Request,connection: Connection?){
        sb.appendln("请求 method:${request.method} url:${decodeUrlStr(request.url.toString())}" +
                "tag:${request.tag()}protocol:${connection?.protocol()?:Protocol.HTTP_1_1}")
    }


    /**
     * 记录响应日志
     * [response]响应数据
     */
    private fun logResponse(response: Response){
        val sb=StringBuffer()
        sb.append("\r\n")
        sb.appendln("->->->->->->->->->->->->")
        when(logLevel){
            LogLevel.NONE ->{

            }
            LogLevel.BASIC ->{
              logBasicRsp(sb ,response)
            }
            LogLevel.HEADERS ->{
                logHeadersRsp(response, sb)
            }
            LogLevel.BODY ->{
                 logHeadersRsp(response, sb)
                kotlin.runCatching {
                    //peek类似于clone数据流，监视，窥探，不能直接用原来的body的string流数据作为日志
                    //会消费掉io，所以这里是peek，监测
                    val peekBody=response.peekBody(1024*1024)//缓存大小
                    sb.appendln(peekBody.string())
                }.getOrNull()
            }
        }
        sb.appendln("<<-<<-<<-<<-<<-<<-<<-<<")
        logIt(sb, ColorLevel.INFO)

    }
    //用于url编码的string解码
    private fun decodeUrlStr(url:String):String?{
        return kotlin.runCatching {
            URLDecoder.decode(url,"utf-8")
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()
    }

    //打印日志
    //any需要打印的数据对象
    //tempLevel便于临时调整打印color等级
    private fun logIt(any: Any, tempLevel: ColorLevel?=null){
        when(tempLevel?:colorLevel){
            ColorLevel.VERBOSE ->Log.v(logTag,any.toString())
            ColorLevel.DEBUG ->Log.d(logTag,any.toString())
            ColorLevel.INFO ->Log.i(logTag,any.toString())
            ColorLevel.WARN ->Log.w(logTag,any.toString())
            ColorLevel.ERROR ->Log.e(logTag,any.toString())
        }
    }
    private fun logHeadersRsp(response: Response,sb:StringBuffer){
        logBasicRsp(sb,response)
        val  headerStr=response.headers.joinToString(separator = ""){
            header->
            "响应 Header：{${header.first}=${header.second}}\n"
        }
        sb.appendln(headerStr)
    }

    private fun logBasicRsp(sb:StringBuffer,response: Response){
        sb.appendln("响应 protocol:${response.protocol} code:${response.code} message:${response.message}")
            .appendln("响应 request Url: ${decodeUrlStr(response.request.url.toString())}")
            .appendln("响应 sentRequestTime:${
                toDataTimeStr(response.sentRequestAtMillis,
                MILLIS_PATTERN
                )
            }receiveResponseTime:${
                toDataTimeStr(response.receivedResponseAtMillis,
                MILLIS_PATTERN
                )
            }")
    }

    companion object{
        private const val TAG="<KtHttp>"//默认的TAG

        //时间格式化
        const val MILLIS_PATTERN="yyyy-MM-dd HH:mm:ss.SSSXXX"
        //转化为格式化的时间字符串
        fun toDataTimeStr(millis:Long,pattern:String):String{
            return SimpleDateFormat(pattern, Locale.getDefault()).format(millis)
        }
    }
}