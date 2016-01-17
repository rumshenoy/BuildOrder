# BuildOrder

This is one of the interesting problems on Trees and Graphs from the book 'Cracking the Coding Interview'.

###Problem Statement:
You are given a list of projects and a list of dependencies (which is a list of pairs of projects, where the first project is dependent on the second project). All of a projects dependencies must be built before the project is. Find a build order that will allow the projects to be built. If there is no valid build order, return an error. 

EXAMPLE

Input:
projects: a, b, c, d, e, f
dependecies: (d, a), (b, f), (d, b), (a, f), (c, d)

Output: f, e, a, b, d, c

Solutions:
- Use topoligical sort
- use DFS

