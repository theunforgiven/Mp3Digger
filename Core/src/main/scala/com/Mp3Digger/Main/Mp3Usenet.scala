package com.Mp3Digger.Main

import org.apache.commons.net.nntp.NNTPClient
import java.util.concurrent.CountDownLatch
import akka.actor.Actor._
import com.Mp3Digger.Actor.Messages.FetchArticles
import com.Mp3Digger.Service.NNTPClientFactory
import com.Mp3Digger.Actor.{FilePool, NtpPool, Director}

object Mp3Usenet {
  def createConnection(hostname: String, userName: String, password: String) : NNTPClient = {
    val client = new NNTPClient()
    client.connect(hostname);
    if (!client.authenticate(userName, password)) {
      println("Authentication failed for user " + userName + "!")
      sys.exit(1)
    }
    client
  }

  def printUsenetHelp {
    val client = createConnection("localhost", "", "")
    println(client.listHelp())
    client.disconnect()
  }

  def main(args: Array[String]) {
        // this latch is only plumbing to know when the calculation is completed
    val latch = new CountDownLatch(1)

    val ntpPool = actorOf(new NtpPool(8, new NNTPClientFactory("localhost", "x", "x"))).start()
    val filePool = actorOf(new FilePool(8)).start()

    // create the master
    val master = actorOf(new Director(8, 100000, ntpPool, filePool, latch)).start()

    // start the calculation
    master ! FetchArticles(0, "alt.binaries.music.mp3")

    // wait for master to shut down
    latch.await()
  }
}

