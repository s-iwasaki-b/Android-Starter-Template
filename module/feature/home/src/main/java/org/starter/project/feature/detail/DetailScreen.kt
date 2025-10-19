package org.starter.project.feature.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.starter.project.feature.home.HomeScreenEvent
import org.starter.project.ui.R
import org.starter.project.ui.design.system.scaffold.SystemScaffold
import org.starter.project.ui.design.system.theme.SystemTheme
import org.starter.project.ui.route.AppRouter
import org.starter.project.ui.shared.event.ScreenEvent

@Composable
fun DetailScreen(
    viewModel: DetailScreenViewModel,
    appRouter: AppRouter
) {
    val state by viewModel.state.collectAsState()

    DetailScreenContent(
        state = state
    ) { event ->
        DetailScreenEventHandler(
            event = event,
            appRouter = appRouter,
            viewModel = viewModel
        )
    }
}

@Composable
private fun DetailScreenContent(
    state: DetailScreenState,
    dispatch: (event: ScreenEvent) -> Unit
) {
    SystemScaffold(
        modifier = Modifier.fillMaxSize(),
        screenState = state.screenState,
        topBar = {
            Row(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { dispatch(DetailScreenEvent.OnTapBackIcon) }
                        .aspectRatio(1f)
                        .padding(vertical = 8.dp),
                    painter = painterResource(R.drawable.icon_arrow_back),
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        },
        onTapErrorActionButton = {
            dispatch(HomeScreenEvent.OnTapErrorScreenAction)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = SystemTheme.typography.titleMedium,
                color = Color.DarkGray,
                text = "${state.id}\n${state.title}",
                textAlign = TextAlign.Center
            )
        }
    }
}
