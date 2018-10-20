package me.sparker0i.mvvmsample.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import me.sparker0i.mvvmsample.model.Rates
import me.sparker0i.mvvmsample.repo.CurrencyRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyViewModel @Inject constructor(
        private val currencyRepository: CurrencyRepository
): ViewModel() {
    var currencyResult: MutableLiveData<List<Rates>> = MutableLiveData()
    var currencyError: MutableLiveData<String> = MutableLiveData()
    lateinit var disposableObserver: DisposableObserver<List<Rates>>

    fun getCurrencyResult(): LiveData<List<Rates>> {
        return currencyResult
    }

    fun getCurrencyError(): LiveData<String> {
        return currencyError
    }

    fun getRates() {
        disposableObserver = object: DisposableObserver<List<Rates>>() {
            override fun onComplete() {

            }

            override fun onNext(t: List<Rates>) {
                currencyResult.postValue(t)
            }

            override fun onError(e: Throwable) {
                currencyError.postValue(e.message)
            }
        }

        currencyRepository.getCurrency()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(400 , TimeUnit.MILLISECONDS)
                .subscribe(disposableObserver)
    }

    fun disposeElements() {
        if (disposableObserver != null && !disposableObserver.isDisposed)
            disposableObserver.dispose()
    }
}