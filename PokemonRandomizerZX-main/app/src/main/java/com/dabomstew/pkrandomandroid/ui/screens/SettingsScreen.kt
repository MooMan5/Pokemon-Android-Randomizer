package com.dabomstew.pkrandomandroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.dabomstew.pkrandom.Settings
import com.dabomstew.pkrandomandroid.ui.components.ExpandableSection
import com.dabomstew.pkrandomandroid.ui.components.LabeledSwitch
import com.dabomstew.pkrandomandroid.ui.components.RadioGroup
import com.dabomstew.pkrandomandroid.ui.components.SliderRow
import com.dabomstew.pkrandomandroid.viewmodel.RandomizerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: RandomizerViewModel,
    controllerConnected: Boolean,
    onBack: () -> Unit
) {
    // Local editable copy — mutations stay here until Save is tapped.
    // Going Back without saving discards all changes.
    val editableSettings = remember { viewModel.createEditableCopy() }
    // Local observable state for recomposition
    var raceMode by remember { mutableStateOf(editableSettings.isRaceMode) }
    var blockBrokenMoves by remember { mutableStateOf(editableSettings.doBlockBrokenMoves()) }
    var limitPokemon by remember { mutableStateOf(editableSettings.isLimitPokemon) }
    var banIrregularAltFormes by remember { mutableStateOf(editableSettings.isBanIrregularAltFormes) }
    var dualTypeOnly by remember { mutableStateOf(editableSettings.isDualTypeOnly) }

    var baseStatsMod by remember { mutableStateOf(editableSettings.baseStatisticsMod) }
    var baseStatsFollowEvos by remember { mutableStateOf(editableSettings.isBaseStatsFollowEvolutions) }
    var updateBaseStats by remember { mutableStateOf(editableSettings.isUpdateBaseStats) }
    var standardizeExpCurves by remember { mutableStateOf(editableSettings.isStandardizeEXPCurves) }

    var abilitiesMod by remember { mutableStateOf(editableSettings.abilitiesMod) }
    var allowWonderGuard by remember { mutableStateOf(editableSettings.isAllowWonderGuard) }
    var abilitiesFollowEvos by remember { mutableStateOf(editableSettings.isAbilitiesFollowEvolutions) }
    var banTrappingAbilities by remember { mutableStateOf(editableSettings.isBanTrappingAbilities) }
    var banNegativeAbilities by remember { mutableStateOf(editableSettings.isBanNegativeAbilities) }
    var banBadAbilities by remember { mutableStateOf(editableSettings.isBanBadAbilities) }

    var startersMod by remember { mutableStateOf(editableSettings.startersMod) }
    var randomizeStartersHeldItems by remember { mutableStateOf(editableSettings.isRandomizeStartersHeldItems) }

    var typesMod by remember { mutableStateOf(editableSettings.typesMod) }

    var evolutionsMod by remember { mutableStateOf(editableSettings.evolutionsMod) }
    var evosSimilarStrength by remember { mutableStateOf(editableSettings.isEvosSimilarStrength) }
    var evosSameTyping by remember { mutableStateOf(editableSettings.isEvosSameTyping) }
    var evosMaxThreeStages by remember { mutableStateOf(editableSettings.isEvosMaxThreeStages) }
    var evosForceChange by remember { mutableStateOf(editableSettings.isEvosForceChange) }

    var updateMoves by remember { mutableStateOf(editableSettings.isUpdateMoves) }
    var randomizeMovePowers by remember { mutableStateOf(editableSettings.isRandomizeMovePowers) }
    var randomizeMoveAccuracies by remember { mutableStateOf(editableSettings.isRandomizeMoveAccuracies) }
    var randomizeMovePPs by remember { mutableStateOf(editableSettings.isRandomizeMovePPs) }
    var randomizeMoveTypes by remember { mutableStateOf(editableSettings.isRandomizeMoveTypes) }
    var randomizeMoveCategory by remember { mutableStateOf(editableSettings.isRandomizeMoveCategory) }

    var movesetsMod by remember { mutableStateOf(editableSettings.movesetsMod) }
    var movesetsForceGoodDamaging by remember { mutableStateOf(editableSettings.isMovesetsForceGoodDamaging) }
    var startWithGuaranteedMoves by remember { mutableStateOf(editableSettings.isStartWithGuaranteedMoves) }
    var reorderDamagingMoves by remember { mutableStateOf(editableSettings.isReorderDamagingMoves) }

    var trainersMod by remember { mutableStateOf(editableSettings.trainersMod) }
    var trainersForceFullyEvolved by remember { mutableStateOf(editableSettings.isTrainersForceFullyEvolved) }
    var trainersLevelModified by remember { mutableStateOf(editableSettings.isTrainersLevelModified) }
    var trainersLevelModifier by remember { mutableStateOf(editableSettings.trainersLevelModifier) }
    var randomizeTrainerNames by remember { mutableStateOf(editableSettings.isRandomizeTrainerNames) }
    var additionalBossTrainerPokemon by remember { mutableStateOf(editableSettings.additionalBossTrainerPokemon) }

    var wildPokemonMod by remember { mutableStateOf(editableSettings.wildPokemonMod) }
    var wildRestrictionMod by remember { mutableStateOf(editableSettings.wildPokemonRestrictionMod) }
    var useMinimumCatchRate by remember { mutableStateOf(editableSettings.isUseMinimumCatchRate) }
    var wildLevelsModified by remember { mutableStateOf(editableSettings.isWildLevelsModified) }
    var wildLevelModifier by remember { mutableStateOf(editableSettings.wildLevelModifier) }

    var staticPokemonMod by remember { mutableStateOf(editableSettings.staticPokemonMod) }
    var staticLevelModified by remember { mutableStateOf(editableSettings.isStaticLevelModified) }
    var staticLevelModifier by remember { mutableStateOf(editableSettings.staticLevelModifier) }

    var tmsMod by remember { mutableStateOf(editableSettings.tmsMod) }
    var fullHMCompat by remember { mutableStateOf(editableSettings.isFullHMCompat) }
    var tmsHMsCompatMod by remember { mutableStateOf(editableSettings.tmsHmsCompatibilityMod) }
    var moveTutorMovesMod by remember { mutableStateOf(editableSettings.moveTutorMovesMod) }
    var moveTutorsCompatMod by remember { mutableStateOf(editableSettings.moveTutorsCompatibilityMod) }

    var fieldItemsMod by remember { mutableStateOf(editableSettings.fieldItemsMod) }
    var shopItemsMod by remember { mutableStateOf(editableSettings.shopItemsMod) }
    var pickupItemsMod by remember { mutableStateOf(editableSettings.pickupItemsMod) }

    var inGameTradesMod by remember { mutableStateOf(editableSettings.inGameTradesMod) }
    var randomizeTradeNicknames by remember { mutableStateOf(editableSettings.isRandomizeInGameTradesNicknames) }
    var randomizeTradeOTs by remember { mutableStateOf(editableSettings.isRandomizeInGameTradesOTs) }
    var randomizeTradeIVs by remember { mutableStateOf(editableSettings.isRandomizeInGameTradesIVs) }
    var randomizeTradeItems by remember { mutableStateOf(editableSettings.isRandomizeInGameTradesItems) }
    val firstFocusRequester = remember { FocusRequester() }

    LaunchedEffect(controllerConnected) {
        if (controllerConnected) {
            firstFocusRequester.requestFocus()
        }
    }

    // Helper — mutates the local copy only; nothing is committed until Save is tapped.
    fun applyToSettings(block: Settings.() -> Unit) {
        editableSettings.block()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Randomizer Settings", style = MaterialTheme.typography.titleLarge)
                        Text(
                            "Tap Save to confirm changes",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 3.dp
            ) {
                Button(
                    onClick = {
                        viewModel.commitSettings(editableSettings)
                        onBack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text("Save")
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp)
        ) {

            // 1. General
            item {
                ExpandableSection(
                    title = "1. General",
                    headerModifier = Modifier.focusRequester(firstFocusRequester)
                ) {
                    LabeledSwitch("Race mode", raceMode) {
                        raceMode = it; applyToSettings { setRaceMode(it) }
                    }
                    LabeledSwitch("Block broken moves", blockBrokenMoves) {
                        blockBrokenMoves = it; applyToSettings { setBlockBrokenMoves(it) }
                    }
                    LabeledSwitch("Limit Pokémon", limitPokemon) {
                        limitPokemon = it; applyToSettings { setLimitPokemon(it) }
                    }
                    LabeledSwitch("Ban irregular alt formes", banIrregularAltFormes) {
                        banIrregularAltFormes = it; applyToSettings { setBanIrregularAltFormes(it) }
                    }
                    LabeledSwitch("Dual type only", dualTypeOnly) {
                        dualTypeOnly = it; applyToSettings { setDualTypeOnly(it) }
                    }
                }
            }

            // 2. Base Statistics
            item {
                ExpandableSection("2. Base Statistics") {
                    RadioGroup(
                        label = "Mode",
                        options = listOf(
                            Settings.BaseStatisticsMod.UNCHANGED to "Unchanged",
                            Settings.BaseStatisticsMod.SHUFFLE to "Shuffle",
                            Settings.BaseStatisticsMod.RANDOM to "Random"
                        ),
                        selected = baseStatsMod,
                        onSelected = {
                            baseStatsMod = it
                            applyToSettings {
                                setBaseStatisticsMod(
                                    it == Settings.BaseStatisticsMod.UNCHANGED,
                                    it == Settings.BaseStatisticsMod.SHUFFLE,
                                    it == Settings.BaseStatisticsMod.RANDOM
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    LabeledSwitch("Follow evolutions", baseStatsFollowEvos) {
                        baseStatsFollowEvos = it; applyToSettings { setBaseStatsFollowEvolutions(it) }
                    }
                    LabeledSwitch("Update base stats to current gen", updateBaseStats) {
                        updateBaseStats = it; applyToSettings { setUpdateBaseStats(it) }
                    }
                    LabeledSwitch("Standardize EXP curves", standardizeExpCurves) {
                        standardizeExpCurves = it; applyToSettings { setStandardizeEXPCurves(it) }
                    }
                }
            }

            // 3. Abilities
            item {
                ExpandableSection("3. Abilities") {
                    RadioGroup(
                        label = "Mode",
                        options = listOf(
                            Settings.AbilitiesMod.UNCHANGED to "Unchanged",
                            Settings.AbilitiesMod.RANDOMIZE to "Randomize"
                        ),
                        selected = abilitiesMod,
                        onSelected = {
                            abilitiesMod = it
                            applyToSettings {
                                setAbilitiesMod(
                                    it == Settings.AbilitiesMod.UNCHANGED,
                                    it == Settings.AbilitiesMod.RANDOMIZE
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    LabeledSwitch("Allow Wonder Guard", allowWonderGuard) {
                        allowWonderGuard = it; applyToSettings { setAllowWonderGuard(it) }
                    }
                    LabeledSwitch("Follow evolutions", abilitiesFollowEvos) {
                        abilitiesFollowEvos = it; applyToSettings { setAbilitiesFollowEvolutions(it) }
                    }
                    LabeledSwitch("Ban trapping abilities", banTrappingAbilities) {
                        banTrappingAbilities = it; applyToSettings { setBanTrappingAbilities(it) }
                    }
                    LabeledSwitch("Ban negative abilities", banNegativeAbilities) {
                        banNegativeAbilities = it; applyToSettings { setBanNegativeAbilities(it) }
                    }
                    LabeledSwitch("Ban bad abilities", banBadAbilities) {
                        banBadAbilities = it; applyToSettings { setBanBadAbilities(it) }
                    }
                }
            }

            // 4. Starters
            item {
                ExpandableSection("4. Starters") {
                    RadioGroup(
                        label = "Mode",
                        options = listOf(
                            Settings.StartersMod.UNCHANGED to "Unchanged",
                            Settings.StartersMod.COMPLETELY_RANDOM to "Completely random",
                            Settings.StartersMod.RANDOM_WITH_TWO_EVOLUTIONS to "Random (2 evos)",
                            Settings.StartersMod.CUSTOM to "Custom"
                        ),
                        selected = startersMod,
                        onSelected = {
                            startersMod = it
                            applyToSettings {
                                setStartersMod(
                                    it == Settings.StartersMod.UNCHANGED,
                                    it == Settings.StartersMod.CUSTOM,
                                    it == Settings.StartersMod.COMPLETELY_RANDOM,
                                    it == Settings.StartersMod.RANDOM_WITH_TWO_EVOLUTIONS
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    LabeledSwitch("Randomize held items", randomizeStartersHeldItems) {
                        randomizeStartersHeldItems = it
                        applyToSettings { setRandomizeStartersHeldItems(it) }
                    }
                }
            }

            // 5. Types
            item {
                ExpandableSection("5. Types") {
                    RadioGroup(
                        label = "Mode",
                        options = listOf(
                            Settings.TypesMod.UNCHANGED to "Unchanged",
                            Settings.TypesMod.RANDOM_FOLLOW_EVOLUTIONS to "Follow evolutions",
                            Settings.TypesMod.COMPLETELY_RANDOM to "Completely random"
                        ),
                        selected = typesMod,
                        onSelected = {
                            typesMod = it
                            applyToSettings {
                                setTypesMod(
                                    it == Settings.TypesMod.UNCHANGED,
                                    it == Settings.TypesMod.RANDOM_FOLLOW_EVOLUTIONS,
                                    it == Settings.TypesMod.COMPLETELY_RANDOM
                                )
                            }
                        }
                    )
                }
            }

            // 6. Evolutions
            item {
                ExpandableSection("6. Evolutions") {
                    RadioGroup(
                        label = "Mode",
                        options = listOf(
                            Settings.EvolutionsMod.UNCHANGED to "Unchanged",
                            Settings.EvolutionsMod.RANDOM to "Random",
                            Settings.EvolutionsMod.RANDOM_EVERY_LEVEL to "Random every level"
                        ),
                        selected = evolutionsMod,
                        onSelected = {
                            evolutionsMod = it
                            applyToSettings {
                                setEvolutionsMod(
                                    it == Settings.EvolutionsMod.UNCHANGED,
                                    it == Settings.EvolutionsMod.RANDOM,
                                    it == Settings.EvolutionsMod.RANDOM_EVERY_LEVEL
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    LabeledSwitch("Similar strength", evosSimilarStrength) {
                        evosSimilarStrength = it; applyToSettings { setEvosSimilarStrength(it) }
                    }
                    LabeledSwitch("Same typing", evosSameTyping) {
                        evosSameTyping = it; applyToSettings { setEvosSameTyping(it) }
                    }
                    LabeledSwitch("Max 3 stages", evosMaxThreeStages) {
                        evosMaxThreeStages = it; applyToSettings { setEvosMaxThreeStages(it) }
                    }
                    LabeledSwitch("Force change", evosForceChange) {
                        evosForceChange = it; applyToSettings { setEvosForceChange(it) }
                    }
                }
            }

            // 7. Move Data
            item {
                ExpandableSection("7. Move Data") {
                    LabeledSwitch("Update moves to current gen", updateMoves) {
                        updateMoves = it; applyToSettings { setUpdateMoves(it) }
                    }
                    LabeledSwitch("Randomize move powers", randomizeMovePowers) {
                        randomizeMovePowers = it; applyToSettings { setRandomizeMovePowers(it) }
                    }
                    LabeledSwitch("Randomize move accuracies", randomizeMoveAccuracies) {
                        randomizeMoveAccuracies = it; applyToSettings { setRandomizeMoveAccuracies(it) }
                    }
                    LabeledSwitch("Randomize move PPs", randomizeMovePPs) {
                        randomizeMovePPs = it; applyToSettings { setRandomizeMovePPs(it) }
                    }
                    LabeledSwitch("Randomize move types", randomizeMoveTypes) {
                        randomizeMoveTypes = it; applyToSettings { setRandomizeMoveTypes(it) }
                    }
                    LabeledSwitch("Randomize move category", randomizeMoveCategory) {
                        randomizeMoveCategory = it; applyToSettings { setRandomizeMoveCategory(it) }
                    }
                }
            }

            // 8. Movesets
            item {
                ExpandableSection("8. Movesets") {
                    RadioGroup(
                        label = "Mode",
                        options = listOf(
                            Settings.MovesetsMod.UNCHANGED to "Unchanged",
                            Settings.MovesetsMod.RANDOM_PREFER_SAME_TYPE to "Random (prefer same type)",
                            Settings.MovesetsMod.COMPLETELY_RANDOM to "Completely random",
                            Settings.MovesetsMod.METRONOME_ONLY to "Metronome only"
                        ),
                        selected = movesetsMod,
                        onSelected = {
                            movesetsMod = it
                            applyToSettings {
                                setMovesetsMod(
                                    it == Settings.MovesetsMod.UNCHANGED,
                                    it == Settings.MovesetsMod.RANDOM_PREFER_SAME_TYPE,
                                    it == Settings.MovesetsMod.COMPLETELY_RANDOM,
                                    it == Settings.MovesetsMod.METRONOME_ONLY
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    LabeledSwitch("Force good damaging moves", movesetsForceGoodDamaging) {
                        movesetsForceGoodDamaging = it; applyToSettings { setMovesetsForceGoodDamaging(it) }
                    }
                    LabeledSwitch("Start with guaranteed moves", startWithGuaranteedMoves) {
                        startWithGuaranteedMoves = it; applyToSettings { setStartWithGuaranteedMoves(it) }
                    }
                    LabeledSwitch("Reorder damaging moves", reorderDamagingMoves) {
                        reorderDamagingMoves = it; applyToSettings { setReorderDamagingMoves(it) }
                    }
                }
            }

            // 9. Trainers
            item {
                ExpandableSection("9. Trainers") {
                    RadioGroup(
                        label = "Mode",
                        options = listOf(
                            Settings.TrainersMod.UNCHANGED to "Unchanged",
                            Settings.TrainersMod.RANDOM to "Random",
                            Settings.TrainersMod.DISTRIBUTED to "Distributed",
                            Settings.TrainersMod.MAINPLAYTHROUGH to "Main playthrough",
                            Settings.TrainersMod.TYPE_THEMED to "Type-themed",
                            Settings.TrainersMod.TYPE_THEMED_ELITE4_GYMS to "Type-themed (Elite 4 + Gyms)"
                        ),
                        selected = trainersMod,
                        onSelected = {
                            trainersMod = it
                            applyToSettings {
                                setTrainersMod(
                                    it == Settings.TrainersMod.UNCHANGED,
                                    it == Settings.TrainersMod.RANDOM,
                                    it == Settings.TrainersMod.DISTRIBUTED,
                                    it == Settings.TrainersMod.MAINPLAYTHROUGH,
                                    it == Settings.TrainersMod.TYPE_THEMED,
                                    it == Settings.TrainersMod.TYPE_THEMED_ELITE4_GYMS
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    LabeledSwitch("Force fully evolved", trainersForceFullyEvolved) {
                        trainersForceFullyEvolved = it; applyToSettings { setTrainersForceFullyEvolved(it) }
                    }
                    LabeledSwitch("Randomize trainer names", randomizeTrainerNames) {
                        randomizeTrainerNames = it; applyToSettings { setRandomizeTrainerNames(it) }
                    }
                    LabeledSwitch("Level modified", trainersLevelModified) {
                        trainersLevelModified = it; applyToSettings { setTrainersLevelModified(it) }
                    }
                    if (trainersLevelModified) {
                        Spacer(Modifier.height(4.dp))
                        SliderRow(
                            label = "Level modifier (%)",
                            value = trainersLevelModifier,
                            onValueChange = {
                                trainersLevelModifier = it
                                applyToSettings { setTrainersLevelModifier(it) }
                            },
                            range = -50..50
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    SliderRow(
                        label = "Extra Pokémon (boss trainers)",
                        value = additionalBossTrainerPokemon,
                        onValueChange = {
                            additionalBossTrainerPokemon = it
                            applyToSettings { setAdditionalBossTrainerPokemon(it) }
                        },
                        range = 0..4
                    )
                }
            }

            // 10. Wild Pokémon
            item {
                ExpandableSection("10. Wild Pokémon") {
                    RadioGroup(
                        label = "Mode",
                        options = listOf(
                            Settings.WildPokemonMod.UNCHANGED to "Unchanged",
                            Settings.WildPokemonMod.RANDOM to "Random",
                            Settings.WildPokemonMod.AREA_MAPPING to "Area mapping",
                            Settings.WildPokemonMod.GLOBAL_MAPPING to "Global mapping"
                        ),
                        selected = wildPokemonMod,
                        onSelected = {
                            wildPokemonMod = it
                            applyToSettings {
                                setWildPokemonMod(
                                    it == Settings.WildPokemonMod.UNCHANGED,
                                    it == Settings.WildPokemonMod.RANDOM,
                                    it == Settings.WildPokemonMod.AREA_MAPPING,
                                    it == Settings.WildPokemonMod.GLOBAL_MAPPING
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    RadioGroup(
                        label = "Restriction",
                        options = listOf(
                            Settings.WildPokemonRestrictionMod.NONE to "None",
                            Settings.WildPokemonRestrictionMod.SIMILAR_STRENGTH to "Similar strength",
                            Settings.WildPokemonRestrictionMod.CATCH_EM_ALL to "Catch-em-all",
                            Settings.WildPokemonRestrictionMod.TYPE_THEME_AREAS to "Type theme areas"
                        ),
                        selected = wildRestrictionMod,
                        onSelected = {
                            wildRestrictionMod = it
                            applyToSettings {
                                setWildPokemonRestrictionMod(
                                    it == Settings.WildPokemonRestrictionMod.NONE,
                                    it == Settings.WildPokemonRestrictionMod.SIMILAR_STRENGTH,
                                    it == Settings.WildPokemonRestrictionMod.CATCH_EM_ALL,
                                    it == Settings.WildPokemonRestrictionMod.TYPE_THEME_AREAS
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    LabeledSwitch("Use minimum catch rate", useMinimumCatchRate) {
                        useMinimumCatchRate = it; applyToSettings { setUseMinimumCatchRate(it) }
                    }
                    LabeledSwitch("Level modified", wildLevelsModified) {
                        wildLevelsModified = it; applyToSettings { setWildLevelsModified(it) }
                    }
                    if (wildLevelsModified) {
                        Spacer(Modifier.height(4.dp))
                        SliderRow(
                            label = "Level modifier (%)",
                            value = wildLevelModifier,
                            onValueChange = {
                                wildLevelModifier = it
                                applyToSettings { setWildLevelModifier(it) }
                            },
                            range = -50..50
                        )
                    }
                }
            }

            // 11. Static Pokémon
            item {
                ExpandableSection("11. Static Pokémon") {
                    RadioGroup(
                        label = "Mode",
                        options = listOf(
                            Settings.StaticPokemonMod.UNCHANGED to "Unchanged",
                            Settings.StaticPokemonMod.RANDOM_MATCHING to "Random matching",
                            Settings.StaticPokemonMod.COMPLETELY_RANDOM to "Completely random",
                            Settings.StaticPokemonMod.SIMILAR_STRENGTH to "Similar strength"
                        ),
                        selected = staticPokemonMod,
                        onSelected = {
                            staticPokemonMod = it
                            applyToSettings {
                                setStaticPokemonMod(
                                    it == Settings.StaticPokemonMod.UNCHANGED,
                                    it == Settings.StaticPokemonMod.RANDOM_MATCHING,
                                    it == Settings.StaticPokemonMod.COMPLETELY_RANDOM,
                                    it == Settings.StaticPokemonMod.SIMILAR_STRENGTH
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    LabeledSwitch("Level modified", staticLevelModified) {
                        staticLevelModified = it; applyToSettings { setStaticLevelModified(it) }
                    }
                    if (staticLevelModified) {
                        Spacer(Modifier.height(4.dp))
                        SliderRow(
                            label = "Level modifier (%)",
                            value = staticLevelModifier,
                            onValueChange = {
                                staticLevelModifier = it
                                applyToSettings { setStaticLevelModifier(it) }
                            },
                            range = -50..50
                        )
                    }
                }
            }

            // 12. TMs & Move Tutors
            item {
                ExpandableSection("12. TMs & Move Tutors") {
                    RadioGroup(
                        label = "TM mode",
                        options = listOf(
                            Settings.TMsMod.UNCHANGED to "Unchanged",
                            Settings.TMsMod.RANDOM to "Random"
                        ),
                        selected = tmsMod,
                        onSelected = {
                            tmsMod = it
                            applyToSettings {
                                setTmsMod(
                                    it == Settings.TMsMod.UNCHANGED,
                                    it == Settings.TMsMod.RANDOM
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    LabeledSwitch("Full HM compatibility", fullHMCompat) {
                        fullHMCompat = it; applyToSettings { setFullHMCompat(it) }
                    }
                    Spacer(Modifier.height(8.dp))
                    RadioGroup(
                        label = "TM/HM compatibility",
                        options = listOf(
                            Settings.TMsHMsCompatibilityMod.UNCHANGED to "Unchanged",
                            Settings.TMsHMsCompatibilityMod.RANDOM_PREFER_TYPE to "Random (prefer type)",
                            Settings.TMsHMsCompatibilityMod.COMPLETELY_RANDOM to "Completely random",
                            Settings.TMsHMsCompatibilityMod.FULL to "Full"
                        ),
                        selected = tmsHMsCompatMod,
                        onSelected = {
                            tmsHMsCompatMod = it
                            applyToSettings {
                                setTmsHmsCompatibilityMod(
                                    it == Settings.TMsHMsCompatibilityMod.UNCHANGED,
                                    it == Settings.TMsHMsCompatibilityMod.RANDOM_PREFER_TYPE,
                                    it == Settings.TMsHMsCompatibilityMod.COMPLETELY_RANDOM,
                                    it == Settings.TMsHMsCompatibilityMod.FULL
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    RadioGroup(
                        label = "Move tutor mode",
                        options = listOf(
                            Settings.MoveTutorMovesMod.UNCHANGED to "Unchanged",
                            Settings.MoveTutorMovesMod.RANDOM to "Random"
                        ),
                        selected = moveTutorMovesMod,
                        onSelected = {
                            moveTutorMovesMod = it
                            applyToSettings {
                                setMoveTutorMovesMod(
                                    it == Settings.MoveTutorMovesMod.UNCHANGED,
                                    it == Settings.MoveTutorMovesMod.RANDOM
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    RadioGroup(
                        label = "Move tutor compatibility",
                        options = listOf(
                            Settings.MoveTutorsCompatibilityMod.UNCHANGED to "Unchanged",
                            Settings.MoveTutorsCompatibilityMod.RANDOM_PREFER_TYPE to "Random (prefer type)",
                            Settings.MoveTutorsCompatibilityMod.COMPLETELY_RANDOM to "Completely random",
                            Settings.MoveTutorsCompatibilityMod.FULL to "Full"
                        ),
                        selected = moveTutorsCompatMod,
                        onSelected = {
                            moveTutorsCompatMod = it
                            applyToSettings {
                                setMoveTutorsCompatibilityMod(
                                    it == Settings.MoveTutorsCompatibilityMod.UNCHANGED,
                                    it == Settings.MoveTutorsCompatibilityMod.RANDOM_PREFER_TYPE,
                                    it == Settings.MoveTutorsCompatibilityMod.COMPLETELY_RANDOM,
                                    it == Settings.MoveTutorsCompatibilityMod.FULL
                                )
                            }
                        }
                    )
                }
            }

            // 13. Items
            item {
                ExpandableSection("13. Items") {
                    RadioGroup(
                        label = "Field items",
                        options = listOf(
                            Settings.FieldItemsMod.UNCHANGED to "Unchanged",
                            Settings.FieldItemsMod.SHUFFLE to "Shuffle",
                            Settings.FieldItemsMod.RANDOM to "Random",
                            Settings.FieldItemsMod.RANDOM_EVEN to "Random (even)"
                        ),
                        selected = fieldItemsMod,
                        onSelected = {
                            fieldItemsMod = it
                            applyToSettings {
                                setFieldItemsMod(
                                    it == Settings.FieldItemsMod.UNCHANGED,
                                    it == Settings.FieldItemsMod.SHUFFLE,
                                    it == Settings.FieldItemsMod.RANDOM,
                                    it == Settings.FieldItemsMod.RANDOM_EVEN
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    RadioGroup(
                        label = "Shop items",
                        options = listOf(
                            Settings.ShopItemsMod.UNCHANGED to "Unchanged",
                            Settings.ShopItemsMod.SHUFFLE to "Shuffle",
                            Settings.ShopItemsMod.RANDOM to "Random"
                        ),
                        selected = shopItemsMod,
                        onSelected = {
                            shopItemsMod = it
                            applyToSettings {
                                setShopItemsMod(
                                    it == Settings.ShopItemsMod.UNCHANGED,
                                    it == Settings.ShopItemsMod.SHUFFLE,
                                    it == Settings.ShopItemsMod.RANDOM
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    RadioGroup(
                        label = "Pickup items",
                        options = listOf(
                            Settings.PickupItemsMod.UNCHANGED to "Unchanged",
                            Settings.PickupItemsMod.RANDOM to "Random"
                        ),
                        selected = pickupItemsMod,
                        onSelected = {
                            pickupItemsMod = it
                            applyToSettings {
                                setPickupItemsMod(
                                    it == Settings.PickupItemsMod.UNCHANGED,
                                    it == Settings.PickupItemsMod.RANDOM
                                )
                            }
                        }
                    )
                }
            }

            // 14. In-Game Trades
            item {
                ExpandableSection("14. In-Game Trades") {
                    RadioGroup(
                        label = "Mode",
                        options = listOf(
                            Settings.InGameTradesMod.UNCHANGED to "Unchanged",
                            Settings.InGameTradesMod.RANDOMIZE_GIVEN to "Randomize given",
                            Settings.InGameTradesMod.RANDOMIZE_GIVEN_AND_REQUESTED to "Randomize given & requested"
                        ),
                        selected = inGameTradesMod,
                        onSelected = {
                            inGameTradesMod = it
                            applyToSettings {
                                setInGameTradesMod(
                                    it == Settings.InGameTradesMod.UNCHANGED,
                                    it == Settings.InGameTradesMod.RANDOMIZE_GIVEN,
                                    it == Settings.InGameTradesMod.RANDOMIZE_GIVEN_AND_REQUESTED
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    LabeledSwitch("Randomize nicknames", randomizeTradeNicknames) {
                        randomizeTradeNicknames = it; applyToSettings { setRandomizeInGameTradesNicknames(it) }
                    }
                    LabeledSwitch("Randomize OT", randomizeTradeOTs) {
                        randomizeTradeOTs = it; applyToSettings { setRandomizeInGameTradesOTs(it) }
                    }
                    LabeledSwitch("Randomize IVs", randomizeTradeIVs) {
                        randomizeTradeIVs = it; applyToSettings { setRandomizeInGameTradesIVs(it) }
                    }
                    LabeledSwitch("Randomize items", randomizeTradeItems) {
                        randomizeTradeItems = it; applyToSettings { setRandomizeInGameTradesItems(it) }
                    }
                }
            }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}
