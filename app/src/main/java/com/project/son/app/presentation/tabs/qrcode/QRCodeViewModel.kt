package com.project.son.app.presentation.tabs.qrcode

import com.project.son.library.base.presentation.viewmodel.BaseAction
import com.project.son.library.base.presentation.viewmodel.BaseViewModel
import com.project.son.library.base.presentation.viewmodel.BaseViewState

internal class QRCodeViewModel : BaseViewModel<QRCodeViewModel.ViewState, QRCodeViewModel.Action>(ViewState()) {

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
        val albums: List<QRCodeDomainModel> = listOf()
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        class AlbumListLoadingSuccess(val albums: List<QRCodeDomainModel>) : Action()
        object AlbumListLoadingFailure : Action()
    }
}
