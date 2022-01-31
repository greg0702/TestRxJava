package my.com.testrxjava.debounce

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import my.com.testrxjava.R
import java.util.concurrent.TimeUnit


class MainActivity4 : AppCompatActivity() {

    private val TAG = "MainActivity4"

    private val disposables = CompositeDisposable()
    private var timeSinceLastRequest : Long = 0 // for log printouts only; not part of logic.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        //declare ui
        val searchView: SearchView = findViewById(R.id.search_view)

        timeSinceLastRequest = System.currentTimeMillis()

        //debounce operator filters out items emitted by source observables that are rapidly followed by another (example: Instagram Search)
        // create the Observable
        val observableQueryText: Observable<String> = Observable
            .create(ObservableOnSubscribe<String> { emitter ->
                // Listen for text input into the SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        if (!emitter.isDisposed) {
                            emitter.onNext(newText) // Pass the query to the emitter
                        }
                        return false
                    }
                })
            })
            .debounce(500, TimeUnit.MILLISECONDS) // Apply Debounce() operator to limit requests
            .subscribeOn(Schedulers.io())

        // Subscribe an Observer
        observableQueryText.subscribe(object : Observer<String> {

            override fun onSubscribe(d: Disposable) { disposables.add(d) }

            override fun onNext(s: String) {
                Log.d(TAG, "onNext: time  since last request: " + (System.currentTimeMillis() - timeSinceLastRequest))
                Log.d(TAG, "onNext: search query: $s")
                timeSinceLastRequest = System.currentTimeMillis()

                // method for sending a request to the server
                sendRequestToServer(s)
            }

            override fun onError(e: Throwable) {}

            override fun onComplete() {}
        })

        //can use SwitchMap() to terminate previous queries to make sure no multiple queries running at same time

    }

    // Fake method for simulating sending a request to the server
    private fun sendRequestToServer(query: String) {
        // do nothing
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear() // clear disposables
    }

}