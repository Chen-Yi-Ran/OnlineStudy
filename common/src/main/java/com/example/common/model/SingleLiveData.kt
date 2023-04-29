package com.example.common.model

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveData<T> :MutableLiveData<T>(){

    protected val mPending=AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if(hasActiveObservers()){
            Log.w("SingleLiveData", "多个观察者存在的时候，只会有一个被通知到数据更新", )
        }

        super.observe(owner, Observer {
            t->
            if(mPending.compareAndSet(true,false)){
                observer.onChanged(t)
            }
        })
    }

    override fun setValue(value: T?) {
        mPending.set(true)
        super.setValue(value)
    }
    @MainThread
    fun call(){
        value =null
    }

}