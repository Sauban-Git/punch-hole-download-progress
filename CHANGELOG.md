

## What's Changed in [v1.0.0-alpha4](https://github.com/hxreborn/punch-hole-download-progress/releases/tag/v1.0.0-alpha4) (2026-01-18)


### Added

- [bfa64bf](https://github.com/hxreborn/punch-hole-download-progress/commit/bfa64bff0c1b23a73c20875227887eefb4b58d78) *(ui)* Add version info section in settings by [@hxreborn](https://github.com/hxreborn)

  > Display version name and code in a new About section
  > on the System screen.


### Fixed

- [59c32a6](https://github.com/hxreborn/punch-hole-download-progress/commit/59c32a6a7ab5a6ac66c85f4e0fe9002e823b51a0) *(ci)* Use dynamic contributor from GitHub API by [@hxreborn](https://github.com/hxreborn)

  > Show contributor only when API returns username, with proper
  > profile link. Works in Actions with GITHUB_TOKEN.


### Changed

- [031df0f](https://github.com/hxreborn/punch-hole-download-progress/commit/031df0f86b879d9fbf5afa78230f9301047539f6) *(ci)* Simplify changelog contributor format by [@hxreborn](https://github.com/hxreborn)

  > - Remove "commit" prefix, show "(by @user)"
  > - Group commits by scope within each type
  > - Add editorconfig rules for toml/yaml/md

- [2314a4e](https://github.com/hxreborn/punch-hole-download-progress/commit/2314a4ea693b0a2cd249dca3b9d2e8ff04142966) *(ci)* Include version in changelog header by [@hxreborn](https://github.com/hxreborn)

- [b0f920b](https://github.com/hxreborn/punch-hole-download-progress/commit/b0f920bcf067a0283ef3e65af9e5db4155a0b040) *(ci)* Distinguish merged PRs from direct commits by [@hxreborn](https://github.com/hxreborn)

  > Show "(merged by @user in #PR)" for PRs, "(commit by @user)" for
  > direct commits.

- [e3e2cc3](https://github.com/hxreborn/punch-hole-download-progress/commit/e3e2cc349b08d6e81b883be09505f25a563a8d83) *(ci)* Show commit hash first in changelog entries by [@hxreborn](https://github.com/hxreborn)

  > Format:`hash` - message (commit by @user)


### Documentation

- [9538d08](https://github.com/hxreborn/punch-hole-download-progress/commit/9538d08d0c81331902bad0e881c6d7cb1205a660) Add LSPosed Next as alternative framework by [@hxreborn](https://github.com/hxreborn)



## What's Changed in [v1.0.0-alpha2](https://github.com/hxreborn/punch-hole-download-progress/releases/tag/v1.0.0-alpha2) (2026-01-18)


### Added

- [b497438](https://github.com/hxreborn/punch-hole-download-progress/commit/b497438c6948c6eaec5ea29984f8fa652cd68e99) *(ci)* Add git-cliff changelog generation by [@hxreborn](https://github.com/hxreborn)

- [9080a95](https://github.com/hxreborn/punch-hole-download-progress/commit/9080a95b79cebdcdcf2b946f0602efdc09ca4767) Update app icon by [@hxreborn](https://github.com/hxreborn)


### Fixed

- [581411a](https://github.com/hxreborn/punch-hole-download-progress/commit/581411ad31960b3e4e036344dd66d6789b3567fe) *(build)* Replace deprecated resourceConfigurations with localeFilters by [@hxreborn](https://github.com/hxreborn)

- [4acc60a](https://github.com/hxreborn/punch-hole-download-progress/commit/4acc60ae333956f2b662d8250d080b4364e6f33d) *(ci)* Use git-cliff GitHub template with API integration by [@hxreborn](https://github.com/hxreborn)

- [fa4286f](https://github.com/hxreborn/punch-hole-download-progress/commit/fa4286f16b17c9d316f20488220b42e0c12e2e46) *(ci)* Use git-cliff without GitHub API integration by [@hxreborn](https://github.com/hxreborn)

- [29dc91a](https://github.com/hxreborn/punch-hole-download-progress/commit/29dc91a3ac47b45b5436b727f85aae6f5ad7d4d2) *(ci)* Build both libxposed api and service in workflows by [@hxreborn](https://github.com/hxreborn)


### Changed

- [4fc8759](https://github.com/hxreborn/punch-hole-download-progress/commit/4fc8759e5fc8a9441f1e3236a0caa55a27af304d) *(hook)* Import Method class directly by [@hxreborn](https://github.com/hxreborn)


### Documentation

- [11b867c](https://github.com/hxreborn/punch-hole-download-progress/commit/11b867c4dea42380d8f85ffb131cb3856ee688b2) Add GPLv3 license file by [@hxreborn](https://github.com/hxreborn)



## What's Changed in [v1.0.0-alpha](https://github.com/hxreborn/punch-hole-download-progress/releases/tag/v1.0.0-alpha) (2026-01-18)


### Added

- [f2ecafd](https://github.com/hxreborn/punch-hole-download-progress/commit/f2ecafdf9a806a24e3ac123f4474abed058a8a06) *(ui)* Add option to show filename label by [@hxreborn](https://github.com/hxreborn)

- [6143ca9](https://github.com/hxreborn/punch-hole-download-progress/commit/6143ca9bf395a282e35003ccbbc7bd215a5ea4c1) Add semantic git versioning and CI workflows by [@hxreborn](https://github.com/hxreborn)

  > - versionCode = MAJOR × 10,000 + commit count
  > - versionName from git describe, fallback to 0.0.0-dev
  > - APK naming: phdp-v{version}-{buildType}.apk
  > - build.yml: release workflow on v* tags (pre-release)
  > - android-ci.yml: CI on push/PR to main


### Fixed

- [54a7891](https://github.com/hxreborn/punch-hole-download-progress/commit/54a7891e673f262e4179b455f311d2b0f8731568) *(ci)* Add libxposed build step before app build by [@hxreborn](https://github.com/hxreborn)

- [8117852](https://github.com/hxreborn/punch-hole-download-progress/commit/8117852b5786931b341ce6d78d93ef55572e356e) *(ui)* Disable overscroll stretch on System screen by [@hxreborn](https://github.com/hxreborn)

- [8c07f2e](https://github.com/hxreborn/punch-hole-download-progress/commit/8c07f2ee0df5c0418c7f92826a519d8ba8bbc9fd) *(version)* Include commit hash when no semver tag by [@hxreborn](https://github.com/hxreborn)

- [5101591](https://github.com/hxreborn/punch-hole-download-progress/commit/5101591e33b32d576c2b1d90568afe6287e015b3) Avoid log() shadowing in XposedModule by [@hxreborn](https://github.com/hxreborn)


### Changed

- [99fb1ad](https://github.com/hxreborn/punch-hole-download-progress/commit/99fb1adfcdfa3be27a7c3a5567e1531778022f0a) *(ui)* Use Material You icons for switch thumbs by [@hxreborn](https://github.com/hxreborn)

- [3d4a2e0](https://github.com/hxreborn/punch-hole-download-progress/commit/3d4a2e02b93a65888c527c94ca34defafcad28fe) *(ui)* Introduce RowPosition for shape math by [@hxreborn](https://github.com/hxreborn)

- [5583aec](https://github.com/hxreborn/punch-hole-download-progress/commit/5583aec4fa6b1bc35796dd6796866a9fc2498242) *(ui)* Use grouped-list layout for settings by [@hxreborn](https://github.com/hxreborn)

- [b0369ec](https://github.com/hxreborn/punch-hole-download-progress/commit/b0369ec7ae24ddf5b0eba800cbdd19c171c977b5) *(ui)* Match spacing to Android Settings density by [@hxreborn](https://github.com/hxreborn)

- [e4344eb](https://github.com/hxreborn/punch-hole-download-progress/commit/e4344ebbdfcc52041857c1d57ea1ed245178f851) *(ui)* Add Material 3 tokens and SettingsGroup by [@hxreborn](https://github.com/hxreborn)

- [f5c11d0](https://github.com/hxreborn/punch-hole-download-progress/commit/f5c11d0bd4a989f6690d92908b8d44a91cf54f29) *(version)* Use raw git describe output by [@hxreborn](https://github.com/hxreborn)

- [305a049](https://github.com/hxreborn/punch-hole-download-progress/commit/305a0498d8ce8bf0e941dfcdc64883fb42ddee9d) Rename PunchHoleDownloadProgress* to PunchHoleProgress* by [@hxreborn](https://github.com/hxreborn)

- [0ada881](https://github.com/hxreborn/punch-hole-download-progress/commit/0ada881964050b4cad15027741acda9528430e48) Rename PHPM to PHDP by [@hxreborn](https://github.com/hxreborn)

- [51a43cc](https://github.com/hxreborn/punch-hole-download-progress/commit/51a43cca16ed938d161514c40dae0374154c1cd9) Remove dead code and simplify haptic prefs by [@hxreborn](https://github.com/hxreborn)


### Performance

- [2f30f11](https://github.com/hxreborn/punch-hole-download-progress/commit/2f30f116c83684f45f771382382684537ef0029d) *(release)* Shrink APK 4.2MB to 1.8MB by [@hxreborn](https://github.com/hxreborn)

- [2c59d63](https://github.com/hxreborn/punch-hole-download-progress/commit/2c59d63fc9817bcbecede7aea846035f7827e34b) Cache reflection Method objects by [@hxreborn](https://github.com/hxreborn)


### Documentation

- [a4b82aa](https://github.com/hxreborn/punch-hole-download-progress/commit/a4b82aa2d33a88b65fc5da6ac46c4cc50fc7fcc6) *(readme)* Refresh tagline and feature list by [@hxreborn](https://github.com/hxreborn)


### Init

- [4f849e0](https://github.com/hxreborn/punch-hole-download-progress/commit/4f849e0dc6e93698fc84adddf8708bdcc9c7e316) LSPosed module scaffold with Compose UI by [@hxreborn](https://github.com/hxreborn)


### New Contributors

* @hxreborn made their first contribution


