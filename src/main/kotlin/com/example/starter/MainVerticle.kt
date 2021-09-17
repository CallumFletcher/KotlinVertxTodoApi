package com.example.starter

import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.core.Promise
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

class MainVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
    val requestRouter = createRouter()
    val baseRouter = Router.router(vertx)
    baseRouter.mountSubRouter("/api", requestRouter)

    vertx
      .createHttpServer()
      .requestHandler(baseRouter)
      .listen(8080) { http ->
        if (http.succeeded()) {
          startPromise.complete()
          println("HTTP server started on port 8080")
        } else {
          startPromise.fail(http.cause());
        }
      }
    vertx.deployVerticle(DatabaseVerticle())
  }

  private fun createRouter() = Router.router(vertx).apply {
    get("/unread").handler(unreadHandler)
    get("/all/:message").handler(allTodosHandler)
    get("/urgent").handler(urgentTodosHandler)

  }

  private val unreadHandler = Handler<RoutingContext> { req ->
    val map = mapOf("hello" to "there", "map" to "test")
    req.json(map)
  }
  private val allTodosHandler = Handler<RoutingContext> { req ->

    vertx.eventBus().send("db-address", req.pathParam("message"))
    req.response().statusCode = 200;
    req.response().end()

  }
  private val urgentTodosHandler = Handler<RoutingContext> { req ->
    req.response().end("urgent todos")
  }


}
