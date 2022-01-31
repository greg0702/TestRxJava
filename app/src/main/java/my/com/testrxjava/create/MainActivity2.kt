package my.com.testrxjava.create

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import my.com.testrxjava.R

class MainActivity2 : AppCompatActivity() {

    private val TAG = "MainActivity2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //val task = Task("Walk the dog", false, 4)

        //val tasks = DataSource.createTaskList()

        //create operator most flexible
//        val taskObservable: Observable<Task> = Observable
//            .create(ObservableOnSubscribe<Task> {
//
//                // create observable out of single object
////                if (!it.isDisposed){
////                    it.onNext(task)
////                    it.onComplete()
////                }
//
//                //create observable out of a list of object
//                for (task in tasks){
//                    if (!it.isDisposed){
//                        it.onNext(task)
//                    }
//                }
//
//                if (!it.isDisposed){
//                    it.onComplete()
//                }
//
//            }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())

//        //create just a single observable or just a small list of observables with max 10 objects
//        val taskObservable: Observable<Task> = Observable
//            .just(task)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())

        // range go through a range of number
//        //create observable out of range of value (better use to replace loop with expensive operations)
//        val intObservable: Observable<Task> = Observable
//            .range(0, 9) //start value is inclusive, end number is exclusive
//            .subscribeOn(Schedulers.io())
//            .map { int ->
//                Log.d(TAG, "apply: " + Thread.currentThread().name)
//                Task("This is a task with priority: $int", false, int)
//            }
//            .takeWhile { task -> task.getPriority() < 9 } // keep emitting until priority reaches 9
//            .observeOn(AndroidSchedulers.mainThread())
//
//        intObservable.subscribe(object: Observer<Task>{
//            override fun onSubscribe(d: Disposable) {}
//
//            override fun onNext(task: Task) {
//                Log.d(TAG, "onNext: " + task.getPriority())
//            }
//
//            override fun onError(e: Throwable) {}
//
//            override fun onComplete() {}
//
//        })

        //repeat will repeat what is needed to be done for a specified amount of times
        //create observable out of range of value (better use to replace loop with expensive operations)
        val intObservable: Observable<Int> = Observable
            .range(0, 3) //start value is inclusive, end number is exclusive
            .subscribeOn(Schedulers.io())
            .repeat(3)
            .observeOn(AndroidSchedulers.mainThread())

        intObservable.subscribe(object: Observer<Int>{
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(int: Int) {
                Log.d(TAG, "onNext: $int")
            }

            override fun onError(e: Throwable) {}

            override fun onComplete() {}

        })

//        taskObservable.subscribe(object: Observer<Task>{
//            override fun onSubscribe(d: Disposable) {}
//
//            override fun onNext(task: Task) {
//                Log.d(TAG, "onNext: " + task.getDescription())
//            }
//
//            override fun onError(e: Throwable) {}
//
//            override fun onComplete() {}
//
//        })

    }
}