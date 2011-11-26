package com.ForeFront.Test

import org.scalatest.FunSuite
import org.scalatra.test.scalatest.ScalatraSuite
import com.ForeFront.ForeFrontServlet


class ScalatraBootTest extends ScalatraSuite with FunSuite {
  addServlet(classOf[ForeFrontServlet], "/*")

  test("get index") {
    get("/") {
      status should equal(200)
      body should include("Files In Database")
    }
  }

  test("get first file list page") {
    get("/fileList/") {
      status should equal(200)
      body should include("Files In Database")
    }
  }

  test("get page after first page of file list page") {
    get("/fileList/1") {
      status should equal(200)
      body should include("Files In Database")
    }
  }

  test("invalid page id of file list page") {
    get("/fileList/a") {
      status should equal(400)
      body should include("Invalid page")
    }
  }
}