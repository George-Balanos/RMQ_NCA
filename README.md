# RMQ_NCA
# Efficient Algorithms for Range Minimum Queries (RMQ) and Nearest Common Ancestors (NCA)

This repository provides an implementation of efficient algorithms to solve the Range Minimum Queries (RMQ) and Nearest Common Ancestors (NCA) problems, leveraging the correlation between these two problems.

## Problem Descriptions

### 1. Range Minimum Queries (RMQ)

The RMQ problem involves a given array of integers `A = [a0, a1, ..., an-1]`. The goal is to preprocess the array such that you can efficiently answer queries of the form:

```cpp
int rangeMin(int l, int r)
```

This function returns the minimum element ak in the subarray [al, al+1, ..., ar], where l ≤ k ≤ r.

For example, if A = [1, 32, 11, 3, 2, 81], then rangeMin(0, 3) would return 1 and rangeMin(1, 2) would return 11.

### 2. Nearest Common Ancestors (NCA)
The NCA problem involves a given tree T rooted at node r. The goal is to preprocess the tree to efficiently answer queries of the form:

```cpp
int nca(int u, int v)
```

This function returns the nearest common ancestor x of nodes u and v in the tree T. The nearest common ancestor is the first common node on the paths from u to r and from v to r.

For example, given a tree, nca(h, m) would return a, nca(s, q) would return l, nca(o, s) would return b, and nca(d, f) would return r.

On-line and Off-line Variants
Both problems can be distinguished into two variants: on-line and off-line.

* On-line Variant: Queries are not known in advance and must be answered as they come.
* Off-line Variant: All queries are known in advance, allowing for potentially more efficient preprocessing.
For the on-line variant, you must prepare to efficiently answer any incoming query, which involves preprocessing the data to create a structure that supports fast query responses. The complexity is determined by the preprocessing time f(n) and the query response time g(n). For the off-line variant, the complexity depends on a function f(n, m), where m is the number of queries.

For the on-line variant, you must prepare to efficiently answer any incoming query, which involves preprocessing the data to create a structure that supports fast query responses. The complexity is determined by the preprocessing time f(n) and the query response time g(n). For the off-line variant, the complexity depends on a function f(n, m), where m is the number of queries.

Theoretical Complexity
Efficient algorithms exist for both problems that preprocess the data in linear time O(n) and answer each query in constant time O(1). However, in this exercise, you will implement algorithms with complexities O(n log n) for preprocessing and O(1) for query responses in the on-line variant, and O(n + m α(m, n)) for the off-line variant, where α is the inverse Ackermann function.

There are four algorithms across two Java files, RMQ.java and NCA.java:

1. On-line RMQ: Preprocess the array for efficient range minimum queries.
2. Off-line NCA: Implement Tarjan's algorithm for nearest common ancestors using a union-find structure with path compression.
3. Reduction from Off-line RMQ to Off-line NCA: Transform RMQ to NCA using a Cartesian tree.
4. Reduction from On-line NCA to On-line RMQ: Use an Euler tour technique to reduce NCA to RMQ.

RMQ.java
* initialize(int[] array, int n): Preprocesses the array for RMQ.
* rangeMin(int l, int r): Returns the minimum element in the range [l, r].
* rmq_offline(int[] array, int n, int[][] queries): Handles off-line RMQ queries by transforming them to NCA queries.

NCA.java
* initialize(int[] tree, int n): Preprocesses the tree for on-line NCA.
* nca(int u, int v): Returns the nearest common ancestor of u and v.
* nca_offline(int[] tree, int n, int[][] queries): Handles off-line NCA queries using Tarjan's algorithm.

### How to Run the Code:

javac RMQ.java<br>
java RMQ < <array-file>.txt

javac NCA.java<br>
java NCA < <tree-file>.txt

### Sources: 

https://en.wikipedia.org/wiki/Cartesian_tree#Range_searching_and_lowest_common_ancestors
[1] M. A. Bender, M. F. Colton. “The LCA Problem Revisited”. (2000) Link: 
https://www.ics.uci.edu/~eppstein/261/BenFar-LCA-00.pdf 
[2] R. E. Tarjan, “Applications of path compression on balanced trees” (1979). 
[3]https://en.wikipedia.org/wiki/Tarjan%27s_offline_lowest_common_ancestors_al
gorithm


This version includes a section titled "Potential for Improvement" where it mentions that due to the project's age, there may be areas that can be optimized or refactored.
