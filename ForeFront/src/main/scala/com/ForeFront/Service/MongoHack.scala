package com.ForeFront.Service

import com.mongodb.casbah.MongoConnection
import com.Mp3Digger.Repository.{Post, MongoPostRepository, PostRepository}

object MongoHack {
  //  def getRepository(): PostRepository = {
  //    val con = MongoConnection("localhost")
  //    val db = con("Mp3UsenetIntTest")
  //    new MongoPostRepository(db)
  //  }

  def getSomeShit(skip: Int,  limit: Int): List[Post] = {
    val con = MongoConnection("localhost")
    val db = con("Mp3UsenetIntTest")
    val someShit = new MongoPostRepository(db).findSomeShit(skip, limit)
    con.close()
    someShit
  }
}
