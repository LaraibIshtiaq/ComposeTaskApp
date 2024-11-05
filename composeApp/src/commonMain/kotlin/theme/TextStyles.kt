package theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import composetaskapp.composeapp.generated.resources.Res
import composetaskapp.composeapp.generated.resources.kalam_bold
import composetaskapp.composeapp.generated.resources.kalam_light
import composetaskapp.composeapp.generated.resources.kalam_regular
import org.jetbrains.compose.resources.Font


@Composable
fun Kalam() = FontFamily(
    Font(Res.font.kalam_light, weight = FontWeight.Light),
    Font(Res.font.kalam_regular, FontWeight.Medium),
    Font(Res.font.kalam_bold, FontWeight.Bold)
)

@Composable
fun Typography() = androidx.compose.material.Typography(
    h6 = TextStyle(
        fontFamily = Kalam(),
        fontWeight = FontWeight.Bold,
        fontSize = 21.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Kalam(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = Kalam(),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = Kalam(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = Kalam(),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = Kalam(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp
    ),
    caption = TextStyle(
        fontFamily = Kalam()
    ),
    overline = TextStyle(
        fontFamily = Kalam()
    )
)