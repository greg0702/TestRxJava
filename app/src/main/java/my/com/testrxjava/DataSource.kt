package my.com.testrxjava

class DataSource {

    companion object{

        private val tasks = listOf(
            Task("Take out the trash", true, 3),
            Task("Walk the dog", false, 2),
            Task("Make my bed", true, 1),
            Task("Unload the dishwasher", false, 0),
            Task("Make dinner", true, 5),
        )

        fun createTaskList(): List<Task> { return tasks }

    }

}