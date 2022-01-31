package my.com.testrxjava.concatmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import my.com.testrxjava.R
import my.com.testrxjava.flatmap.RecyclerViewAdapter
import my.com.testrxjava.flatmap.models.Post
import my.com.testrxjava.flatmap.request.ServiceGenerator
import java.util.*

class ConcatMapTest : AppCompatActivity() {

    private val TAG = "ConcatMapTest"

    //declare UI elements
    private var recyclerView: RecyclerView? = null

    //create disposable
    private val disposables = CompositeDisposable()

    // recycler view adapter
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concat_map_test)

        recyclerView = findViewById(R.id.recycler_view)

        initRecyclerView()

        getPostsObservable()
            .subscribeOn(Schedulers.io())
            .concatMap { post ->
                return@concatMap getCommentsObservable(post) // create 100 observables sources & return an updated Observable<Post> with comments included
                //concatmap retrieve all posts in order; switchmap do similar things to concatmap and flatmap but only allow one observer to exist at a given time
                //concatmap disadvantage is it slow but with order preserved; flatmap is fast but no order
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<Post> {

                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                override fun onNext(post: Post) {
                    updatePost(post) //update the post in the list
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: ", e)
                }

                override fun onComplete() {}

            })

    }

    //method to retrieve posts
    private fun getPostsObservable(): Observable<Post> {
        //take posts from API
        return ServiceGenerator.getRequestApi()
            .getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { posts ->
                adapter.setPosts(posts) //set posts into recycler view
                return@flatMap Observable.fromIterable(posts)
                    .subscribeOn(Schedulers.io())
            } //return the list of posts as observable
        // allows to map a list of object to become observable of object which can be emitted individually
    }

    private fun updatePost(p: Post) {
        Observable
            .fromIterable(adapter.getPosts())
            .filter { post -> post.getId() == p.getId() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Post> {

                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                override fun onNext(post: Post) {
                    Log.d(TAG, "onNext: updating post: " + post.getId() + ", thread: " + Thread.currentThread().name)
                    adapter.updatePost(post)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: ", e)
                }

                override fun onComplete() {}

            })
    }

    //method to get post with comments
    private fun getCommentsObservable(post: Post): Observable<Post> {
        return ServiceGenerator.getRequestApi()
            .getComments(post.getId())
            .map { comments ->
                val delay: Int = (Random().nextInt(5) + 1) * 1000 // delay time in ms
                Thread.sleep(delay.toLong()) //just use to simulate network request time delay in real life
                Log.d(TAG, "apply: sleeping thread " + Thread.currentThread().name + " for " + delay.toString() + "ms")
                post.setComments(comments) //set comments retrieved
                return@map post
            } // allows to flatten a lot of observables into a single observable
            .subscribeOn(Schedulers.io())
    }

    private fun initRecyclerView() {
        adapter = RecyclerViewAdapter()
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

}