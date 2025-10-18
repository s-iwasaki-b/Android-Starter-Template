package org.starter.project.feature.detail

import androidx.compose.runtime.Immutable
import org.starter.project.ui.shared.state.ScreenState

@Immutable
internal data class DetailScreenState(
    val screenState: ScreenState,
    val title: String = ""
)
