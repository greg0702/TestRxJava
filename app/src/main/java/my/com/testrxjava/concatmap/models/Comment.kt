package my.com.testrxjava.concatmap.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//create comment object
class Comment {

    @SerializedName("postId")
    @Expose
    private var postId = 0

    @SerializedName("id")
    @Expose
    private var id = 0

    @SerializedName("name")
    @Expose
    private var name: String = ""

    @SerializedName("email")
    @Expose
    private var email: String = ""

    @SerializedName("body")
    @Expose
    private var body: String = ""

    fun Comment(postId: Int, id: Int, name: String, email: String, body: String) {
        this.postId = postId
        this.id = id
        this.name = name
        this.email = email
        this.body = body
    }


    fun getPostId(): Int {
        return postId
    }

    fun setPostId(postId: Int) {
        this.postId = postId
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getEmail(): String {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getBody(): String {
        return body
    }

    fun setBody(body: String) {
        this.body = body
    }

}