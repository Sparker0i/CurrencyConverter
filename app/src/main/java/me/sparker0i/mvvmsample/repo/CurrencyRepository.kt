package me.sparker0i.mvvmsample.repo

import android.os.HandlerThread
import io.reactivex.Observable
import me.sparker0i.mvvmsample.api.ApiInterface
import me.sparker0i.mvvmsample.application.MvvmApplication
import me.sparker0i.mvvmsample.database.RatesDao
import me.sparker0i.mvvmsample.model.Rates
import timber.log.Timber
import java.util.ArrayList
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import javax.inject.Inject

class CurrencyRepository @Inject constructor(val apiInterface: ApiInterface,
                                             val ratesDao: RatesDao) {
    fun getCurrency(): Observable<List<Rates>> {
        val observableFromApi = getCurrencyFromApi()
        val observableFromDb = getRatesFromDb()
        return Observable.concatArrayEager(observableFromApi , observableFromDb)
    }

    fun getCurrency(limit: Int, offset: Int): Observable<List<Rates>> {
        val hasConnection = MvvmApplication.ifHasNetwork()
        var observableFromApi: Observable<List<Rates>>? = null
        if (hasConnection)
            observableFromApi = getCurrencyFromApi()
        val observableFromDb = getRatesFromDb()

        return if (hasConnection)
            Observable.concatArrayEager(observableFromApi , observableFromDb)
        else
            observableFromDb
    }

    private fun getCurrencyFromApi(): Observable<List<Rates>> {
        val executor = Executors.newSingleThreadExecutor()
        val callable = Callable<Observable<List<Rates>>> { apiInterface.getRates()
                .flatMapIterable { it.rates.entries }
                .map { Rates(it.key , it.value) }
                .doOnNext{ ratesDao.insertRate(it) }
                .toList()
                .toObservable() }
        val future = executor.submit(callable)
        return future.get().doOnNext{
            executor.shutdown()
        }
    }

    private fun getRatesFromDb(): Observable<List<Rates>> {
        return ratesDao.getAllRates()
                .toObservable()
                .doOnNext {
                    Timber.i("Hello ${it.size + 1}")
                }
    }
}