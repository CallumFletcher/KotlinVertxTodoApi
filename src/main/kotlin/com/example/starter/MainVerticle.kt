package com.example.starter

import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.core.Promise
import io.vertx.ext.web.Router
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext

class MainVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
    val router = createRouter()

    vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(8888) { http ->
        if (http.succeeded()) {
          startPromise.complete()
          println("HTTP server started on port 8888")
        } else {
          startPromise.fail(http.cause());
        }
      }
  }

  private fun createRouter() = Router.router(vertx).apply {
    get("/").handler(handlerRoot)
    get("/all").handler(handlerAllTodos)
    get("/urgent").handler(handlerUrgentTodos)
  }

  val handlerRoot = Handler<RoutingContext> { req ->
    req.response().end("root")
  }
  val handlerAllTodos = Handler<RoutingContext> { req ->
    req.response().end("All Todos")
  }
  val handlerUrgentTodos = Handler<RoutingContext> { req ->
    req.response().end("urgent todos")
  }


}
