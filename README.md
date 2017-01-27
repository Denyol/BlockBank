# BlockBank

## About
An in game economy solution mod, coin forging, counterfeits, bank vaults!

## Issues & Contacts
* [IRC #BlockBank on irc.Esper.net](http://webchat.esper.net/?channels=blockbank&prompt=1)

Please include the following in an issue report:
* Detailed title, summarizing the issue
* BlockBank version
* Minecraft version
* Forge version
* A list of other mods and their version, if they're related to the issue
* Screenshots (if need be)
* Crashes
  * ForgeModLoader log and Minecraft crash log
  * Steps to reproduce
  * What was expected?
  * Single player or Server
  
Please provide as much information as possible to the issue report.

## Licenses
This project and its assets are licensed under the [GNU GPL-3 License](https://tldrlegal.com/license/gnu-general-public-license-v3-(gpl-3)).

Any alternative licenses are noted where applicable.

This mod may be included in any modpack, as long as the modpack is free to access.

# Compiling
1. Clone the repo
  - SSH `git git@github.com:dan14941/BlockBank.git` or 
  - HTTPS `git clone https://github.com/dan14941/BlockBank.git`
2. Setup the workspace
  - `gradlew setupDecompWorkspace` or `./gradlew setupDecompWorkspace`
3. Setup IDE
  - IntelliJ: Import build.gradle, run `gradlew genIntellijRuns`
  - Eclipse: Run `gradlew eclipse`
  - As per the [Forge Docs](https://mcforge.readthedocs.io/en/latest/gettingstarted/)
