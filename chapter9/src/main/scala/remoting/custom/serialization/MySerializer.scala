package remoting.custom.serialization

import akka.serialization.Serializer
import com.google.gson.GsonBuilder

class MySerializer extends Serializer {

  val gson = new GsonBuilder().serializeNulls().create()

  override def identifier: Int = 12062010

  override def toBinary(o: AnyRef): Array[Byte] = gson.toJson(o).getBytes()

  override def includeManifest: Boolean = ???

  override def fromBinary(bytes: Array[Byte], manifest: Option[Class[_]]): AnyRef = gson.fromJson(new String(bytes), manifest.toList.head)
}
