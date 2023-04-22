package com.example.onlinestudy

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Test {

}fun main() {
    //launch是异步不阻塞线程,开辟一个协程去异步执行下面方法，虽然Thread.sleep会阻塞当前线程,并阻塞该线程的
    // 所有协程但是是作用与
    //launch里面，所以他也是阻塞的是开辟的协程
    GlobalScope.launch {
        println("sss${Thread.currentThread()}")
        println("codes run in coroutine scope")
        delay(1000)//挂起当前协程
        println("sss${Thread.currentThread()}")
        println("codes run in coroutine scope finished")
    }
    GlobalScope.launch {
        println(Thread.currentThread())
        println("codes run in coroutine scope")
     //   delay(1000)//挂起当前协程
        println("codes run in coroutine scope finished")
    }

    runBlocking {
        launch {

        }
    }
    println(Thread.currentThread())
    Thread.sleep(3000)
}