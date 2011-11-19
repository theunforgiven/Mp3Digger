package bootstrap.liftweb

import _root_.net.liftweb.http._
import net.liftweb.scalate.ScalateView
import com.ForeFront.lib.ArticleServices

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    // lets add Scalate
    val scalateView = new ScalateView
    scalateView.register

    LiftRules.addToPackages("com.ForeFront")
    LiftRules.statelessDispatchTable.append(ArticleServices)
    LiftRules.localeCalculator = r => LiftRules.defaultLocaleCalculator(r)

    /* Build SiteMap
    ///
        val entries = Menu(Loc("Home", List("index"), "Home")) ::
        Menu(Loc("Request Details", List("request"), "Request Details")) ::
        Article.sitemap

        LiftRules.setSiteMap(SiteMap(entries:_*))
    */
  }
}