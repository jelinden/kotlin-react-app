package com.example.routes

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.html.*
import com.google.gson.Gson

import java.io.*
import java.nio.file.*
import java.time.*
import java.time.format.*

import com.example.database.*
import com.example.util.*
import com.example.domain.Coffee

import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*

fun Application.configureRouting() {

    val db: Database = Database()
    install(ContentNegotiation) {
        gson() { 
        setPrettyPrinting() }
    }

    var env: String = System.getenv("env") ?: "dev"
    var path = getPathByEnv(env)

    routing {
        // when serving from local, give the client build directory
        // in production, assume build directory is in current directory
        static("/") {
            staticRootFolder = File(path)
            files("build")
            default("build/index.html")
        }

        static("/edit") {
            staticRootFolder = File(path)
            files("build")
            default("build/index.html")
        }

        get("/list") {
            var coffees = db.Select("select * from coffee;") as Array<Coffee>
            
            call.respondHtml(HttpStatusCode.OK) {
                head {
                    title {

                    }
                }
                body {
                    h1 {
                        +"Coffee list"
                    }
                    coffees.forEach {
                        div {
                            +"$it"
                        }
                    }
                }
            }
        }
        get("/coffees") {
            val coffees = db.Select("select * from coffee;") as Array<Coffee>
            call.respondText(toJson(coffees), ContentType.Application.Json, HttpStatusCode.OK)
        }

        get("/coffee/{id}") {
            val id = call.parameters["id"]
            if (id != null && isIdValid(id)) {
                val coffee = db.SelectOne("select * from coffee where id = ?", id) as Coffee?
                if (coffee != null) {
                    call.respondText(toJson(coffee), ContentType.Application.Json, HttpStatusCode.OK)
                } else {
                    call.respondText("404", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("Failed", status = HttpStatusCode.BadRequest)
            }
        }

        post("/coffee") {
            val coffee = call.receive<Coffee>()

            db.Insert("insert into coffee values (?,?,?,?,?)"
                , listOf<Any>(
                    RandomString(16), 
                    coffee.name, 
                    coffee.price, 
                    coffee.weight, 
                    coffee.roastlevel
                )
            )
            call.respondText("OK", status = HttpStatusCode.Created)
        }
        put("/coffee") {
            val coffee = call.receive<Coffee>()

            db.Update("update coffee set"
                .plus(" name=?,")
                .plus(" price=?,")
                .plus(" weight=?,")
                .plus(" roastlevel=?")
                .plus(" where id=?;"), listOf<Any>(coffee.name, coffee.price, coffee.weight, coffee.roastlevel, coffee.id)
            )
            call.respondText("OK", status = HttpStatusCode.Created)
        }
        delete("/coffee/{id}") {
            val id = call.parameters["id"]
            if (id != null && isIdValid(id)) {
                db.Insert("delete from coffee where id = ?;", listOf<Any>(id))
                call.respondText("OK DELETED", status = HttpStatusCode.OK)
            } else {
                call.respondText("Failed", status = HttpStatusCode.BadRequest)
            }
        }
    }
}

// return the path static files are in
fun getPathByEnv(env: String): String {
    println(env + " " + Paths.get("").toAbsolutePath().toString())
    if (env == "dev") {
        return "../client"
    }
    return "."
}

fun toJson(coffees: Array<Coffee>): String {
    return Gson().toJson(Data(coffees))
}

fun toJson(coffee: Coffee): String {
    return Gson().toJson(coffee)
}

fun isIdValid(id: String): Boolean {
    val ID_REGEX = "^[a-zA-Z0-9]*$";
    return ID_REGEX.toRegex().matches(id);
}

class Data(val data: Array<Coffee>)
