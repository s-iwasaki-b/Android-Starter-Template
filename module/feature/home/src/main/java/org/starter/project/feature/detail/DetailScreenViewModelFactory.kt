package org.starter.project.feature.detail

import dagger.assisted.AssistedFactory
import org.starter.project.ui.route.AppRoute

@AssistedFactory
interface DetailScreenViewModelFactory {
    fun create(navArgs: AppRoute.Detail.NavArgs): DetailScreenViewModel
}
