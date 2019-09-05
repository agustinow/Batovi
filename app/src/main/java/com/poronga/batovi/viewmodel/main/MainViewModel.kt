package com.poronga.batovi.viewmodel.main

import androidx.lifecycle.ViewModel
import com.poronga.batovi.model.json.Project
import com.poronga.batovi.model.json.Task
import java.sql.Date

class MainViewModel: ViewModel() {
    var projects: MutableList<Project> = mutableListOf(
        Project(
            id = 1,
            name = "Bacchus",
            description = "Application for a wine store made with Kotlin for Android",
            tags = mutableListOf("android", "wine", "shop", ".net", "fullstack", "mongodb"),
            languages = mutableListOf("kotlin", "java", "c#", "json"),
            tasks = mutableListOf(
                Task(
                    name = "Create Database",
                    description = "Create the Database of the project",
                    completed = true,
                    difficulty = 1
                ),
                Task(
                    name = "Create API",
                    description = "Create the API of the project",
                    completed = true,
                    difficulty = 2
                ),
                Task(
                    name = "Logic",
                    description = "Logic side of the app",
                    completed = true,
                    difficulty = 2
                ),
                Task(
                    name = "UI",
                    description = "Visual side of the app",
                    completed = false,
                    difficulty = 2
                )
            ),
            dateCreated = Date.valueOf("2019-08-12"),
            dateFinish = Date.valueOf("2019-12-01"),
            thumbnailURL = "https://upload.wikimedia.org/wikipedia/commons/3/3c/Red_and_white_wine_12-2015.jpg",
            difficulty = 2,
            completed = false
        ),
        Project(
            id = 2,
            name = "VetApp",
            description = "Multiplatform application for veterinarians (iOS and Android)",
            tags = mutableListOf("android", "ios", "pets", ".net", "fullstack", "veterinarian", "mongodb"),
            languages = mutableListOf("kotlin", "java", "swift", "c#", "json"),
            tasks = mutableListOf(
                Task(
                    name = "Create Database",
                    description = "Create the Database of the project",
                    completed = true,
                    difficulty = 1
                ),
                Task(
                    name = "Create API",
                    description = "Create the API of the project",
                    completed = false,
                    difficulty = 3
                ),
                Task(
                    name = "Logic",
                    description = "Logic side of the app",
                    completed = true,
                    difficulty = 3
                ),
                Task(
                    name = "UI",
                    description = "Visual side of the app",
                    completed = false,
                    difficulty = 3
                )
            ),
            dateCreated = Date.valueOf("2019-08-21"),
            dateFinish = Date.valueOf("2019-12-01"),
            thumbnailURL = "https://i.ytimg.com/vi/8VFepDvHcws/maxresdefault.jpg",
            difficulty = 3,
            completed = false
        ),
        Project(
            id = 1,
            name = "MyCoffee",
            description = "this@MyCoffee xDXdXDxdXD",
            tags = mutableListOf("android", "coffee", "portfolio", "brown"),
            languages = mutableListOf("kotlin", "java"),
            tasks = mutableListOf(
                Task(
                    name = "Logic",
                    description = "Logic side of the app",
                    completed = false,
                    difficulty = 2
                ),
                Task(
                    name = "UI",
                    description = "Visual side of the app",
                    completed = false,
                    difficulty = 2
                )
            ),
            dateCreated = Date.valueOf("2019-09-05"),
            dateFinish = Date.valueOf("2019-12-01"),
            thumbnailURL = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2018/04/15/16/china-coffee-cup.jpg",
            difficulty = 1,
            completed = false
        )
    )

}