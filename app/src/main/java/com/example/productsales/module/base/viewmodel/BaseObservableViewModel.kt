package com.example.quickstartlessons.module.base.viewmodel

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import com.example.quickstartlessons.QSApplication
import com.example.quickstartlessons.module.base.activity.BaseActivity
import com.example.quickstartlessons.module.base.fragment.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import org.koin.core.component.KoinComponent
import java.util.Stack

open class BaseObservableViewModel : ViewModel(), CoroutineScope, Observable, KoinComponent {

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun onCleared() {
        super.onCleared()
        cancel()
        BaseFragment.removeLoader()
        BaseActivity.isShowLoadingLiveData.value?.clear()
        BaseActivity.isShowLoadingLiveData.value = Stack()

        BaseActivity.isShowErrorMessageLiveData.value?.clear()
        BaseActivity.isShowErrorMessageLiveData.value = Stack()

        BaseActivity.isShowSuccessMessageLiveData.value?.clear()
        BaseActivity.isShowSuccessMessageLiveData.value = Stack()

        BaseActivity.isShowInfoMessageLiveData.value?.clear()
        BaseActivity.isShowInfoMessageLiveData.value = Stack()

        BaseActivity.isShowConfirmationMessageLiveData.value?.clear()
        BaseActivity.isShowConfirmationMessageLiveData.value = Stack()
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    @Suppress("unused")
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [androidx.databinding.Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    override val coroutineContext = QSApplication.getCoroutineContext()
}