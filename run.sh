#!/bin/bash

# Check if Java is installed
if ! [ -x "$(command -v java)" ]; then
  echo "Error: Java is not installed. Please install Java (11 or higher) to use this script." >&2
  exit 1
fi

if ! [ -x "$(command -v javac)" ]; then
  echo "Error: javac (Java Compiler) is not installed. Please install a Java Development Kit (JDK)." >&2
  exit 1
fi

# Function to extract major version from java -version
get_java_major_version() {
  local cmd=$1
  local version_output=$("$cmd" -version 2>&1)

  if [[ "$cmd" == *"javac" ]]; then
    echo "$version_output" | awk '{ split($2, v, "."); print v[1] }'
  else
    local version_line=$(echo "$version_output" | grep -i 'version')
    echo "$version_line" | awk -F[\".] '{
      if ($2 == "1") print $3;
      else print $2
    }'
  fi
}

# Check Java version
JAVA_VERSION=$(get_java_major_version java)
JAVAC_VERSION=$(get_java_major_version javac)
REQUIRED_VERSION=11

# Check for empty values
if [ -z "$JAVA_VERSION" ] || [ -z "$JAVAC_VERSION" ]; then
  echo "Error: Could not determine Java or Javac version." >&2
  echo "Please make sure Java and Javac are correctly installed and accessible." >&2
  exit 1
fi

# Print used versions
echo "Detected Java version: $JAVA_VERSION"
echo "Detected Javac version: $JAVAC_VERSION"

# Check if both are >= REQUIRED_VERSION
if [ "$JAVA_VERSION" -lt "$REQUIRED_VERSION" ]; then
  echo "Error: Java version $JAVA_VERSION is too old. Please use Java $REQUIRED_VERSION or higher." >&2
  exit 1
fi

if [ "$JAVAC_VERSION" -lt "$REQUIRED_VERSION" ]; then
  echo "Error: Javac version $JAVAC_VERSION is too old. Please use JDK $REQUIRED_VERSION or higher." >&2
  exit 1
fi


# Directories
SRC_DIR="src"
OUT_DIR="out"

# Check if source directory exists
if [ ! -d "$SRC_DIR" ]; then
  echo "Error: Source directory '$SRC_DIR' does not exist." >&2
  exit 1
fi

# Create output directory if it doesn't exist
if [ ! -d "$OUT_DIR" ]; then
  mkdir "$OUT_DIR"
fi

# Compile all Java source files
echo "Compiling source files..."
javac -d "$OUT_DIR" $(find "$SRC_DIR" -name "*.java")
if [ $? -ne 0 ]; then
  echo "Error: Compilation failed." >&2
  exit 1
fi

# Find the file containing the main method
MAIN_CLASS_PATH=$(grep -rl "public static void main" "$SRC_DIR" | head -n 1)
if [ -z "$MAIN_CLASS_PATH" ]; then
  echo "Error: No class with a main method found in the source directory." >&2
  exit 1
fi

# Extract class name
MAIN_CLASS_NAME=$(basename "$MAIN_CLASS_PATH" .java)

# Extract package from file
PACKAGE_LINE=$(grep -m 1 "^package " "$MAIN_CLASS_PATH")
if [ -n "$PACKAGE_LINE" ]; then
  PACKAGE=$(echo "$PACKAGE_LINE" | sed 's/package //' | sed 's/;//')
  MAIN_CLASS="$PACKAGE.$MAIN_CLASS_NAME"
else
  MAIN_CLASS="$MAIN_CLASS_NAME"
fi

# Run the program
echo "Running the application..."
java -cp "$OUT_DIR" "$MAIN_CLASS"