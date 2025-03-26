# Parallel Merge Sort with Fork-Join Pool

This project implements **Merge Sort** using Java's **Fork-Join Pool** to distribute the sorting task across **all available processors** on a system. By leveraging parallelism, a significant improvement in sorting speed can be achieved for large datasets when compared to sequential Merge Sort. Additionally, the runtimes of parallel and sequential sorting can be compared.

---

## Features

- **Parallel Merge Sort**: Different tasks for sorting of subarrays are distributed across multiple processors for parallel execution, significantly reducing runtime for large input files.
- **Sequential Merge Sort**: The same sorting algorithm runs on a single thread to highlight the efficiency of the parallel approach.
- **Input file specification**: The user can:
  - Provide a path to an existing file to be sorted
  - Or generate a new input file, specifying the number of **random numbers** to generate.
- **Output directory**: The sorted file is saved under `resources/output/` in the project folder.

---

## Requirements

- **Java version**: This project requires Java **11 or higher**.
- **Maven**: Apache Maven must be installed to build and run the project.
- **Bash**: A Bash-compatible terminal is needed to execute the program.

---

## Installation & Execution

### Preparation 

1. Ensure that **Java 11** or a newer version, **Maven**, and **Bash** are installed on your system.
2. Clone or download the repository.

```bash
git clone <repository_url>
cd <project_directory>
```

### Run the script

The project is executed using the Bash script `run.sh`, which ensures the Java program runs with the necessary parameters.

```bash
bash run.sh
```

The script runs the Java program and provides options for selecting the input file.

---

## Input Files

There are two ways to specify input files:

1. **Use an existing file**: A path to an input text file can be provided. The file must adhere to the following format:
   - **One integer per line**, e.g.:
     ```
     42
     19
     105
     -76
     ```

2. **Generate a new file**: Alternatively, the user can generate an input file. The number of **random integers** to generate is specified during execution. The generated file will automatically be saved under `resources/generated_input.txt` in the project folder.

---

## Output

After sorting, the sorted file is saved in the `resources/output/` directory.

- The file will have the same name as the input file, with the addition of: `_sorted.txt`

Example: If the input file is named `input_numbers.txt`, the results will be saved under the following paths: `resources/output/input_numbers_sorted.txt`

---

## Example Workflow

After running `bash run.sh`, the user will be prompted to choose one of the following actions:

1. Provide a **path to an existing file** to be sorted.
2. Trigger the **generation of an input file** with random numbers.

The program will then perform both parallel and sequential Merge Sort, compare their runtimes, and save the results. The runtimes will also be displayed in the terminal.

---

## Performance Note

- **Parallel Merge Sort** demonstrates its performance advantage primarily with **large datasets**, as the overhead of the Fork-Join Pool can slow down parallel execution for smaller files. For meaningful results, it's recommended to test with large input files (e.g., with several million numbers).
