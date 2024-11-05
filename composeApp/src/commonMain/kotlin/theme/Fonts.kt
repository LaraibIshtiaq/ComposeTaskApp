package theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import composetaskapp.composeapp.generated.resources.Res
import composetaskapp.composeapp.generated.resources.kalam_bold
import composetaskapp.composeapp.generated.resources.kalam_light
import composetaskapp.composeapp.generated.resources.kalam_regular
import org.jetbrains.compose.resources.Font

@Composable
fun BoldKalamFontFamily() = FontFamily(Font(Res.font.kalam_bold))

@Composable
fun LightKalamFontFamily() = FontFamily(Font(Res.font.kalam_light))

@Composable
fun RegularKalamFontFamily() = FontFamily(Font(Res.font.kalam_regular))