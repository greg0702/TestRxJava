package my.com.testrxjava

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val observable: Observable<Task> = Observable
            .fromIterable(DataSource.createTaskList())
            .subscribeOn(Schedulers.io()) //specify which thread to do it on & all things trying to see result need subscribe to it
            .filter(Predicate { task ->

                //filter all task to only return those that is completed

                Log.d(TAG,"test: " + Thread.currentThread().name)

                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException){
                    e.printStackTrace()
                }

                return@Predicate task.isComplete()

            })
            .observeOn(AndroidSchedulers.mainThread()) //specify where the result will be observe on

        observable.subscribe(object : Observer<Task> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG,"onSubscribe: called.")
            }

            override fun onNext(task: Task) {
                Log.d(TAG, "onNext: " + Thread.currentThread().name)
                Log.d(TAG, "onNext: " + task.getDescription())

            }

            override fun onError(e: Throwable) {
                Log.e(TAG,"onError: ", e)
            }

            override fun onComplete() {
                Log.d(TAG,"onComplete: called.")
            }
        })

    }
}