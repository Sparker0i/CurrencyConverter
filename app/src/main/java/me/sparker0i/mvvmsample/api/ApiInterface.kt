package me.sparker0i.mvvmsample.api

import io.reactivex.Observable
import me.sparker0i.mvvmsample.model.Currency
import retrofit2.http.GET
import java.util.*

interface ApiInterface {
    @GET("latest") fun getRates(): Observable<Currency>
}