package co.folto.viewnavigation

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

abstract class ViewController @JvmOverloads constructor(context: Context, var extraData: Bundle? = null, attrs: AttributeSet? = null)
    : FrameLayout(context, attrs) {

    init {
        initView()
    }

    private fun initView() {
        addView(onCreateView(LayoutInflater.from(context), this))
        onViewCreated()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onViewAttached()
    }

    open fun onViewCreated() {}

    open fun onViewAttached() {}

    abstract fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View
}
