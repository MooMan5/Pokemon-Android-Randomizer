package com.dabomstew.pkrandomandroid.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

// Dark-only theme: the app is designed around the dark navy + red palette.
// Dynamic color is intentionally disabled to preserve the branded identity.
private val AppColorScheme = darkColorScheme(
    primary                = Red,
    onPrimary              = White,
    primaryContainer       = RedContainer,
    onPrimaryContainer     = OnRedContainer,

    secondary              = Secondary,
    onSecondary            = White,
    secondaryContainer     = SecondaryContainer,
    onSecondaryContainer   = OnSecondaryContainer,

    tertiary               = Tertiary,
    onTertiary             = Background,
    tertiaryContainer      = TertiaryContainer,
    onTertiaryContainer    = OnTertiaryContainer,

    background             = Background,
    onBackground           = OnBackground,

    surface                = Surface,
    onSurface              = OnSurface,
    surfaceVariant         = SurfaceVariant,
    onSurfaceVariant       = OnSurfaceVariant,
    surfaceContainer       = SurfaceContainer,
    surfaceContainerHigh   = SurfaceVariant,

    outline                = Outline,
    outlineVariant         = OutlineVariant,

    error                  = Error,
    onError                = White,
    errorContainer         = ErrorContainer,
    onErrorContainer       = OnErrorContainer,
)

@Composable
fun PokemonRandomizerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography  = Typography,
        content     = content
    )
}
