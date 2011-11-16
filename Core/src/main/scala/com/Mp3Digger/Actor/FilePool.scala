package com.Mp3Digger.Actor

import akka.actor.Actor._
import akka.routing._
import akka.actor.Actor
class FilePool(poolSizeLimit: Int) extends Actor with DefaultActorPool
                               with FixedCapacityStrategy
                               with SmallestMailboxSelector
{
   def receive = _route
   def partialFill = true
   def selectionCount = 1
   def instance() = actorOf(new FileActor())

  def limit = poolSizeLimit
}

