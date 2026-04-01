# DEISI World Meter 🌍

A demographic data processing and analysis system developed in Java. 
This project allows for the loading, validation, and visualisation of global data concerning countries, cities, and population statistics.

This project was developed as a university assignment for the **Data Structures and Algorithms** course, with a primary focus on data optimisation and efficient memory management.

---

## Current Features

The system focuses on loading data from CSV files, ensuring relational integrity between different entities.

* **Data Loading:** Automated reading of multiple CSV files.
* **Relational Validation:**
  * Cities are only loaded if the country code (`alfa2`) exists in the system.
  * Populations are only loaded if the corresponding country ID is found.
* **Error Handling:** Management of missing data (empty fields) and inconsistent numeric formats (e.g., handling decimals in population fields).
* **Automatic Reporting:** Generates a detailed error report indicating valid lines, invalid lines, and the location of the first error for each file.
* **Formatted Interface:** Terminal-based table visualization using `String.format` for better column alignment.

---

## Data Structure

The project organizes information into four main entities:

1.  **Country (Pais):** Name, ID, Alfa2, Alfa3.
2.  **City (Cidade):** Name, Region, Population, Latitude, and Longitude.
3.  **Population (Populacao):** Year, Male/Female Population, and Density.
4.  **Invalid Input (InputInvalido):** Audit log for file reading errors.

---

## How to Use

1.  Ensure the CSV files are located in the `test-files/` folder.
2.  Run the `Main` class.
3.  Use the interactive menu to:
    * `[1]` Load the data files.
    * `[2], [3], [4]` List the loaded entities.
    * `[5]` View the validation/error report.

---

## Authors

* **Joana Correia** - a22405469
* **Guilherme Coelho** - a22405805