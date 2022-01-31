package my.com.testrxjava.concatmap.request

import io.reactivex.Observable
import my.com.testrxjava.flatmap.models.Comment
import my.com.testrxjava.flatmap.models.Post
import retrofit2.http.GET
import retrofit2.http.Path


interface RequestApi {

    // get post query and return as observable of list of post
    @GET("posts")
    fun getPosts(): Observable<List<Post>>

    // get comments query and uses the post ID to get the comments and return as observable of list of comments
    @GET("posts/{id}/comments")
    fun getComments(@Path("id") id: Int): Observable<List<Comment>>

}