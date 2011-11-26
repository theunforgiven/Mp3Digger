package com.ForeFront

import org.scalatra._
import scalate.ScalateSupport
import Service.ArticleService
import org.slf4j._
import javax.servlet.ServletConfig
import scala.Int

class ForeFrontServlet extends ScalatraServlet with ScalateSupport with Initializable {
  def logger = LoggerFactory.getLogger("ForeFrontServlet")

  val articleService = new ArticleService()

  before() {
    contentType = "text/html"
  }

  get("/") {
    renderFilesPage()
  }
  get("/fileList/") {
    renderFilesPage()
  }

  get("/fileList/:page") {
    val pageInput: Option[String] = params.get('page).headOption
    parsePage(pageInput) match {
      case Some(x: Int) => {
        renderFilesPage(x)
      }
      case None => {
        response.sendError(400, "Invalid page \"%s\" specified.".format(pageInput.getOrElse("")))
        //        resourceNotFound()
      }
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

  def parsePage(option: Option[String]): Option[Int] = {
    option match {
      case Some(x: String) => {
        try {
          Some(x.toInt)
        } catch {
          case _ => Option.empty
        }
      }
      case None => Option.empty
    }
  }

  def renderFilesPage(offset: Int = 0): String = {
    val bodyTemplate = Map("files" -> articleService.viewArticles(offset * 100, 100), "currentOffset" -> offset)
    templateEngine.layout("/index.ssp", bodyTemplate)
  }

  override def initialize(config: ServletConfig): Unit = {
    super.initialize(config)
    logger.info("Initializing servlet.")
  }
}