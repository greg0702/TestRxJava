package my.com.testrxjava.bufferoperator

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import my.com.testrxjava.R
import java.util.concurrent.TimeUnit


class TrackUiExample : AppCompatActivity() {

    private val TAG = "TrackUiExample"

    // global disposables object
    var disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_ui_example)

        val button: Button = findViewById(R.id.button2)

        // detect clicks to a button
        button.clicks()
            .map {
                // convert the detected clicks to an integer
                return@map 1
            }
            .buffer(4, TimeUnit.SECONDS) // capture all the clicks during a 4 second interval
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<Int>> {

                override fun onSubscribe(d: Disposable) {
                disposables.add(d) // add to disposables to be cleared in onDestroy
                }

                override fun onNext(integers: List<Int>) {
                    Log.d(TAG, "onNext: You clicked " + integers.size + " times in 4 seconds!")
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {}
            })

    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

}