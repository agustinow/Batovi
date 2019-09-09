package com.poronga.batovi.model.json

import java.util.*
/*
*
**
***
****
*****ADD IDEAS PLEASE***
****
***
**
*
*/

class User(var id: Int? = null, var name: String?, var lvl: Int = 1, var xp: Int = 0, var image: String? = "https://i1.wp.com/www.sopitas.com/wp-content/uploads/2015/01/bad_luck_brian-e1420582077129.jpeg")

class Project(var id: Int? = null, var name: String? = null, var description: String? = null,//Maybe participants?
              var tags: MutableList<String> = mutableListOf(),
              var languages: MutableList<String> = mutableListOf(),
              var tasks: MutableList<Task> = mutableListOf(), var dateCreated: Date? = null,
              var dateFinish: Date? = null, var thumbnailURL: String? = null,//Difficulty: 1 easy, 2 normal, 3 hard
              var difficulty: Int? = null, var completed: Boolean = false
)

class Task(var name: String? = null, var description: String? = null,
           var completed: Boolean = false,//Difficulty: 1 easy, 2 normal, 3 hard
           val difficulty: Int? = null
)