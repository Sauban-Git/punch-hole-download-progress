#!/usr/bin/env bash
set -euo pipefail

VERSION=${1:-}

if [[ -z "$VERSION" ]]; then
  echo "Usage: ./scripts/release.sh <version>"
  echo "Example: ./scripts/release.sh 1.0.0-alpha5"
  exit 1
fi

TAG="v$VERSION"

# Ensure clean working tree
if [[ -n "$(git status --porcelain)" ]]; then
  echo "Error: working tree not clean"
  exit 1
fi

# Ensure on main
BRANCH=$(git rev-parse --abbrev-ref HEAD)
if [[ "$BRANCH" != "main" ]]; then
  echo "Error: not on main branch (currently on $BRANCH)"
  exit 1
fi

# Pull latest
git pull --ff-only

# Update CHANGELOG.md with the new tag
echo "Updating CHANGELOG.md for $TAG..."
git cliff --config .github/cliff.toml --tag "$TAG" -o CHANGELOG.md

# Commit changelog
git add CHANGELOG.md
git commit -m "docs(changelog): update for $TAG"

# Create and push tag
git tag -a "$TAG" -m "Release $TAG"

# Push commit and tag
git push origin main
git push origin "$TAG"

echo "Released $TAG"
