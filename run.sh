#!/bin/bash

# Check if Maven is installed
if ! [ -x "$(command -v mvn)" ]; then
  echo "Error: Maven is not installed. Please install Maven to use this script." >&2
  exit 1
fi

# Check if Java is installed
if ! [ -x "$(command -v java)" ]; then
  echo "Error: Java is not installed. Please install Java (11 or higher) to use this script." >&2
  exit 1
fi

echo "Running Maven build and application..."

# Clean and compile the project
mvn clean compile -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn \
  -Dorg.slf4j.simpleLogger.defaultLogLevel=warn

# Run tests
mvn test -DskipTests=false

# Run the application
MAIN_CLASS="com.github.moritzgermann.Main"

# Run the application via exec plugin
mvn exec:java -Dexec.mainClass="$MAIN_CLASS" -q
