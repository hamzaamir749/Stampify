package com.coderpakistan.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.coderpakistan.core.presentation.designsystem.Poppins
import com.coderpakistan.core.presentation.designsystem.StampifyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StampifyToolbar(
    modifier: Modifier = Modifier,
    showBackButton: Boolean,
    title: String,
    titleColor: Color,
    /*    menuItems: List<DropDownItem> = emptyList(),*/
    onMenuItemClick: (Int) -> Unit = {},
    onBackClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    startContent: (@Composable () -> Unit)? = null
) {
    /*var isDropDownOpen by rememberSaveable {
        mutableStateOf(false)
    }*/
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                /*  startContent?.invoke()
                  Spacer(modifier = Modifier.width(8.dp))*/
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    color = titleColor,
                    fontFamily = Poppins
                )
            }
        },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            /* if (showBackButton) {
                 IconButton(onClick = onBackClick) {
                     Icon(
                         imageVector = ArrowLeftIcon,
                         contentDescription = stringResource(id = R.string.go_back),
                         tint = MaterialTheme.colorScheme.onBackground,
                         modifier = Modifier.size(35.dp)
                     )
                 }
             }*/
        },
        actions = {
            /*  if (menuItems.isNotEmpty()) {
                  Box {
                      DropdownMenu(
                          expanded = isDropDownOpen,
                          onDismissRequest = {
                              isDropDownOpen = false
                          }
                      ) {
                          menuItems.forEachIndexed { index, item ->
                              Row(
                                  verticalAlignment = Alignment.CenterVertically,
                                  modifier = Modifier
                                      .clickable { onMenuItemClick(index) }
                                      .fillMaxWidth()
                                      .padding(16.dp)
                              ) {
                                  Icon(
                                      imageVector = item.icon,
                                      contentDescription = item.title
                                  )
                                  Spacer(modifier = Modifier.width(8.dp))
                                  Text(text = item.title)
                              }
                          }
                      }
                      IconButton(onClick = { isDropDownOpen = true }) {
                          Icon(
                              imageVector = Icons.Default.MoreVert,
                              contentDescription = stringResource(id = R.string.open_drop_down),
                              tint = MaterialTheme.colorScheme.onSurfaceVariant
                          )
                      }
                  }
              }*/
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun StampifyToolbarPreview() {
    StampifyTheme {
        StampifyToolbar(
            modifier = Modifier.fillMaxWidth(),
            showBackButton = false,
            title = "Stampify",
            titleColor =  MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.surfaceContainer),
            /*menuItems = listOf(
                DropDownItem(
                    icon = AnalyticsIcon,
                    title = "Analytics"
                ),

                DropDownItem(
                    icon = LogoutIcon,
                    title = "Logout"
                )
            ),*/
            startContent = {
                /* Icon(
                     imageVector = LogoIcon,
                     contentDescription = null,
                     tint = StampifyGreen,
                     modifier = Modifier.size(35.dp)
                 )*/
            }
        )
    }
}