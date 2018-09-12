package co.folto.viewnavigation

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.Navigation

class NavHostController @JvmOverloads constructor(
        context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr), NavHost {

    private val navController = NavController(context)

    init {
        Navigation.setViewNavController(this, navController)
        val controllerNavigator = ControllerNavigator(context, this)
        navController.navigatorProvider.addNavigator(controllerNavigator)
        context.withStyledAttributes(attributeSet, R.styleable.NavHostController, 0, 0) {
            navController.setGraph(getResourceId(R.styleable.NavHostController_navGraph, 0))
        }
    }

    override fun getNavController() = navController

    override fun onSaveInstanceState(): Parcelable {
        return Bundle().apply {
            putParcelable(KEY_VIEW_STATE, super.onSaveInstanceState())
            putParcelable(KET_NAV_CONTROLLER_STATE, navController.saveState())
        }
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is Bundle && state.containsKey(KEY_VIEW_STATE)) {
            super.onRestoreInstanceState(state.getParcelable("viewState"))
            navController.restoreState(state.getParcelable(KET_NAV_CONTROLLER_STATE))
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    companion object {
        const val KEY_VIEW_STATE = "viewState"
        const val KET_NAV_CONTROLLER_STATE = "navControllerState"
    }

}
