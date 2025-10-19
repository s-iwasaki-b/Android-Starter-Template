package org.starter.project.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.starter.project.ui.route.AppRoute
import org.starter.project.ui.shared.state.ScreenLoadingState
import org.starter.project.ui.shared.state.ScreenState

@HiltViewModel(assistedFactory = DetailScreenViewModelFactory::class)
class DetailScreenViewModel @AssistedInject constructor(
    @Assisted private val navArgs: AppRoute.Detail.NavArgs
) : ViewModel() {
    private val _screenState = MutableStateFlow(
        ScreenState(ScreenLoadingState.Initial(false), null)
    )
    private val _state = MutableStateFlow(
        DetailScreenState(screenState = _screenState.value)
    )
    internal val state: StateFlow<DetailScreenState> = combine(
        _screenState,
        _state
    ) { screenState, state ->
        state.copy(screenState = screenState)
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _state.value
    )

    init {
        _state.update {
            it.copy(id = navArgs.id, title = navArgs.title)
        }
    }
}
