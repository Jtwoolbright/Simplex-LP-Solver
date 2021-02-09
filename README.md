# Simplex Linear Program Solver

## Background

A Linear Program is a method of maximizing or minimizing an equation based off a set of constraints that is often used to express optimization problems in the field of Operations Research (See https://en.wikipedia.org/wiki/Linear_programming to learn more). 
As more and more constraints and variables are introduced, Linear Programs can grow substantially in size, and it can be very difficult, if not impossible, to solve these problems by simple methods such as elimination or substitution. Fortunately, a US Army Air Force veteran and mathematical scientist by the name of George Dantzig discovered an easier way of solving these problems which he named the Simplex Algorithm (See https://en.wikipedia.org/wiki/Simplex_algorithm). The Simplex Algorithm is effective at solving a large subset of Linear Programs and has since been built upon to solve many more.

![Screenshot (4)](https://user-images.githubusercontent.com/48270610/107432059-3afde800-6adc-11eb-9ec7-a805df6d30ce.png)

## Description

The Simplex Linear Program Solver uses the original Simplex Algorithm, and it requires the Linear Program to be in standard form prior to user input (See above image). The b<sub>m</sub> is referred to as the right hand side constraints when prompting for user input, and it cannot include numbers that are <= 0. The c<sub>n</sub> is referred to as the the constants of the equation to maximize, and they also cannot be <= 0. The a<sub>mn</sub> is referred to as the constraint variable constants, and the user must type in constants for all variables, including those with zero constants, in the constraints.

![CommandLine](https://user-images.githubusercontent.com/48270610/107435750-6b945080-6ae1-11eb-86a3-b4379599acea.PNG)
  
## Requirements

To run from command line, you must have a Java kit installed and your system's environment variables properly configured.
