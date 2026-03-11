#!/bin/bash
# install-hooks.sh

chmod +x scripts/pre-commit
chmod +x scripts/pre-push

cp scripts/pre-commit .git/hooks/pre-commit
cp scripts/pre-push .git/hooks/pre-push

echo "✅ Hooks installiert: Pre-Commit (Style) und Pre-Push (Tests/Bugs)"