# StockTradingSystem (Compose Desktop)

A real-time stock trading simulator built with Kotlin and Compose Multiplatform.

## Prerequisites

To run this project on another machine (e.g., Debian, Windows, macOS), you only need:

1.  **Java JDK 21** (or at least JDK 17).
    - **Fedora/RHEL**: `sudo dnf install java-21-openjdk-devel`
    - **Debian/Ubuntu**: `sudo apt install openjdk-21-jdk`
    - Checks: `java -version`
2.  **Git** (optional, if cloning).

No manual Gradle installation is required. The project includes a Gradle Wrapper (`gradlew`).

## How to Run

1.  **Copy this folder** to your other machine.
2.  Open a terminal in this folder.
3.  Run the app:

    ```bash
    ./run_app.sh
    ```

    _Alternatively:_

    ```bash
    ./gradlew run
    ```

    _(On Windows, use `gradlew.bat run`)_

## Persistence

Data is saved to `~/.stockflow/userdata.txt`.
You can reset data via the **Settings** tab in the app.

## VS Code Setup

1.  Install the **"Kotlin"** and **"Gradle for Java"** extensions.
2.  Open this folder in VS Code.
3.  It should detect the Gradle project automatically.
