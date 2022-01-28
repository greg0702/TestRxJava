package my.com.testrxjava

class Task(private var description: String, private var isComplete: Boolean,
           private var priority: Int
) {

    //
//    fun taskObj(description: String, isComplete: Boolean, priority: Int) {
//        this.description = description
//        this.isComplete = isComplete
//        this.priority = priority
//    }

    fun getDescription(): String{ return description }

    fun setDescription(description: String){ this.description = description }

    fun isComplete(): Boolean{ return isComplete }

    fun setComplete(isComplete: Boolean){ this.isComplete = isComplete }

    fun getPriority(): Int{ return priority }

    fun setPriority(priority: Int){ this.priority = priority }

}