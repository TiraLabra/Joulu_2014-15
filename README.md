# Persistent Vector

Persistent vector is a built-in data structure of the clojure programming language.
The philosophy of clojure is to never use mutable objects. Mutability in idiomatic clojure
is restricted to reference pointers. To accomplish this goal a language needs an **immutable**
and **efficient** collection type that implements a set of operations similar to those of ArrayList in Java.
Persistent vector does just that. It cannot be mutated. When conj is called on a persistent vector.
it returns a new vector that is equal to the old vector with the new item added to its last index.

## API

`PersistentVector<T>` exposes the following public methods


```java
    public int count ();
```
returns the number of items in the vector

***

```java
    public T peek ();
```
returns the last value in vector

***

```java
    public PersistentVector pop () {}
```
returns a new PersistentVector that is the old vector minus the last value

***

```java
    public PersistentVector conj (T value) {}
```
returns a new PersistentVector with the given value added to its last index

***

```java
    public PersistentVector assoc (int index, T value) {}
```
associates the given index with the given value and returns the new PersistentVector

***

```java
    public T get(int index) {}
```
returns the value associated with the given index

## Implementation

To do immutability efficiently a traditional list data type based on a dynamic array is not sufficient.
To prevent excessive copying of data the old and the newly created vector must share memory.
For this is reason persistent vector should never be used with mutable values.

The solution is a tree like structure where all the values are in the leaf nodes. The interior nodes
do not contain values but merely references to their children.

For simplicity's sake, let's first look at an example where all leaf nodes reside at the same depth.
Below is a visualization of a vector in which each leaf node contains two values. The root node contains the size of the vector.
Each value is equal to the index it is associated with. For example, calling get(2) on this vector would return 2.

![alt text](https://dl.dropboxusercontent.com/u/56014373/persistent%20vector.jpeg "Structure of a persistent vector")

### Finding elements

To find elements in the vector, we use bit partitioning. Another, more formal name for
this data structure is a *persistent bit-partitioned vector trie*. In the example above the vector is 4 levels deep
and has a branching factor of true. Thus we need a total of 4 bits to represent an index in this vector,
one for each level. For example, let's say we want to find the 4th index of this vector. First we take the binary
number of 4 which is 10, the we add padding to make it 4 bits long (0010). Starting from the left we take a left
turn every time we see 0 and a right when we see 1. If you look at the diagram above you'll notice that this takes
us to index 4 of the vector where we find the value 4.

### Adding elements

Now, how do we add an element to this vector? If we were not concerned with mutability,
we could simply add the new value to the second container of the third leaf node from the left. But we can't do that,
because we don't want mutation. Also, we don't want to copy the whole structure, because that's not efficient.
Instead, what we want is to create a new tree with the new value and share as much memory as possible with the old tree.
The image below shows how the new vector shares memory with the old one. Nodes colored blue belong to the original vector,
red ones belong to the new vector, and the ones colored magenta are shared between the two.

![alt text](https://dl.dropboxusercontent.com/u/56014373/adding%20to%20the%20vector.jpeg "adding to a persistent vector")

### Updating elements

As with adding elements, when we want to update a vector we create a new vector
that shares all unchanged data with the old one. Here's a diagram for that.

![alt text](https://dl.dropboxusercontent.com/u/56014373/updating%20vector.jpeg "adding to a persistent vector")

### How to make it efficient

This is all good in terms of persistency, but it's still not very efficient. Let's look at how we can improve efficiency.
First of all lets increase the branching factor. Our current implementation only has two elements.
That means the look up time for any element would be O(log n). If we increase the branching factor we are already
better off as the tree will stay more shallow. The clojure implementation has a branching factor of 32. Hence a worst
case look up time of O(log32 n). Another easy optimization is to keep the depth of the tree to minimum.

The maximum depth for a tree with a branching factor of 32 in this setup is 6. This is derived from the fact that we use
32 bit integers to represent the indeces (5 bits for each level 2^5 = 32 and 32/5 = 6). However, we should make sure
never to use more levels than necessary.

