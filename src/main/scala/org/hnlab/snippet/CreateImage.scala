package org.hnlab.snippet

import scala.xml.{NodeSeq}
import net.liftweb.util.Helpers._

class CreateImage {	
	//   def render = "*" #> <strong>hello world!</strong>
	// def renderFractal = {
	// 	val mandelbrot = FractalImage
	// 	mandelbrot.render
	// 	println(new java.io.File(".").getAbsolutePath)
	// 	"img" #> <img src="mandelbrot.png"></img>
	// }
}

object FractalImage {

	var iter = 0
	val overflow = 1.0e100
		
  // def render(): Unit = {
  //   var rendImage = drawMandelbrot
  // 		try { 
  // 		  val file = new java.io.File("mandelbrot.png")
  // 			javax.imageio.ImageIO.write(rendImage, "png", file)
  // 		} catch {
  // 		  case e: java.io.IOException => println("Could not create image\n")
  // 		}
  // }
	def drawMandelbrot(pMin: Double, pMax: Double, qMin: Double, qMax: Double): java.awt.image.RenderedImage = {
		val xScreen = 1024
		val yScreen = 1024
		
		val bufferedImage = new java.awt.image.BufferedImage(xScreen, yScreen, java.awt.image.BufferedImage.TYPE_BYTE_GRAY)

		val context = bufferedImage.createGraphics()
		// drawing code here
		var iyNew = 0
		// val pMin = -0.2
		// val pMax = 0.7
		// val qMin = -0.5
		// val qMax = 0.5
		
		val deltaP = (pMax - pMin) / (xScreen - 1)
		val deltaQ = (qMin - qMax) / (yScreen - 1)
		
		for (np <- 0 to xScreen - 2) {
			var iy = 0
			var x = pMin + deltaP * np
			var d = mandelbrotSetDistance(x, qMin)
			while (iy < (yScreen - 1)) {
				iyNew = iy + (scala.math.floor(scala.math.max(1.0, scala.math.min(20.0, d)))).toInt
				var y = iyNew * deltaQ + qMax
				var dNew = mandelbrotSetDistance(x, y)
				if (d <= 0.0)	context.setColor(java.awt.Color.black)
				else {
					var c = (iter % 15 + 1) * 17
					context.setColor(new java.awt.Color(c, c, c))
				}
				context.drawLine(np, iy, np, iyNew)
				iy = iyNew
				d = dNew
			}
		}
		
		context.dispose
		
		return bufferedImage
	}
	
	def mandelbrotSetDistance(cx : Double, cy : Double):Double = {
	  val MaxIterations = 100;
	  val huge = 1000.0;
	  var i = 0
	  var xorbit = new Array[Double](MaxIterations)
	  var yorbit = new Array[Double](MaxIterations)
	  var dist = 0.0 
	  var xder = 0.0;  var yder = 0.0  
	  var temp = 0.0
	  var x2 = 0.0 
	  var y2 = 0.0 
	  var x = 0.0 
	  var y = 0.0
	  xorbit(0) = 0.0
	  yorbit(0) = 0.0;
	  iter = 1;
	  while ((iter < MaxIterations) && ((x2+y2) < huge)) {
	    temp = x2 - y2 + cx
	    y = 2.0*x*y + cy
	    x = temp
	    x2 = x*x
	    y2 = y*y
	    xorbit(iter) = x
	    yorbit(iter) = y
	    iter += 1
	  }
	  if ( (x2+y2) > huge ) {
	    xder = 0.0
	    yder = 0.0
	    var i = 0
	    var flag = false
	    while ( ( i < iter ) && (! flag) ) {
	      temp = 2.0*(xorbit(i)*xder-yorbit(i)*yder+1.0)
	      yder = 2.0*(yorbit(i)*xder+xorbit(i)*yder)
	      xder = temp
	      flag = scala.math.max(scala.math.abs(xder), scala.math.abs(yder)) > overflow
	      i += 1
	    }
	    if (! flag) 
	      dist = scala.math.log(x2+y2)*scala.math.sqrt(x2+y2)/
	             scala.math.sqrt(xder*xder+yder*yder)               
	  }
	  return dist
	}

}
