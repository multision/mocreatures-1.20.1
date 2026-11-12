# Changelog

## Unreleased — Minecraft 1.20.1

### Community fixes

- Restore horse rear-leg animation during zebra shuffling. **Credit: Lollizaczek / Lollizak**, [PR #6](https://github.com/multision/mocreatures-1.20.1/pull/6).
- Restore the zebra-shuffle model state and prevent the nearby-jukebox lookup from throwing an uncaught exception. **Credit: Lollizaczek / Lollizak**, distributed by **Dinocraft-cz / Vaniron** in the [community fork](https://github.com/Dinocraft-cz/mocreatures-1.20.1/commits/main/). The integration uses a direct block-position loop in place of exception-based stream control.
- Repair elephant/mammoth kneeling and move rider placement into Minecraft's passenger-positioning callback to prevent competing positions and camera shaking. Allow mounting a kneeling elephant with storage attached, prevent repeated kneeling resets, and synchronize the end of kneeling on mounting. **Credit: ItsFadedMaybe / Faded**, [Elephant FIX](https://www.curseforge.com/minecraft/mc-mods/mo-creatures-elephant-fix).
- Repair the legacy big-cat layer mismatch and keep the mane aligned with the animated head/body, including sitting. **Credit for community fixes and investigation: Clickibait / ClickLarp and Pug**, [Nostalgia Edition - Fix](https://www.curseforge.com/minecraft/mc-mods/mo-creatures-nostalgia-edition-fix-legacy-model). Implemented independently in the existing source.
- Supply the required light-map values for tamed-shark health/name overlay vertices, addressing the rendering crash after hatching/taming. **Credit for the community shark fix and investigation: Clickibait / ClickLarp and Pug**; implemented independently here. Historical reports of other shark spawn crashes still need separate reproduction.
- Exempt tamed bunnies from distance despawning; preserve tamed aquatics and vanilla persistence flags in the custom aquatic despawn path. **Credit for the community bunny/dolphin persistence fix and investigation: Clickibait / ClickLarp and Pug**; implemented independently here.
- Let sugar lumps grow tamed juvenile elephants. **Credit for documenting this behavior in their 1.0.5 community fix: Clickibait / ClickLarp and Pug**; implemented independently here.

### Audit fixes

- Restore vanilla, dimension-aware hostile darkness/difficulty rules when no light limits are configured; preserve explicit JSON light overrides.
- Correct all ten malformed cooking conditions in nine loot tables. Non-burning animals drop raw food; burning animals use the matching cooked drop.
- Register renderers for the existing generic ogre and manticore IDs, retaining save and command compatibility.
- Give wyverns a working melee goal and wild-player target selection; sitting/ridden wyverns cannot run the new attack goal, and poison requires a successful hit.
- Accept the crafted MoC saddle on ostriches; retain saddle state across saving/loading.
- Stop feeding already-tamed horses and foxes from opening the first-time naming flow.
- Retain adult male white-lion manes after the winged transformation.
- Restore natural wyvern-leaf distance updates and decay; preserve player-placed leaves. Update the leaf loot conditions for 1.20.1, including shears.
- Drop fairy-horse parents' saddles, armor, storage chests, and contents exactly once before their intended disappearance.
- Connect aquatic ascent input, preserve intentional diving while idle, and identify the actual controlling passenger in the aquatic base class.
- Honor bypass damage for big golems so `/kill` works during every phase.
- Allow crouch-pickup of an empty kitty bed while holding an item; grant items on the server and drop overflow when the inventory is full.
- Initialize both vanilla and MoC hatchling age state. Let juvenile turtles grow and repair the old adult-at-90 state on loading.
- Accept ordinary pumpkins for horse feeding; restore the unreachable rare zebra selection and avoid overwriting existing horse variants during selection.
- Add smoker/campfire venison recipes and a Silk Touch alternative for wyvern nest blocks.
- Refresh armor-set effects only on the server, once per boots tick, preventing competing client effect updates.
- Protect pets from natural despawning without blocking normal chunk/dimension removal; remove the remaining dolphin, bird, and kitty removal vetoes.
- Prevent tamed animals, aquatics, and ambient pets from selecting or directly attacking their recorded owner. Prevent ogres from selecting creative/spectator attackers for retaliation.
- Add English subtitles for all 176 custom sound events; other languages use the English fallback until translated.
- Harden the dormant legacy block-ID helper against invalid uppercase identifiers and a null rewrite rule. No legacy migration is newly activated.

### Attribution and validation

- Add community credits to the Forge mod information, README, and bundled `CREDITS.md` / `CHANGELOG.md` files.
- Add build checks for legacy model-layer identifiers, model baking, and mane alignment through standing/sitting transitions, plus a dedicated-server GameTest suite for audit repairs.

Full contribution details and source references are in [CREDITS.md](CREDITS.md).
