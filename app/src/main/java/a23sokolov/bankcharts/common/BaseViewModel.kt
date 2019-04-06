package a23sokolov.bankcharts.common

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by a.v.sokolov
 */
open class BaseViewModel: ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val loadingState = MutableLiveData<LoadingState>()
    fun getLoadingState(): LiveData<LoadingState> = loadingState

    @CallSuper
    open fun init() = Unit

    fun updateState(state: LoadingState) {
        loadingState.postValue(state)
    }

    fun subscribe(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

class LoadingState(
    val isLoading: Boolean,
    val errorMsg: String?
) {
    companion object {
        fun ofProgress(isLoading: Boolean) = LoadingState(isLoading, null)

        fun ofError(message: String? = null) = LoadingState(false, message)
    }
}