package com.kevinli5506.appta

object CommentsData {
    private val name :String = "Someone"
    private val content :String = "This is Comment"
    val listComments :ArrayList<Comment>
    get() {
        val list : ArrayList<Comment> = arrayListOf<Comment>()
        for (i in 1..5){
            val comment = Comment(name, content)
            list.add(comment)
        }
        return list
    }
}