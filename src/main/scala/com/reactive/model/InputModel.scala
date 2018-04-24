package com.reactive.model

case class Temperature(timestamp: Double, temperature: Int)

case class Light(timestamp: Double, is_light: Boolean)

case class Water(timestamp: Double, is_water: Boolean)

case class Soil(timestamp: Double, is_wet: Boolean)
