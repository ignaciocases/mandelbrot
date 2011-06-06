package org.hnlab
import	org.hnlab.model._
import net.liftweb.http._
import net.liftweb.mapper._
import net.liftweb.common._
import net.liftweb.util.Helpers._
import org.hnlab.snippet._

object ImageLogic {

	object TestImage {
	  def unapply(in: String): Option[Image] =
	    Image.find(By(Image.lookup, in.trim))
	}

	def matcher: LiftRules.DispatchPF = {
	  case req @ Req("image" :: TestImage(img) :: Nil, _, GetRequest) =>
	    () => serveImage(img, req)
	}

	def serveImage(img: Image, req: Req) : Box[LiftResponse] = {
		
	    Full(InMemoryResponse(
	          img.image,
	          List("Last-Modified" -> toInternetDate(img.saveTime.is),
	               "Content-Type" -> "image/png",//img.mimeType.is,
	               "Content-Length" -> img.image.length.toString),
	               Nil /*cookies*/,
	               200)
	    )

	}

}
