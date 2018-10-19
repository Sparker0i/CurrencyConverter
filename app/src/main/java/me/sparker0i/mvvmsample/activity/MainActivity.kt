package me.sparker0i.mvvmsample.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import me.sparker0i.mvvmsample.R
import me.sparker0i.mvvmsample.api.ApiClient
import me.sparker0i.mvvmsample.api.ApiInterface
import me.sparker0i.mvvmsample.model.Currency
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showRates()
    }

    private fun showRates() {
        val currencyResponse = getCurrencies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())

        val disposableObserver = currencyResponse.subscribeWith(object: DisposableObserver<Currency>() {
            override fun onComplete() {

            }

            override fun onNext(t: Currency) {
                Timber.i(t.toString())
            }

            override fun onError(e: Throwable) {
                Timber.e(e)
            }
        })

        compositeDisposable.addAll(disposableObserver)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private fun getCurrencies(): Observable<Currency> {
        val retrofit = ApiClient.getClient()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        return apiInterface.getRates()
    }
}
