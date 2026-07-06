package com.dabomstew.pkrandomandroid.util

import com.dabomstew.pkrandom.Settings

object SettingsSummary {

    /**
     * Returns a human-readable list of settings that differ from the defaults.
     * An empty list means everything is at default.
     */
    fun getChangedSettings(s: Settings): List<String> {
        val d = Settings() // fresh defaults to diff against
        val changes = mutableListOf<String>()

        // ── General ──────────────────────────────────────────────────
        if (s.isRaceMode != d.isRaceMode) changes += "Race mode"
        if (s.doBlockBrokenMoves() != d.doBlockBrokenMoves()) changes += "Block broken moves"
        if (s.isLimitPokemon != d.isLimitPokemon) changes += "Limit Pokémon"
        if (s.isBanIrregularAltFormes != d.isBanIrregularAltFormes) changes += "Ban irregular alt formes"
        if (s.isDualTypeOnly != d.isDualTypeOnly) changes += "Dual type only"

        // ── Base Statistics ───────────────────────────────────────────
        if (s.baseStatisticsMod != d.baseStatisticsMod)
            changes += "Base stats: ${s.baseStatisticsMod.label()}"
        if (s.isBaseStatsFollowEvolutions != d.isBaseStatsFollowEvolutions)
            changes += "Base stats follow evolutions"
        if (s.isUpdateBaseStats != d.isUpdateBaseStats)
            changes += "Update base stats"
        if (s.isStandardizeEXPCurves != d.isStandardizeEXPCurves)
            changes += "Standardize EXP curves"

        // ── Abilities ─────────────────────────────────────────────────
        if (s.abilitiesMod != d.abilitiesMod)
            changes += "Abilities: ${s.abilitiesMod.label()}"
        if (s.isAllowWonderGuard != d.isAllowWonderGuard)
            changes += if (s.isAllowWonderGuard) "Allow Wonder Guard" else "No Wonder Guard"
        if (s.isAbilitiesFollowEvolutions != d.isAbilitiesFollowEvolutions)
            changes += "Abilities follow evolutions"
        if (s.isBanTrappingAbilities != d.isBanTrappingAbilities) changes += "Ban trapping abilities"
        if (s.isBanNegativeAbilities != d.isBanNegativeAbilities) changes += "Ban negative abilities"
        if (s.isBanBadAbilities != d.isBanBadAbilities) changes += "Ban bad abilities"

        // ── Starters ─────────────────────────────────────────────────
        if (s.startersMod != d.startersMod)
            changes += "Starters: ${s.startersMod.label()}"
        if (s.isRandomizeStartersHeldItems != d.isRandomizeStartersHeldItems)
            changes += "Randomize starter held items"

        // ── Types ─────────────────────────────────────────────────────
        if (s.typesMod != d.typesMod)
            changes += "Types: ${s.typesMod.label()}"

        // ── Evolutions ────────────────────────────────────────────────
        if (s.evolutionsMod != d.evolutionsMod)
            changes += "Evolutions: ${s.evolutionsMod.label()}"
        if (s.isEvosSimilarStrength != d.isEvosSimilarStrength) changes += "Evos: similar strength"
        if (s.isEvosSameTyping != d.isEvosSameTyping) changes += "Evos: same typing"
        if (s.isEvosMaxThreeStages != d.isEvosMaxThreeStages) changes += "Evos: max 3 stages"
        if (s.isEvosForceChange != d.isEvosForceChange) changes += "Evos: force change"

        // ── Move Data ─────────────────────────────────────────────────
        if (s.isUpdateMoves != d.isUpdateMoves) changes += "Update moves"
        if (s.isRandomizeMovePowers != d.isRandomizeMovePowers) changes += "Randomize move powers"
        if (s.isRandomizeMoveAccuracies != d.isRandomizeMoveAccuracies) changes += "Randomize move accuracies"
        if (s.isRandomizeMovePPs != d.isRandomizeMovePPs) changes += "Randomize move PPs"
        if (s.isRandomizeMoveTypes != d.isRandomizeMoveTypes) changes += "Randomize move types"
        if (s.isRandomizeMoveCategory != d.isRandomizeMoveCategory) changes += "Randomize move categories"

        // ── Movesets ──────────────────────────────────────────────────
        if (s.movesetsMod != d.movesetsMod)
            changes += "Movesets: ${s.movesetsMod.label()}"
        if (s.isMovesetsForceGoodDamaging != d.isMovesetsForceGoodDamaging)
            changes += "Movesets: force good damaging"
        if (s.isStartWithGuaranteedMoves != d.isStartWithGuaranteedMoves)
            changes += "Movesets: guaranteed level-1 moves"
        if (s.isReorderDamagingMoves != d.isReorderDamagingMoves)
            changes += "Movesets: reorder damaging moves"

        // ── Trainers ──────────────────────────────────────────────────
        if (s.trainersMod != d.trainersMod)
            changes += "Trainers: ${s.trainersMod.label()}"
        if (s.isTrainersForceFullyEvolved != d.isTrainersForceFullyEvolved)
            changes += "Trainers: force fully evolved"
        if (s.isRandomizeTrainerNames != d.isRandomizeTrainerNames)
            changes += "Randomize trainer names"
        if (s.isTrainersLevelModified != d.isTrainersLevelModified)
            changes += "Trainer level modifier: ${if (s.trainersLevelModifier >= 0) "+" else ""}${s.trainersLevelModifier}%"
        if (s.additionalBossTrainerPokemon != d.additionalBossTrainerPokemon)
            changes += "Boss trainers +${s.additionalBossTrainerPokemon} Pokémon"

        // ── Wild Pokémon ──────────────────────────────────────────────
        if (s.wildPokemonMod != d.wildPokemonMod)
            changes += "Wild Pokémon: ${s.wildPokemonMod.label()}"
        if (s.wildPokemonRestrictionMod != d.wildPokemonRestrictionMod)
            changes += "Wild restriction: ${s.wildPokemonRestrictionMod.label()}"
        if (s.isUseMinimumCatchRate != d.isUseMinimumCatchRate)
            changes += "Minimum catch rate"
        if (s.isWildLevelsModified != d.isWildLevelsModified)
            changes += "Wild level modifier: ${if (s.wildLevelModifier >= 0) "+" else ""}${s.wildLevelModifier}%"

        // ── Static Pokémon ────────────────────────────────────────────
        if (s.staticPokemonMod != d.staticPokemonMod)
            changes += "Static Pokémon: ${s.staticPokemonMod.label()}"
        if (s.isStaticLevelModified != d.isStaticLevelModified)
            changes += "Static level modifier: ${if (s.staticLevelModifier >= 0) "+" else ""}${s.staticLevelModifier}%"

        // ── TMs & Move Tutors ─────────────────────────────────────────
        if (s.tmsMod != d.tmsMod)
            changes += "TMs: ${s.tmsMod.label()}"
        if (s.isFullHMCompat != d.isFullHMCompat) changes += "Full HM compatibility"
        if (s.tmsHmsCompatibilityMod != d.tmsHmsCompatibilityMod)
            changes += "TM/HM compat: ${s.tmsHmsCompatibilityMod.label()}"
        if (s.moveTutorMovesMod != d.moveTutorMovesMod)
            changes += "Move tutors: ${s.moveTutorMovesMod.label()}"
        if (s.moveTutorsCompatibilityMod != d.moveTutorsCompatibilityMod)
            changes += "Tutor compat: ${s.moveTutorsCompatibilityMod.label()}"

        // ── Items ─────────────────────────────────────────────────────
        if (s.fieldItemsMod != d.fieldItemsMod)
            changes += "Field items: ${s.fieldItemsMod.label()}"
        if (s.shopItemsMod != d.shopItemsMod)
            changes += "Shop items: ${s.shopItemsMod.label()}"
        if (s.pickupItemsMod != d.pickupItemsMod)
            changes += "Pickup items: ${s.pickupItemsMod.label()}"

        // ── In-Game Trades ────────────────────────────────────────────
        if (s.inGameTradesMod != d.inGameTradesMod)
            changes += "In-game trades: ${s.inGameTradesMod.label()}"
        if (s.isRandomizeInGameTradesNicknames != d.isRandomizeInGameTradesNicknames)
            changes += "Randomize trade nicknames"
        if (s.isRandomizeInGameTradesOTs != d.isRandomizeInGameTradesOTs)
            changes += "Randomize trade OTs"
        if (s.isRandomizeInGameTradesIVs != d.isRandomizeInGameTradesIVs)
            changes += "Randomize trade IVs"
        if (s.isRandomizeInGameTradesItems != d.isRandomizeInGameTradesItems)
            changes += "Randomize trade items"

        return changes
    }

    private fun Enum<*>.label(): String =
        name.lowercase().replace('_', ' ')
}
