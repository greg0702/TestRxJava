package my.com.testrxjava.throttlefirst

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import my.com.testrxjava.R
import java.util.concurrent.TimeUnit


class MainActivity5 : AppCompatActivity() {

    private val TAG = "MainActivity5"

    private val disposables: CompositeDisposable = CompositeDisposable()
    private var timeSinceLastRequest: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        val button: Button = findViewById(R.id.button)

        timeSinceLastRequest = System.currentTimeMillis()

        //ThrottleFirst() solve button spamming
        // Set a click listener to the button with RxBinding Library
        button.clicks()
            .throttleFirst(4000, TimeUnit.MILLISECONDS) // have the clicks to have 4000 ms must pass before registering a new click
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Unit> {

                override fun onSubscribe(d: Disposable) { disposables.add(d) }

                override fun onNext(unit: Unit) {
                    Log.d(TAG, "onNext: time since last clicked: " + (System.currentTimeMillis() - timeSinceLastRequest))
                    someMethod() // Execute some method when a click is registered
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {}
            })

    }

    private fun someMethod() {
        timeSinceLastRequest = System.currentTimeMillis()
        // Display toast to indicate button click
        Toast.makeText(this, "The button is clicked!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear() // Dispose observable
    }

}