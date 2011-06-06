package bootstrap.liftweb
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import _root_.net.liftweb.http._
import _root_.net.liftweb.http.provider._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import Helpers._
import net.liftweb._
import http.{LiftRules, NotFoundAsTemplate, ParsePath}
import sitemap.{SiteMap, Menu, Loc}
import util.{ NamedPF }
import net.liftweb.mapper.{MapperRules,DefaultConnectionIdentifier,
  DBLogEntry,DB,Schemifier,StandardDBVendor}
import net.liftweb.http.js.jquery.JQuery14Artifacts
import org.hnlab.model._
import org.hnlab.ImageLogic

class Boot {
  def boot {
  
    // where to search snippet
    LiftRules.addToPackages("org.hnlab")

		// handle JNDI not being available
		if (!DB.jndiJdbcConnAvailable_?) {
			DB.defineConnectionManager(DefaultConnectionIdentifier, Database)
			LiftRules.unloadHooks.append(() => Database.closeAllConnections_!())
		}

		if (Props.devMode)
			Schemifier.schemify(true, Schemifier.infoF _, Image)

	  LiftRules.dispatch.append(ImageLogic.matcher)


    // build sitemap
    val entries = List(Menu("Home") / "index") :::
									List(Menu("Mandelbrot") / "capp") :::
									List(Menu("Mandelbrot-debug") / "capp_debug") :::
                  Nil
    
    LiftRules.uriNotFound.prepend(NamedPF("404handler"){
      case (req,failure) => NotFoundAsTemplate(
        ParsePath(List("exceptions","404"),"html",false,false))
    })
    
    LiftRules.setSiteMap(SiteMap(entries:_*))
    
    // set character encoding
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    
    LiftRules.jsArtifacts = JQuery14Artifacts

    /*
     * Show the spinny image when an Ajax call starts
     */
    LiftRules.ajaxStart =
    Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    /*
     * Make the spinny image go away when it ends
     */
    LiftRules.ajaxEnd =
    Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    LiftRules.early.append(makeUtf8)

    LiftRules.useXhtmlMimeType = false

    // We serve Cappuccino files with wicked friendly
    // mime types
    LiftRules.liftRequest.append {
      case Req( _, "j", GetRequest) => true
      case Req( _, "sj", GetRequest) => true
      case Req( _, "plist", GetRequest) => true
      case Req( _, "png", GetRequest) => true // Added to access to Resources folder
    }

    LiftRules.statelessDispatchTable.prepend {
      case r @ Req( _, "j", GetRequest) => ObjJServer.serve(r)
      case r @ Req( _, "sj", GetRequest) => ObjJServer.serve(r)
      case r @ Req( _, "plist", GetRequest) => ObjJServer.serve(r)
      case r @ Req( _, "png", GetRequest) => ObjJServer.serve(r) // Added to access to Resources folder
    }
	}

  /**
   * Force the request to be UTF-8
   */
  private def makeUtf8(req: HTTPRequest) {
    req.setCharacterEncoding("UTF-8")
  }

	object Database extends StandardDBVendor( 
		Props.get("db.class").openOr("org.h2.Driver"), 
		Props.get("db.url").openOr("jdbc:h2:database/chapter_eleven"), 
		Props.get("db.user"),
		Props.get("db.pass"))
	
}

object ObjJServer {
  def serve(req: Req)(): Box[LiftResponse] =
  for {
    url <- LiftRules.getResource(req.path.wholePath.mkString("/", "/", ""))
    urlConn <- tryo(url.openConnection)
    lastModified = ResourceServer.calcLastModified(url)
  } yield {
    req.testFor304(lastModified, "Expires" -> toInternetDate(millis + 30.days)) openOr {
      val stream = url.openStream
      StreamingResponse(stream, () => stream.close, urlConn.getContentLength,
                        (if (lastModified == 0L) Nil else
                         List(("Last-Modified", toInternetDate(lastModified)))) :::
                        List(("Expires", toInternetDate(millis + 30.days)),
                             ("Content-Type","application/text")), Nil,
                        200)
    }
  }

}