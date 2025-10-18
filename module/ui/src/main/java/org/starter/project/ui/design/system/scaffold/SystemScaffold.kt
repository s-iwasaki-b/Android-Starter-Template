package org.starter.project.ui.design.system.scaffold

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.starter.project.ui.R
import org.starter.project.ui.design.system.loading.SystemLoadingIndicator
import org.starter.project.ui.design.system.theme.SystemTheme
import org.starter.project.ui.shared.state.ScreenLoadingState
import org.starter.project.ui.shared.state.ScreenState

@Composable
fun SystemScaffold(
    modifier: Modifier = Modifier,
    screenState: ScreenState,
    containerColor: Color = SystemTheme.colors.background,
    onTapErrorActionButton: (() -> Unit)? = null,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(screenState.snackBarState) {
        val message = screenState.snackBarState?.message
        if (message.isNullOrEmpty()) return@LaunchedEffect
        snackbarHostState.showSnackbar(
            message = message, duration = SnackbarDuration.Short
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            topBar?.let {
                Box(
                    modifier = Modifier
                        .background(color = containerColor)
                        .statusBarsPadding()
                ) {
                    topBar()
                }
            }
        },
        bottomBar = {
            bottomBar?.let {
                bottomBar()
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = containerColor,
    ) { paddingValues ->
        if (screenState.screenLoadingState is ScreenLoadingState.Failure) {
            ErrorContent(
                modifier = if (topBar != null) Modifier.padding(top = 32.dp) else Modifier,
                title = stringResource(R.string.loading_failure_default_message),
                message = screenState.screenLoadingState.throwable.message,
                actionLabel = stringResource(R.string.loading_failure_default_action_label),
                onTapActionButton = onTapErrorActionButton
            )
        } else {
            content(paddingValues)
        }
    }

    AnimatedVisibility(
        visible = screenState.screenLoadingState.showLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        SystemLoadingIndicator()
    }
}

@Composable
private fun ErrorContent(
    modifier: Modifier = Modifier,
    title: String,
    message: String?,
    actionLabel: String,
    onTapActionButton: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SystemTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                style = SystemTheme.typography.titleMedium,
                text = title
            )
            message?.let {
                Text(
                    style = SystemTheme.typography.labelMedium,
                    text = it
                )
            }
            onTapActionButton?.let {
                Button(
                    shape = CircleShape,
                    onClick = it
                ) {
                    Text(text = actionLabel)
                }
            }
        }
    }
}
