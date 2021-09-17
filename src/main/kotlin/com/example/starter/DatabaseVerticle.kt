package com.example.starter

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.eventbus.EventBus
import io.vertx.core.eventbus.Message
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.json.JsonObject

class DatabaseVerticle : AbstractVerticle() {
  override fun start(startPromise: Promise<Void>) {
    println("db verticle started")
    var eventBus: EventBus = vertx.eventBus()
    var consumer: MessageConsumer<String> = eventBus.consumer("db-address")

    consumer.handler { message ->
      run {
        println("I have received message via event bus")
        println("message: ${message.body()}")
//        val action: String = message.body().getString("action")
//
//        when (action) {
//          "getTodos" -> getTodos(message)
//          else -> message.fail(1, "unknown action" + message.body())
//        }
      }
    }
    startPromise.complete()
  }

}
