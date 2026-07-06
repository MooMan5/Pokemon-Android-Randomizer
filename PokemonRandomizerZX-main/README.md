# Pokemon Randomizer ZX — Android

An unofficial Android port of [Universal Pokemon Randomizer ZX](https://github.com/Ajarmar/universal-pokemon-randomizer-zx) by Ajarmar, built with Jetpack Compose.

> **Disclaimer**: Pokémon and all related names, trademarks, and intellectual property are © Nintendo / Game Freak / The Pokémon Company. This project does not distribute any ROM files or copyrighted game content. You must own a legal copy of the ROM you wish to randomize.

---

## Features

- Randomize Gen 1–7 Pokémon ROMs directly on your Android device
- Full settings screen covering all 14 randomization categories (Base Stats, Abilities, Starters, Types, Evolutions, Movesets, Trainers, Wild Pokémon, Static Pokémon, TMs, Items, In-Game Trades, and more)
- Save and load preset files (`.rnqs`)
- Manual or random seed control
- Randomization log viewer with share functionality
- No internet permission required — all processing happens on-device

## Requirements

- Android 8.0 (API 26) or higher
- A ROM file for a supported Pokémon game (Gen 1–7)

## Usage

1. Tap **Pick ROM** and select your input ROM file
2. Tap **Set Output** and choose where to save the randomized ROM
3. Tap **Settings** to configure randomization options
4. (Optional) Load a preset with **Load Preset**
5. Tap **Randomize!**

## Building

```bash
# Clone the repository
git clone https://github.com/bielsoler98/PokemonRandomizerZX.git
cd PokemonRandomizerZX

# Build debug APK
./gradlew assembleDebug
```

Requires Android Studio or the Android SDK with at least API 36.

## Credits & License

This project is an Android port of:

- **Universal Pokemon Randomizer ZX** by [Ajarmar](https://github.com/Ajarmar/universal-pokemon-randomizer-zx)
  — with significant contributions from darkeye and cleartonic
- **Universal Pokemon Randomizer** by [Dabomstew](https://github.com/Dabomstew/universal-pokemon-randomizer)

All core randomization logic originates from those projects and is used under the terms of the **GNU General Public License v3** (GPL-3.0). This Android port is likewise released under GPL-3.0. See [LICENSE](LICENSE) for the full license text.

The Android UI layer (Kotlin/Jetpack Compose code under `com.dabomstew.pkrandomandroid`) was written as part of this port.
