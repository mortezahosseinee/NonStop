package com.project.son.app.view.tabs.shortcut

import com.project.son.app.domain.ShortcutDomainModel
import com.project.son.library.base.presentation.viewmodel.BaseAction
import com.project.son.library.base.presentation.viewmodel.BaseViewModel
import com.project.son.library.base.presentation.viewmodel.BaseViewState

internal class ShortcutViewModel :
    BaseViewModel<ShortcutViewModel.ViewState, ShortcutViewModel.Action>(ViewState()) {

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
        val albums: List<ShortcutDomainModel> = listOf()
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        class AlbumListLoadingSuccess(val albums: List<ShortcutDomainModel>) : Action()
        object AlbumListLoadingFailure : Action()
    }
}