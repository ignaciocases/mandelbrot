package org.hnlab.model

import net.liftweb.mapper._
import scala.xml._
import net.liftweb.util.Helpers

class Image extends LongKeyedMapper[Image] with IdPK {

  def getSingleton = Image

	object image extends MappedBinary(this)
	
	object lookup extends MappedUniqueId(this, 16) {
		override def dbIndexed_? = true
	}
	
	object saveTime extends MappedLong(this) {
		override def defaultValue = Helpers.millis
	}
	
	object mimeType extends MappedString(this, 256)
	
	def imageUrl : NodeSeq = <img src={"/image/" + lookup} />
	
	def imageLocation: String = "/image/"+lookup

}
object Image extends Image with LongKeyedMetaMapper[Image] {
	override def dbTableName = "images"
}