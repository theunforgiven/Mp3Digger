package com.ForeFront

import org.scalatra._
import scalate.ScalateSupport
import Service.ArticleService
import ch.qos.logback._
import org.slf4j._

class ForeFrontServlet extends ScalatraServlet with ScalateSupport {
  def logger = LoggerFactory.getLogger("ForeFrontServlet")
  val articleService = new ArticleService()
  get("/") {
    contentType = "text/html"
    val bodyTemplate = Map("files" -> articleService.viewArticles(0, 100).toString())
    templateEngine.layout("/index.ssp", bodyTemplate)
  }
  get("/articles/*") {
    multiParams("splat").headOption match {
      case Some(x: String) => {
        val offset = x.toInt
        contentType = "text/html"
        val bodyTemplate = Map("files" -> articleService.viewArticles(offset, 100).toString(), "currentOffset" -> offset)
        templateEngine.layout("/index.ssp", bodyTemplate)
      }
      case None => notFound()
    }
  }

  notFound {
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map {
      path =>
        contentType = "text/html"
        layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound()
  }
}