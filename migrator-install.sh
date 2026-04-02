#!/bin/sh
set -e

REPO="enonic/xp8migrator"
ARCHIVE="migrator-$(uname -s | tr '[:upper:]' '[:lower:]')-$(uname -m).tar.gz"

URL="https://github.com/${REPO}/releases/latest/download/${ARCHIVE}"
echo "Downloading ${ARCHIVE}..."
curl -fsSL "$URL" | tar -xz
chmod +x migrator
echo "Installed ./migrator"
