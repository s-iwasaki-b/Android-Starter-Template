package org.starter.project.ui.design.system.search

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.starter.project.ui.R
import org.starter.project.ui.design.system.theme.SystemTheme

@Composable
fun SystemSearchBar(
    modifier: Modifier = Modifier,
    value: String = "",
    placeholder: String = stringResource(R.string.search_bar_default_placeholder),
    onValueChange: (String) -> Unit = {},
    onTapClear: () -> Unit = {},
    onTapAction: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusState = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.2f))
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .onFocusChanged {
                    focusState.value = it.isFocused
                },
            value = value,
            onValueChange = onValueChange,
            textStyle = SystemTheme.typography.bodyMedium,
            singleLine = true,
            cursorBrush = SolidColor(SystemTheme.colors.primary),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(R.drawable.icon_search),
                        contentDescription = null,
                        tint = Color.Gray
                    )
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        innerTextField()
                        if (value.isEmpty()) {
                            Text(
                                style = SystemTheme.typography.bodyMedium,
                                color = Color.Gray,
                                text = placeholder
                            )
                        }
                    }
                    if (value.isNotEmpty()) {
                        Icon(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .clickable { onTapClear() },
                            painter = painterResource(R.drawable.icon_close),
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
                keyboardController?.hide()
                onTapAction()
            }
        )
        if (focusState.value) {
            Text(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        onTapAction()
                    }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                style = SystemTheme.typography.bodyMedium,
                color = Color.Black,
                text = stringResource(R.string.search_bar_default_action),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun SystemSearchBarPreview() {
    SystemTheme {
        SystemSearchBar(
            modifier = Modifier.fillMaxWidth()
        )
    }
}
