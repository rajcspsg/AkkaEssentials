package remoting.custom.serialization

import akka.actor.ActorSystem
import akka.serialization.SerializationExtension
import com.typesafe.config.ConfigFactory

object SerializationDemo extends  App {
  val system = ActorSystem("MySerializable", ConfigFactory.load().getConfig("MySerializableSys"))
  val log = system.log
  val serialization = SerializationExtension(system)
  val originalMsg = MyMessage("Raj", 36, "Bangalore")
  log.info(s"The original mesage as is {}", originalMsg)
  val serializer = serialization.findSerializerFor(originalMsg)
  val bytes = serializer.toBinary(originalMsg)
  val deSerializedMsg = serializer.fromBinary(bytes, classOf[MyMessage])
  println(s"bytes $bytes deSerializedMsg $deSerializedMsg")
}
