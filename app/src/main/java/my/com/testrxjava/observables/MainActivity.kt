package my.com.testrxjava.observables

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import my.com.testrxjava.DataSource
import my.com.testrxjava.R
import my.com.testrxjava.Task


class MainActivity : AppCompatActivity() {

    // disposable -> use to dispose of observers after they are not needed or is done using
    private var disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // observables & observers
        val observable: Observable<Task> = Observable
            .fromIterable(DataSource.createTaskList())
            .subscribeOn(Schedulers.io()) //specify which thread to do it on & all things trying to see result need subscribe to it
            .filter(Predicate { task ->

                //filter all task to only return those that is completed
                //filter run in background thread as it is called to be done in io()

                Log.d(TAG,"test: " + Thread.currentThread().name)

                //for observables & observers tutorial
//                try {
//                    Thread.sleep(1000)
//                } catch (e: InterruptedException){
//                    e.printStackTrace()
//                }

                return@Predicate task.isComplete()

            })
            .observeOn(AndroidSchedulers.mainThread()) //specify where the result will be observe on
            //observers will be observing on the main thread
            // shows the use of 2 operators (filter & observeOn())

        observable.subscribe(object : Observer<Task> {

            // subscribe only return disposable if does not have onSubscribe method
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG,"onSubscribe: called.")
                disposables.add(d) //add the disposable to the list
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

        //this allows the disposable to be added directly when call subscribe
//        disposables.add(observable.subscribe(object: Consumer<Task>{
//
//            override fun accept(t: Task?) {
//
//            }
//
//        }))

    }

    override fun onDestroy() {
        //clear disposables when activity is destroyed
        //place in onCleared() inside ViewModel
        super.onDestroy()
        disposables.clear() //this will remove all subscriber & observer but not disabling observables
        //disposables.dispose() //this will not allow anything to subscribe to the observables
    }

}