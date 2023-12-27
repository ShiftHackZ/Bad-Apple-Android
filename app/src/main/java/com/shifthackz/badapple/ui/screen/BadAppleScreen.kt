@file:OptIn(ExperimentalStdlibApi::class)

package com.shifthackz.badapple.ui.screen

import android.graphics.Typeface.MONOSPACE
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.shifthackz.badapple.engine.Frame
import com.shifthackz.badapple.source.vector.BadAppleVectorFrames
import com.shifthackz.badapple.source.vector.R
import com.shifthackz.badapple.ui.contract.ActionListener
import com.shifthackz.badapple.ui.model.BadAppleState
import com.shifthackz.badapple.ui.model.MainButtonState
import com.shifthackz.badapple.ui.utils.getVersionString
import com.shifthackz.badapple.ui.utils.openWebPage
import com.shifthackz.badapple.ui.utils.replaceColor

private const val GITHUB = "https://github.com/ShiftHackZ/Bad-Apple-Android"

@Composable
fun BadAppleScreen(
    modifier: Modifier = Modifier,
    state: BadAppleState,
    listener: ActionListener,
) {
    val ctx = LocalContext.current
    val frameRes = (state.frame as? Frame.XmlRes)?.resId ?: R.drawable.bav_1
    val frameId = (state.frame as? Frame.XmlRes)?.id ?: 1
    val drawable = ctx.getDrawable(frameRes)?.replaceColor(Color.Black.toArgb(), state.accentColor)
    val fontMonospace = FontFamily(Typeface(MONOSPACE))

    Column(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text(
                text = "Frame  : $frameId/${BadAppleVectorFrames.MAX}",
                fontFamily = fontMonospace,
                style = LocalTextStyle.current.copy(fontFeatureSettings = "tnum"),
            )
            Text(
                text = "Seed   : $frameRes",
                fontFamily = fontMonospace,
                style = LocalTextStyle.current.copy(fontFeatureSettings = "tnum"),
            )
            Text(
                text = "Color  : ${state.accentColor.toHexString(HexFormat.Default)}",
                fontFamily = fontMonospace,
                style = LocalTextStyle.current.copy(fontFeatureSettings = "tnum"),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { listener.setAnimateColor(!state.animateColor) },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(4.dp))
            Checkbox(
                checked = state.animateColor,
                onCheckedChange = listener::setAnimateColor,
            )
            Text(
                text = "Enable color animation",
                fontFamily = fontMonospace,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = rememberDrawablePainter(drawable = drawable),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            OutlinedIconButton(
                modifier = Modifier.size(56.dp),
                onClick = { listener.togglePlayback() },
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = when (state.mainButton) {
                        MainButtonState.PLAY -> Icons.Default.PlayArrow
                        MainButtonState.PAUSE -> Icons.Default.Pause
                    },
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.width(32.dp))
            OutlinedIconButton(
                modifier = Modifier.size(56.dp),
                onClick = { listener.stopPlayback() },
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = Icons.Default.Stop,
                    contentDescription = null,
                )
            }
        }
        Text(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally),
            text = "Bad Apple Demo v${ctx.getVersionString()}\nby ShiftHackZ",
            fontFamily = fontMonospace,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
        OutlinedIconButton(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .align(Alignment.CenterHorizontally),
            onClick = {
                listener.pausePlayback()
                ctx.openWebPage(GITHUB)
            },
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Code,
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = "Source code",
                    fontFamily = fontMonospace,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
