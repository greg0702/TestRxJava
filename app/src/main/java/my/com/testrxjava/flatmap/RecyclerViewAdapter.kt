package my.com.testrxjava.flatmap


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.com.testrxjava.R
import my.com.testrxjava.flatmap.models.Post


class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    // create a list for posts
    private var posts: List<Post> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_post_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun setPosts(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    fun updatePost(post: Post) {
        val getPost = posts.toMutableList()

        getPost[posts.indexOf(post)] = post

        notifyItemChanged(posts.indexOf(post))
    }

    fun getPosts(): List<Post> {
        return posts
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var title: TextView = itemView.findViewById(R.id.title)
        private var numComments: TextView = itemView.findViewById(R.id.num_comments)
        private var progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)

        fun bind(post: Post) {
            title.text = post.getTitle()
            if (post.getComments() == null) {
                showProgressBar(true)
                numComments.text = ""
            } else {
                showProgressBar(false)
                numComments.text = post.getComments()!!.size.toString()
            }
        }

        private fun showProgressBar(showProgressBar: Boolean) {
            if (showProgressBar) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }

    }
}