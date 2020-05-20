package com.project.son.app.view.tabs.connection.presentation

import android.content.Context
import com.project.son.app.database.MyDatabase
import com.project.son.app.database.model.Log
import com.project.son.app.domain.ConnectionDomainModel
import com.project.son.library.base.presentation.viewmodel.BaseAction
import com.project.son.library.base.presentation.viewmodel.BaseViewModel
import com.project.son.library.base.presentation.viewmodel.BaseViewState
import io.reactivex.Completable
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

internal class ConnectionViewModel constructor(val context: Context) :
    BaseViewModel<ConnectionViewModel.ViewState, ConnectionViewModel.Action>(ViewState()), KodeinAware {

    override val kodein by kodein(context)
    override val kodeinContext = kcontext(context)

    private val database: MyDatabase by instance()

//    private fun insertLogToDb(log: Log) {
//        Completable.fromAction { database.logDao().insertLog(log) }
//            .subscribeOn(schedulers.io())
//            .observeOn(schedulers.main())
//            .subscribe()
//    }

    override fun onLoadData() {
    }

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.AlbumListLoadingSuccess -> state.copy(
            isLoading = false,
            isError = false,
            albums = viewAction.albums
        )
        is Action.AlbumListLoadingFailure -> state.copy(
            isLoading = false,
            isError = true,
            albums = listOf()
        )
    }

    internal data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val albums: List<ConnectionDomainModel> = listOf()
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        class AlbumListLoadingSuccess(val albums: List<ConnectionDomainModel>) : Action()
        object AlbumListLoadingFailure : Action()
    }
}
