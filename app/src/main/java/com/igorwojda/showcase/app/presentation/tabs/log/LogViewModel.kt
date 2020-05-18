package com.igorwojda.showcase.app.presentation.tabs.log

import com.igorwojda.showcase.library.base.presentation.viewmodel.BaseAction
import com.igorwojda.showcase.library.base.presentation.viewmodel.BaseViewModel
import com.igorwojda.showcase.library.base.presentation.viewmodel.BaseViewState

internal class LogViewModel :
    BaseViewModel<LogViewModel.ViewState, LogViewModel.Action>(ViewState()) {

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
        val albums: List<LogDomainModel> = listOf()
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        class AlbumListLoadingSuccess(val albums: List<LogDomainModel>) : Action()
        object AlbumListLoadingFailure : Action()
    }
}
