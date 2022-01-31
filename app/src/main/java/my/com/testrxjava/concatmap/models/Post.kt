package my.com.testrxjava.concatmap.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// create a Post object
class Post {

    @SerializedName("userId")
    @Expose
    private var userId = 0

    @SerializedName("id")
    @Expose
    private var id = 0

    @SerializedName("title")
    @Expose
    private var title: String = ""

    @SerializedName("body")
    @Expose
    private var body: String = ""

    private var comments: List<Comment>? = null

    fun Post(userId: Int, id: Int, title: String, body: String, comments: List<Comment>) {
        this.userId = userId
        this.id = id
        this.title = title
        this.body = body
        this.comments = comments
    }

    fun getUserId(): Int {
        return userId
    }

    fun setUserId(userId: Int) {
        this.userId = userId
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getBody(): String {
        return body
    }

    fun setBody(body: String) {
        this.body = body
    }

    fun getComments(): List<Comment>? {
        return comments
    }

    fun setComments(comments: List<Comment>?) {
        this.comments = comments
    }

    override fun toString(): String {
        return "Post{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}'
    }

}