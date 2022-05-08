package com.example

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.*
import io.ktor.server.testing.*
import com.example.routes.*
import com.example.database.*
import com.example.domain.Coffee

class ApplicationTest {
    @Test
    fun testRoutes() = testApplication {
        application {
            configureRouting()
        }

        client.get("/list").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertContains(bodyAsText(), "Coffee", false, "No 'Coffee' in body")
        }

        val resp: HttpResponse = client.post("/coffee") {
            contentType(ContentType.Application.Json)
            setBody(
                toJson(
                    Coffee("President", 5.80, 0.500, 2)
                )
            )
        }
        assertEquals(HttpStatusCode.Created, resp.status)
    }
}
