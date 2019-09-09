package com.poronga.batovi.viewmodel.main

import androidx.lifecycle.ViewModel
import com.poronga.batovi.model.json.Project
import com.poronga.batovi.model.json.Task
import java.sql.Date

class MainViewModel: ViewModel() {
    var newProjectDifficulty: Int = 0
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
        ),
        Project(
            id = 1,
            name = "Pokedex",
            description = "Multiplatform application that obtains the data from PokeAPI.",
            tags = mutableListOf("android", "swift", "pokemon", "api", "games"),
            languages = mutableListOf("kotlin", "java", "swift"),
            tasks = mutableListOf(
                Task(
                    name = "Logic iOS",
                    description = "Logic side of the iOS app",
                    completed = true,
                    difficulty = 2
                ),
                Task(
                    name = "Visual iOS",
                    description = "Visual side of the iOS app",
                    completed = true,
                    difficulty = 2
                ),
                Task(
                    name = "Logic Android",
                    description = "Logic side of the Android app",
                    completed = true,
                    difficulty = 2
                ),
                Task(
                    name = "Visual Android",
                    description = "Visual side of the Android app",
                    completed = true,
                    difficulty = 2
                )
            ),
            dateCreated = Date.valueOf("2019-08-01"),
            dateFinish = Date.valueOf("2019-08-06"),
            thumbnailURL = "https://cdn.bulbagarden.net/upload/6/6c/Generation_I_Pokedex.png",
            difficulty = 2,
            completed = true
        ),
        Project(
            id = 1,
            name = "Transformers Blog",
            description = "Blog about the Transformers",
            tags = mutableListOf("meme", "web", "webapp", "transformers", "angular"),
            languages = mutableListOf("javascript", "html", "css"),
            tasks = mutableListOf(
                Task(
                    name = "Database",
                    description = "NoSQL Database",
                    completed = true,
                    difficulty = 1
                ),
                Task(
                    name = "NodeJS Backend",
                    description = "Backend of the app",
                    completed = true,
                    difficulty = 1
                ),
                Task(
                    name = "Angular frontend",
                    description = "Frontend of the app with Angular 8",
                    completed = true,
                    difficulty = 2
                ),
                Task(
                    name = "Visual frontend",
                    description = "HTML and CSS work",
                    completed = true,
                    difficulty = 2
                )
            ),
            dateCreated = Date.valueOf("2014-01-01"),
            dateFinish = Date.valueOf("2014-02-10"),
            thumbnailURL = "https://www.sideshow.com/storage/product-images/902764/optimus-prime-transformers-generation-1_transformers_silo.png",
            difficulty = 1,
            completed = true
        ),
        Project(
            id = 1,
            name = "S.G.VEN",
            description = "Integral system for management and selling of tickets for events.",
            tags = mutableListOf("informix", "desktop", "visual studio", "windows"),
            languages = mutableListOf("sql", "visual basic"),
            tasks = mutableListOf(
                Task(
                    name = "Planning",
                    description = "Planning of the application (models, technologies, learning, etc...)",
                    completed = true,
                    difficulty = 3
                ),
                Task(
                    name = "Database",
                    description = "SQL Database with Informix",
                    completed = true,
                    difficulty = 3
                ),
                Task(
                    name = "App backend",
                    description = "Backend of the application which connects it to the DB",
                    completed = true,
                    difficulty = 3
                ),
                Task(
                    name = "App frontend",
                    description = "Frontend of the application, visual and logic of it",
                    completed = true,
                    difficulty = 3
                )

            ),
            dateCreated = Date.valueOf("2018-06-20"),
            dateFinish = Date.valueOf("2018-11-10"),
            thumbnailURL = "https://therockandblues.com/wp-content/uploads/2018/07/tickets.png",
            difficulty = 4,
            completed = true
        ),
        Project(
            id = 1,
            name = "Absolutely Meme",
            description = "This is just sample data, sorry JUEJUEJUE",
            tags = mutableListOf("meme", "more meme", "even more meme", "absolutely insane meme"),
            languages = mutableListOf("assembler"),
            tasks = mutableListOf(
                Task(
                    name = "Meme side",
                    description = "Meme side of the app",
                    completed = false,
                    difficulty = 2
                ),
                Task(
                    name = "Nothins side",
                    description = "This is nothing",
                    completed = false,
                    difficulty = 3
                )
            ),
            dateCreated = Date.valueOf("2019-09-07"),
            dateFinish = Date.valueOf("2019-09-08"),
            thumbnailURL = "https://image.europafm.com/clipping/cmsimages02/2017/05/29/A7F37592-3DAF-439C-9390-7A2FB0F90E9B/51.jpg",
            difficulty = 2,
            completed = false
        )
    )

}