#!/bin/bash

# Project Management System - Run Script
# Automatically checks and starts MariaDB if needed

# Check if MariaDB is running, start if not
echo "Checking MariaDB..."
if sudo systemctl is-active --quiet mariadb 2>/dev/null; then
  echo "✓ MariaDB is already running"
else
  echo "Starting MariaDB..."
  sudo systemctl start mariadb

  if sudo systemctl is-active --quiet mariadb 2>/dev/null; then
    echo "✓ MariaDB started successfully"
  else
    echo "✗ Failed to start MariaDB!"
    echo ""
    echo "Please run: sudo systemctl start mariadb"
    exit 1
  fi
fi

echo ""
echo "Compiling..."
javac -d . logic/*.java cli/*.java Main.java

if [ $? -ne 0 ]; then
  echo "✗ Compilation failed!"
  exit 1
fi

echo "✓ Compilation successful"
echo ""
echo "Starting application..."
java -cp .:database/mariadb-java-client-3.1.4.jar Main
