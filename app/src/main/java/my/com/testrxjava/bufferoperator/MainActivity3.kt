package my.com.testrxjava.bufferoperator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import my.com.testrxjava.DataSource
import my.com.testrxjava.R
import my.com.testrxjava.Task


class MainActivity3 : AppCompatActivity() {

    private val TAG = "MainActivity3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        // Create an Observable
        val taskObservable: Observable<Task> = Observable
            .fromIterable(DataSource.createTaskList())
            .subscribeOn(Schedulers.io())

        //buffer operator emits all in a bundle specified
        // good for use to perform network operations; only pass through if have acquire certain amount of data
        taskObservable
            .buffer(2) // Apply the Buffer() operator
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<Task>> {

                // Subscribe and view the emitted results
               override fun onSubscribe(d: Disposable) {}

                override fun onNext(tasks: List<Task>) {
                    Log.d(TAG, "onNext: bundle results: -------------------")
                    for (task in tasks) {
                        Log.d(TAG, "onNext: " + task.getDescription())
                    }
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {}

            })

    }
}