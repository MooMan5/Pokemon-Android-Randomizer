package com.dabomstew.pkrandomandroid.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsInfoDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Settings Info") },
        text = {
            LazyColumn(modifier = Modifier.heightIn(max = 460.dp)) {
                settingsInfoSections.forEach { section ->
                    item {
                        Text(
                            text = section.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(6.dp))
                    }
                    items(section.items) { item ->
                        Column {
                            Text(item.title, style = MaterialTheme.typography.labelLarge)
                            Text(item.description, style = MaterialTheme.typography.bodySmall)
                            Spacer(Modifier.height(10.dp))
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done")
            }
        }
    )
}

private data class SettingsInfoSection(
    val title: String,
    val items: List<SettingsInfoItem>
)

private data class SettingsInfoItem(
    val title: String,
    val description: String
)

private val settingsInfoSections = listOf(
    SettingsInfoSection(
        title = "1. General",
        items = listOf(
            SettingsInfoItem("Race mode", "Hides spoiler-heavy output where supported so randomized ROMs can be shared without revealing key results."),
            SettingsInfoItem("Block broken moves", "Prevents moves with known game-breaking behavior from being used by randomization."),
            SettingsInfoItem("Limit Pokemon", "Restricts randomized Pokemon to the generations or availability rules supported by the selected ROM."),
            SettingsInfoItem("Ban irregular alt formes", "Avoids unusual alternate forms that may not behave consistently in normal gameplay."),
            SettingsInfoItem("Dual type only", "Limits eligible Pokemon to species with two types when a Pokemon pool is being filtered.")
        )
    ),
    SettingsInfoSection(
        title = "2. Base Statistics",
        items = listOf(
            SettingsInfoItem("Mode", "Unchanged keeps stats as-is. Shuffle rearranges each Pokemon's existing stats. Random generates new stat spreads."),
            SettingsInfoItem("Follow evolutions", "Keeps evolved forms stronger than their pre-evolutions when base stats are changed."),
            SettingsInfoItem("Update base stats to current gen", "Applies later-generation official stat changes to older games where supported."),
            SettingsInfoItem("Standardize EXP curves", "Makes experience growth curves more consistent across Pokemon.")
        )
    ),
    SettingsInfoSection(
        title = "3. Abilities",
        items = listOf(
            SettingsInfoItem("Mode", "Unchanged keeps normal abilities. Randomize assigns new abilities to Pokemon."),
            SettingsInfoItem("Allow Wonder Guard", "Allows Wonder Guard in the randomized ability pool; this can create very powerful Pokemon."),
            SettingsInfoItem("Follow evolutions", "Keeps related evolutionary families using related ability choices."),
            SettingsInfoItem("Ban trapping abilities", "Removes abilities that prevent switching, such as Shadow Tag-style effects."),
            SettingsInfoItem("Ban negative abilities", "Removes intentionally harmful abilities from the random pool."),
            SettingsInfoItem("Ban bad abilities", "Removes weak or generally undesirable abilities from the random pool.")
        )
    ),
    SettingsInfoSection(
        title = "4. Starters",
        items = listOf(
            SettingsInfoItem("Mode", "Chooses how starter Pokemon are replaced: unchanged, fully random, random Pokemon with two evolutions, or custom where supported."),
            SettingsInfoItem("Randomize held items", "Changes the items held by starter Pokemon when the ROM supports starter held items.")
        )
    ),
    SettingsInfoSection(
        title = "5. Types",
        items = listOf(
            SettingsInfoItem("Mode", "Unchanged keeps normal typings. Follow evolutions randomizes while keeping evolution lines related. Completely random rerolls typings freely.")
        )
    ),
    SettingsInfoSection(
        title = "6. Evolutions",
        items = listOf(
            SettingsInfoItem("Mode", "Random changes evolution targets. Random every level creates frequent evolution changes where supported."),
            SettingsInfoItem("Similar strength", "Tries to replace evolutions with Pokemon of comparable overall strength."),
            SettingsInfoItem("Same typing", "Prefers evolution replacements that share typing with the original."),
            SettingsInfoItem("Max 3 stages", "Prevents evolution chains from becoming longer than normal three-stage lines."),
            SettingsInfoItem("Force change", "Requires evolution targets to be different from the original targets.")
        )
    ),
    SettingsInfoSection(
        title = "7. Move Data",
        items = listOf(
            SettingsInfoItem("Update moves to current gen", "Applies newer official move data to older games where supported."),
            SettingsInfoItem("Randomize move powers", "Changes attack power values."),
            SettingsInfoItem("Randomize move accuracies", "Changes hit chance values."),
            SettingsInfoItem("Randomize move PPs", "Changes how many times moves can be used."),
            SettingsInfoItem("Randomize move types", "Changes move elemental types."),
            SettingsInfoItem("Randomize move category", "Changes physical, special, or status categories where the game supports categories.")
        )
    ),
    SettingsInfoSection(
        title = "8. Movesets",
        items = listOf(
            SettingsInfoItem("Mode", "Controls how Pokemon learnsets are changed, from unchanged to randomized or special modes such as metronome-only."),
            SettingsInfoItem("Force good damaging moves", "Biases generated movesets so Pokemon receive usable damaging moves."),
            SettingsInfoItem("Start with guaranteed moves", "Ensures Pokemon begin with a practical set of moves at low levels."),
            SettingsInfoItem("Reorder damaging moves", "Moves stronger damaging moves later in learnsets to smooth early-game balance.")
        )
    ),
    SettingsInfoSection(
        title = "9. Trainers",
        items = listOf(
            SettingsInfoItem("Mode", "Controls trainer Pokemon replacement. Options range from fully random to distributed, type-themed, or main-playthrough focused teams."),
            SettingsInfoItem("Force fully evolved", "Makes eligible trainer Pokemon fully evolved based on level rules."),
            SettingsInfoItem("Randomize trainer names", "Changes trainer names where supported."),
            SettingsInfoItem("Level modified", "Enables trainer level scaling."),
            SettingsInfoItem("Level modifier (%)", "Raises or lowers trainer levels by the selected percentage."),
            SettingsInfoItem("Extra Pokemon (boss trainers)", "Adds extra Pokemon to major trainer battles where supported.")
        )
    ),
    SettingsInfoSection(
        title = "10. Wild Pokemon",
        items = listOf(
            SettingsInfoItem("Mode", "Controls wild encounter replacement: fully random, mapped by area, or mapped globally."),
            SettingsInfoItem("Restriction", "Narrows wild replacements by strength, catch-em-all coverage, type themes, or no restriction."),
            SettingsInfoItem("Use minimum catch rate", "Raises very low catch rates so randomized encounters are less frustrating to capture."),
            SettingsInfoItem("Level modified", "Enables wild encounter level scaling."),
            SettingsInfoItem("Level modifier (%)", "Raises or lowers wild Pokemon levels by the selected percentage.")
        )
    ),
    SettingsInfoSection(
        title = "11. Static Pokemon",
        items = listOf(
            SettingsInfoItem("Mode", "Changes fixed encounters such as gifts, legendaries, or overworld Pokemon where supported."),
            SettingsInfoItem("Level modified", "Enables level scaling for static encounters."),
            SettingsInfoItem("Level modifier (%)", "Raises or lowers static encounter levels by the selected percentage.")
        )
    ),
    SettingsInfoSection(
        title = "12. TMs & Move Tutors",
        items = listOf(
            SettingsInfoItem("TM mode", "Changes which moves TMs contain."),
            SettingsInfoItem("Full HM compatibility", "Lets all Pokemon learn required HMs, reducing the chance of progression blockers."),
            SettingsInfoItem("TM/HM compatibility", "Controls which Pokemon can learn TMs and HMs, from unchanged to random or full compatibility."),
            SettingsInfoItem("Move tutor mode", "Changes move tutor move offerings where supported."),
            SettingsInfoItem("Move tutor compatibility", "Controls which Pokemon can learn tutor moves.")
        )
    ),
    SettingsInfoSection(
        title = "13. Items",
        items = listOf(
            SettingsInfoItem("Field items", "Changes items found on the ground or in item balls."),
            SettingsInfoItem("Shop items", "Changes items sold by shops."),
            SettingsInfoItem("Pickup items", "Changes items obtained from the Pickup ability where supported.")
        )
    ),
    SettingsInfoSection(
        title = "14. In-Game Trades",
        items = listOf(
            SettingsInfoItem("Mode", "Changes Pokemon offered or requested by NPC trades."),
            SettingsInfoItem("Randomize nicknames", "Changes nicknames on traded Pokemon."),
            SettingsInfoItem("Randomize OT", "Changes original trainer names on traded Pokemon."),
            SettingsInfoItem("Randomize IVs", "Changes individual values for traded Pokemon."),
            SettingsInfoItem("Randomize items", "Changes held items on traded Pokemon.")
        )
    )
)
