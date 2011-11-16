package bootstrap.liftweb
import _root_.net.liftweb.common._
import _root_.net.liftweb.util._
import _root_.net.liftweb.http._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import Helpers._
import _root_.net.liftweb.mapper._
import _root_.java.sql.{Connection, DriverManager}
import net.liftweb.scalate.ScalateView

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    // add the connection manager if there's not already a JNDI connection defined
    if (!DB.jndiJdbcConnAvailable_?) DB.defineConnectionManager(DefaultConnectionIdentifier, DBVendor)

    // lets add Scalate
    val scalateView = new ScalateView
    scalateView.register

    // Update the database schema to be in sync
    Schemifier.schemify(true, Schemifier.infoF _, User)

    // The locale is either calculated based on the incoming user or
    // based on the http request
    LiftRules.localeCalculator = r => User.currentUser.map(_.locale.isAsLocale).openOr(LiftRules.defaultLocaleCalculator(r))

    // Build SiteMap
    ///
        val entries = Menu(Loc("Home", List("index"), "Home")) ::
        Menu(Loc("Request Details", List("request"), "Request Details")) ::
        User.sitemap

        LiftRules.setSiteMap(SiteMap(entries:_*))
    //*/
  }
}

object DBVendor extends ConnectionManager {
  def newConnection(name: ConnectionIdentifier): Box[Connection] = {
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver")
      val dm = DriverManager.getConnection("jdbc:derby:lift_example;create=true")
      Full(dm)
    } catch {
      case e: Exception => e.printStackTrace; Empty
    }
  }

  def releaseConnection(conn: Connection) {
    conn.close
  }
}
