#!/bin/bash
# Helper script to run the Kotlin Compose App
# Uses the absolute path to gradle found in SDKMAN
echo "Starting application..."
echo "First run will download dependencies (this may take a few minutes)..."
# Try to find Java 21 on Linux/Fedora
if [ -d "/usr/lib/jvm/java-21-openjdk" ]; then
    export JAVA_HOME="/usr/lib/jvm/java-21-openjdk"
    echo "Using Java 21 from: $JAVA_HOME"
elif [ -d "/usr/lib/jvm/java-21-openjdk-amd64" ]; then
    export JAVA_HOME="/usr/lib/jvm/java-21-openjdk-amd64"
    echo "Using Java 21 from: $JAVA_HOME"
fi

echo "Verifying Java version:"
"$JAVA_HOME/bin/java" -version

./gradlew run --console=plain -Dorg.gradle.java.home="$JAVA_HOME"
