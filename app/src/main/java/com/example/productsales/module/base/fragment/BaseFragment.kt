package com.example.quickstartlessons.module.base.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.quickstartlessons.QSApplication.Companion.networkStateLiveData
import com.example.quickstartlessons.R
import com.example.quickstartlessons.module.base.activity.BaseActivity
import com.example.quickstartlessons.module.base.utils.QSDefaultDialogData
import com.example.quickstartlessons.module.base.utils.unbindDrawables
import org.koin.androidx.scope.ScopeFragment
import java.util.Stack

abstract class BaseFragment : ScopeFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dismissLoadingDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as BaseActivity).setupUI(view)
        observeLiveData()
    }

    private fun observeLiveData() {
        networkStateLiveData.observe(viewLifecycleOwner) {
            if (it != true)
                hideSwipeRefreshLayoutAction()
        }
    }

    override fun onDestroy() {
        if (view != null && isUnbindViews()) {
            requireView().unbindDrawables()
        }
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
        super.onDestroy()
    }

    override fun onDestroyView() {
        if (view != null) {
            requireView().unbindDrawables()
        }

        dismissLoadingDialog()
        super.onDestroyView()
    }

    fun showLoadingDialog() {
        (requireActivity() as BaseActivity).showLoadingDialog()
    }

    fun dismissLoadingDialog() {
        (requireActivity() as BaseActivity).dismissLoadingDialog()
    }

    open fun isUnbindViews(): Boolean = true

    open fun hideSwipeRefreshLayoutAction() {}

    open fun onStateInVisible() {}

    open fun onStateVisible() {}

    companion object {
        fun addLoader() {
            BaseActivity.addLoader()
        }

        fun removeLoader() {
            BaseActivity.removeLoader()
        }

        fun showErrorMessageDialog(title: String, message: String, iconRes: Int = R.drawable.ic_success, isCancelable: Boolean = true) {
            BaseActivity.showErrorMessageDialog(title, message, iconRes, isCancelable)
        }

        fun showErrorMessageDialog(errorMessageDialogData: QSDefaultDialogData) {
            BaseActivity.showErrorMessageDialog(errorMessageDialogData)
        }

        fun showSuccessMessageDialog(title: String, message: String, iconRes: Int = R.drawable.ic_success, isCancelable: Boolean = true) {
            BaseActivity.showSuccessMessageDialog(title, message, iconRes, isCancelable)
        }

        fun showSuccessMessageDialog(successMessageDialogData: QSDefaultDialogData) {
            BaseActivity.showSuccessMessageDialog(successMessageDialogData)
        }

        fun showInfoMessageDialog(title: String, message: String, iconRes: Int = R.drawable.ic_info, isCancelable: Boolean = true) {
            BaseActivity.showInfoMessageDialog(title, message, iconRes, isCancelable)
        }

        fun showInfoMessageDialog(successMessageDialogData: QSDefaultDialogData) {
            BaseActivity.showInfoMessageDialog(successMessageDialogData)
        }

        fun showConfirmationMessageDialog(title: String, message: String, iconRes: Int = R.drawable.ic_confirmation, isCancelable: Boolean = true, okClick: () -> Unit = {}, cancelClick: () -> Unit = {}) {
            BaseActivity.showConfirmationMessageDialog(title, message, iconRes, isCancelable, okClick = okClick, cancelClick = cancelClick)
        }

        fun showConfirmationMessageDialog(successMessageDialogData: QSDefaultDialogData) {
            BaseActivity.showConfirmationMessageDialog(successMessageDialogData)
        }
    }
}