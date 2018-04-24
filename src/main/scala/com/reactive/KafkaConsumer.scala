package com.reactive

import akka.NotUsed
import akka.actor.ActorSystem
import akka.kafka.{ConsumerMessage, ConsumerSettings, Subscriptions}
import akka.kafka.scaladsl.Consumer
import akka.stream.scaladsl.Source
import com.reactive.model.Temperature
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization._

trait KafkaConsumer[K, V] {

  def createSettings[KD <: Deserializer[K], VD <: Deserializer[V]](system: ActorSystem,
                                                                         bootstrap: String,
                                                                         group: String,
                                                                         keyDeserializer: KD,
                                                                         valueDeserializer: VD
                                                                        ): ConsumerSettings[K, V] =
    ConsumerSettings.create[K, V](system, keyDeserializer, valueDeserializer)
      .withBootstrapServers(bootstrap)
      .withGroupId(group)
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  def createSource(consumerSettings: ConsumerSettings[K, V], topic: String):
  Source[ConsumerMessage.CommittableMessage[K, V], Consumer.Control] =
    Consumer.committableSource(consumerSettings, Subscriptions.topics(topic))

  def atLeastOnceProcess[T](source: Source[ConsumerMessage.CommittableMessage[K, V], Consumer.Control],
                         transform: (K, V) => T) = {
    source.map(msg => msg.committableOffset.commitScaladsl)
  }

  def transformSource[T](source: Source[ConsumerMessage.CommittableMessage[K, V], Consumer.Control],
                               transform: (K, V) => T): Source[T, Consumer.Control] = {
    source.map(msg => transform(msg.record.key, msg.record.value))
  }
}

class TemperatureKafkaConsumer extends KafkaConsumer[Double, Int] {

}
