package me.sparker0i.mvvmsample.model

import java.io.Serializable

data class Currency(var date: String, var rates: Map<String, Double>, var base: String): Serializable