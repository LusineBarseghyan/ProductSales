package com.example.quickstartlessons.module.base.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.quickstartlessons.QSApplication.Companion.networkStateLiveData
import com.example.quickstartlessons.R
import com.example.quickstartlessons.databinding.DialogLoaderBinding
import com.example.quickstartlessons.module.base.utils.QSDefaultDialogData
import com.example.quickstartlessons.module.base.utils.SingleLiveEventData
import com.example.quickstartlessons.module.base.utils.createDialog
import com.example.quickstartlessons.module.base.utils.createMessageDialog
import org.koin.androidx.scope.ScopeActivity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Stack

abstract class BaseActivity : ScopeActivity() {
    var noConnectionTextView: TextView? = null

    private val loadingDialog: Dialog? by lazy {
        val binding = DialogLoaderBinding.inflate(LayoutInflater.from(this))
        createDialog(binding.root, false)
    }

    private var errorMessageDialog: Dialog? = null
    private var successMessageDialog: Dialog? = null
    private var infoMessageDialog: Dialog? = null
    private var confirmationMessageDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData() {
        networkStateLiveData.observe(this) {
            showNoInternetView(!it)
        }

        setupUI(findViewById(android.R.id.content))

        isShowLoadingLiveData.observe(this) {
            if (it?.isNotEmpty() == true) {
                Handler(Looper.getMainLooper()).postDelayed({ if (it.isNotEmpty()) showLoadingDialog() }, 500)
            } else {
                dismissLoadingDialog()
            }
        }

        isShowErrorMessageLiveData.observe(this) {
            if (it?.isNotEmpty() == true) {
                Handler(Looper.getMainLooper()).postDelayed({ if (it.isNotEmpty()) showErrorMsgDialog(it.peek()) }, 0)
            }
        }

        isShowSuccessMessageLiveData.observe(this) {
            if (it?.isNotEmpty() == true) {
                Handler(Looper.getMainLooper()).postDelayed({ if (it.isNotEmpty()) showSuccessMsgDialog(it.peek()) }, 0)
            }
        }

        isShowInfoMessageLiveData.observe(this) {
            if (it?.isNotEmpty() == true) {
                Handler(Looper.getMainLooper()).postDelayed({ if (it.isNotEmpty()) showInfoMsgDialog(it.peek()) }, 0)
            }
        }

        isShowConfirmationMessageLiveData.observe(this) {
            if (it?.isNotEmpty() == true) {
                Handler(Looper.getMainLooper()).postDelayed({ if (it.isNotEmpty()) showConfirmationMsgDialog(it.peek()) }, 0)
            }
        }
    }

    private fun showNoInternetView(display: Boolean) {
        if (display) {
            val enterAnim = AnimationUtils.loadAnimation(this, R.anim.anim_enter_from_bottom)
            noConnectionTextView?.startAnimation(enterAnim)
        } else {
            val exitAnim = AnimationUtils.loadAnimation(this, R.anim.anim_exit_to_bottom)
            noConnectionTextView?.startAnimation(exitAnim)
        }
        noConnectionTextView?.visibility = if (display) View.VISIBLE else View.GONE
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setupUI(view: View) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { _, _ ->
                val v = currentFocus
                v?.clearFocus()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v?.windowToken, 0)
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    @Synchronized
    fun showLoadingDialog() {
        if (loadingDialog != null && !loadingDialog!!.isShowing && !isDestroyed && !isFinishing) {
            loadingDialog?.show()
        }
    }

    @Synchronized
    fun dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            try {
                loadingDialog?.dismiss()
            } catch (e: Exception) {
                Log.d("TAG", "dismissLoadingDialog: $e")
            }
        }
    }

    @Synchronized
    fun showErrorMsgDialog(errorMessageData: QSDefaultDialogData) {
        if (errorMessageDialog != null) {
            if (errorMessageDialog!!.isShowing) {
                errorMessageDialog!!.dismiss()
            }
            errorMessageDialog = null
        }

        errorMessageDialog = createMessageDialog(errorMessageData)

        if (!isDestroyed && !isFinishing) {
            errorMessageDialog!!.show()
        }
    }

    @Synchronized
    fun showInfoMsgDialog(errorMessageData: QSDefaultDialogData) {
        if (infoMessageDialog != null) {
            if (infoMessageDialog!!.isShowing) {
                infoMessageDialog!!.dismiss()
            }
            infoMessageDialog = null
        }

        infoMessageDialog = createMessageDialog(errorMessageData)

        if (!isDestroyed && !isFinishing) {
            infoMessageDialog!!.show()
        }
    }

    @Synchronized
    fun showSuccessMsgDialog(successMessageData: QSDefaultDialogData) {
        if (successMessageDialog != null) {
            if (successMessageDialog!!.isShowing) {
                successMessageDialog!!.dismiss()
            }
            successMessageDialog = null
        }

        successMessageDialog = createMessageDialog(successMessageData)

        if (!isDestroyed && !isFinishing) {
            successMessageDialog!!.show()
        }
    }

    @Synchronized
    fun showConfirmationMsgDialog(successMessageData: QSDefaultDialogData) {
        if (confirmationMessageDialog != null) {
            if (confirmationMessageDialog!!.isShowing) {
                confirmationMessageDialog!!.dismiss()
            }
            confirmationMessageDialog = null
        }

        confirmationMessageDialog = createMessageDialog(successMessageData, isShowCancelButton = true)

        if (!isDestroyed && !isFinishing) {
            confirmationMessageDialog!!.show()
        }
    }

    companion object : KoinComponent {
        private val context by inject<Context>()

        val isShowLoadingLiveData: MutableLiveData<Stack<Unit>?> = MutableLiveData()

        val isShowErrorMessageLiveData: SingleLiveEventData<Stack<QSDefaultDialogData>?> = SingleLiveEventData()
        val isShowSuccessMessageLiveData: SingleLiveEventData<Stack<QSDefaultDialogData>?> = SingleLiveEventData()
        val isShowInfoMessageLiveData: SingleLiveEventData<Stack<QSDefaultDialogData>?> = SingleLiveEventData()
        val isShowConfirmationMessageLiveData: SingleLiveEventData<Stack<QSDefaultDialogData>?> = SingleLiveEventData()

        fun addLoader() {
            if (isShowLoadingLiveData.value == null)
                isShowLoadingLiveData.value = Stack()

            val loadersStack = isShowLoadingLiveData.value
            loadersStack!!.push(Unit)
            isShowLoadingLiveData.value = loadersStack
        }

        fun removeLoader() {
            val loadersStack = isShowLoadingLiveData.value
            if (!loadersStack.isNullOrEmpty()) {
                loadersStack.pop()
                isShowLoadingLiveData.value = loadersStack
            }
        }

        fun showErrorMessageDialog(title: String, message: String, iconRes: Int = R.drawable.ic_warning, isCancelable: Boolean) {
            showErrorMessageDialog(QSDefaultDialogData(title, message, iconRes, isCancelable, positiveButtonText = context.getString(android.R.string.ok), negativeButtonText = context.getString(android.R.string.cancel)))
        }

        fun showErrorMessageDialog(errorMessageData: QSDefaultDialogData) {
            if (isShowErrorMessageLiveData.value == null)
                isShowErrorMessageLiveData.value = Stack()

            val errorMessageStack = isShowErrorMessageLiveData.value
            errorMessageStack!!.push(errorMessageData)
            isShowErrorMessageLiveData.value = errorMessageStack
        }

        fun showSuccessMessageDialog(title: String, message: String, iconRes: Int = R.drawable.ic_success, isCancelable: Boolean = true) {
            showSuccessMessageDialog(QSDefaultDialogData(title, message, iconRes, isCancelable, positiveButtonText = context.getString(android.R.string.ok), negativeButtonText = context.getString(android.R.string.cancel)))
        }

        fun showSuccessMessageDialog(successMessageData: QSDefaultDialogData) {
            if (isShowSuccessMessageLiveData.value == null)
                isShowSuccessMessageLiveData.value = Stack()

            val successMessageStack = isShowSuccessMessageLiveData.value
            successMessageStack!!.push(successMessageData)
            isShowSuccessMessageLiveData.value = successMessageStack
        }

        fun showInfoMessageDialog(title: String, message: String, iconRes: Int = R.drawable.ic_info, isCancelable: Boolean = true) {
            showInfoMessageDialog(QSDefaultDialogData(title, message, iconRes, isCancelable, positiveButtonText = context.getString(android.R.string.ok), negativeButtonText = context.getString(android.R.string.cancel)))
        }

        fun showInfoMessageDialog(infoMessageData: QSDefaultDialogData) {
            if (isShowInfoMessageLiveData.value == null)
                isShowInfoMessageLiveData.value = Stack()

            val infoMessageStack = isShowInfoMessageLiveData.value
            infoMessageStack!!.push(infoMessageData)
            isShowInfoMessageLiveData.value = infoMessageStack
        }

        fun showConfirmationMessageDialog(title: String, message: String, iconRes: Int = R.drawable.ic_confirmation, isCancelable: Boolean = true, okClick: () -> Unit = {}, cancelClick: () -> Unit = {}) {
            showConfirmationMessageDialog(QSDefaultDialogData(title, message, iconRes, isCancelable, okClick = okClick, cancelClick = cancelClick, positiveButtonText = context.getString(android.R.string.ok), negativeButtonText = context.getString(android.R.string.cancel)))
        }

        fun showConfirmationMessageDialog(infoMessageData: QSDefaultDialogData) {
            if (isShowConfirmationMessageLiveData.value == null)
                isShowConfirmationMessageLiveData.value = Stack()

            val infoMessageStack = isShowConfirmationMessageLiveData.value
            infoMessageStack!!.push(infoMessageData)
            isShowConfirmationMessageLiveData.value = infoMessageStack
        }
    }
}