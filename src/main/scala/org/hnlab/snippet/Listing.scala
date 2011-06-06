package org.hnlab.snippet

import scala.xml.{NodeSeq}
import net.liftweb.util.Helpers._
import org.hnlab.model.Image
import org.hnlab._
import net.liftweb.mapper.{OrderBy, Ascending}

class Listing {
	// lazy val imageData: Array[Byte] = {
	//    val baos = new java.io.ByteArrayOutputStream
	//    val is = new java.io.FileInputStream("/Development/lift/integration/testing/mandelbrot/mandelbrot.png")
	//    val buf = new Array[Byte](1024)
	//    var bytesRead = 0
	//    while ({ bytesRead = is.read(buf); bytesRead } >= 0) {
	//        baos.write(buf, 0, bytesRead)
	//    }
	//    baos.toByteArray
	// }
	

  def summary = {
		println(Image.findAll)
		"#title" #> "In summary" &
		"#description" #> "description" &
		"#img" #> Image.findAll(OrderBy(Image.saveTime, Ascending)).last.imageUrl
		
		// "#img" #> Image.findAll.flatMap {
		// 	img => img.imageUrl
		// }
 	}

	def generateImage = {
		// Creates the Mandelbrot render
		val mandelData = FractalImage.drawMandelbrot(-2.2, 0.7, -1.5, 1.5)

		// Transform to byte array
		val baos = new java.io.ByteArrayOutputStream
		javax.imageio.ImageIO.write(mandelData, "png", baos)
		val imageBytes = baos.toByteArray
				
		Image.create.image(imageBytes).save
		"#title" #> "generating"
		"#img" #> Image.findAll(OrderBy(Image.saveTime, Ascending)).last.imageUrl
		
		
	}

	def cleanDatabase = {
		Image.findAll.map(_.delete_!)
		println("cleaning")
		"#title" #> "cleaning"
	}

	private def bindImages(images: List[Image]): NodeSeq = {
		if (images.isEmpty) {
			<b>"No image or default"</b>
		} else {
			<b></b>
		}
	}

}