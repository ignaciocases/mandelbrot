package org.hnlab.snippet

import net.liftweb._
import http._
import js._
import JsCmds._
import JE._
import util._
import Helpers._

import scala.xml._

import net.liftweb.util.Helpers._

import org.hnlab._
import org.hnlab.model.Image
import net.liftweb.mapper.{OrderBy, Ascending}
import net.liftweb.util.JSONParser._
import net.liftweb.common.{Box,Full,Empty,Failure,ParamFailure}

import java.io.InputStream

object JsonVar extends SessionVar(S.functionLifespan(true){S.buildJsonFunc{
      case JsonCmd("fractal", _, s: String, _) => {
				// Parse the incoming parameters
				val parsedJson = parse(s)
				
				// Unbox the parsed string
				val parameters: Map[String, Double] = parsedJson match {
					case Full(p:Map[String, Double]) => p
				}
				
				val pMin:Double = parameters.get("pMin").get
				val pMax:Double = parameters.get("pMax").get
				val qMin:Double = parameters.get("qMin").get
				val qMax:Double = parameters.get("qMax").get
				
				// Creates the Mandelbrot render
				val mandelData = FractalImage.drawMandelbrot(pMin, pMax, qMin, qMax)
				
				// Transform to byte array
				val baos = new java.io.ByteArrayOutputStream
				javax.imageio.ImageIO.write(mandelData, "png", baos)
				val imageBytes = baos.toByteArray
				
				// Save the image in the Database
				// In the future can serve as a cache mechanism
				Image.create.image(imageBytes).save				
				
				// Sends back to the client the image url
				val imageUrl = Image.findAll(OrderBy(Image.saveTime, Ascending)).last.imageLocation
				
				        JsRaw("""imageCallback("""+("http://localhost:8080"+imageUrl).encJs+""");""")
			}

      case x =>
        println("Got x via json: "+x)
        Noop
    }
  })

class HNFractal {
  def render(in: NodeSeq): NodeSeq = Script(
    Function("retrieveImageUrl", List("param"), JsonVar.is._1("fractal", JsVar("param"))) &
    JsonVar.is._2)
}