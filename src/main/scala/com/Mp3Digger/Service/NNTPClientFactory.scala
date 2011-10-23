package com.Mp3Digger.Service

import org.apache.commons.net.nntp.NNTPClient

class NNTPClientFactory(hostName: String,  userName: String,  password: String){
  def createClient(): NNTPClient = {
    val ntpClient = new NNTPClient()
    ntpClient.connect(hostName)
    ntpClient.authenticate(userName, password)
    ntpClient
  }

}

