### Huom!
Lukiessanne tätä tekstiä huomannette kaksi asiaa.

1. Teksti on englanniksi.

   En viitsinyt käyttää aikaa suomennosten keksimiseen/etsimiseen.
   Jos on pakko, niin suomennan.
   
2. Teksti on kirjoitettu tutoriaalin muotoon. Tähän on kaksi syytä.

   Ensiksikin toivon, että se helpottaa ohjaajaa ymmärtämään harjoitustyön aiheen ja motivaation helpommin.
   Toiseksi, kirjoitin sen samalla kun opiskelin itse tietorakennetta, ja huomaan että opin parhaiten,
   kun kirjoitan itselleni ikään kuin *muistiinpanot* tähän muotoon.

# Persistent Data Structures
   PersistentVector and PersistentHashMap are built-in data structures in the clojure programming language.
   The philosophy of clojure is to never use mutable objects. Mutability in idiomatic clojure
   is restricted to reference primitives (pointers). To accomplish this goal a language needs an **immutable**
   and **efficient** collection types that implement a set of operations similar to those of ArrayList and HashMap in Java.

# Persistent Vector
   Persistent vector is a persistent list data structure. It cannot be mutated. When *conj* is called on a persistent vector it returns a new vector that is equal to the old vector with the new item added to its last index. Similarly *pop*
   returns a new vector that is the old one minus the last item.

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
    public PersistentVector<T> pop ();
```
returns a new PersistentVector<T> that is the old vector minus the last value

***

```java
    public PersistentVector<T> conj (T value);
```
returns a new PersistentVector with the given value appended to it

***

```java
    public PersistentVector<T> assoc (int index, T value);
```
returns a new PersistentVector that associates the given index with the given value

***

```java
    public T get(int index);
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

![alt text](https://dl.dropboxusercontent.com/u/56014373/updating%20vector.jpeg "updating a persistent vector")

### How to make it efficient

This is all good in terms of persistency, but it's still not very efficient. Let's look at how we can improve efficiency.
First of all, lets increase the branching factor. Our current implementation only has two elements per node.
That means the look up time for any element would be O(log n). If we increase the branching factor we are already
better off as the tree will stay more shallow. The clojure implementation has a branching factor of 32. Hence a worst case look up time of O(log32 n).

Another easy optimization is to keep the depth of the tree to a minimum. The maximum depth for a tree with a branching
factor of 32 in this setup is 6. This is derived from the fact that we use 32 bit integers to represent the indeces
(5 bits for each level 2^5 = 32 and 32/5 = 6). However, we should make sure never to use more levels than necessary.

# Persistent Hash Map

Persistent hash map is the clojure version of Java's HashMap. It uses hashing to associate keys with values.
Just like PersistentVector it is *persistent* and *immutable*. It is based on the work by Rich Hickey and Phil Bagwell.
Phil Bagwell introduced the *Hash Array Mapped Trie* in his paper *Ideal Hash Trees*. Later, Rich Hickey made a persistent
version of it for clojure.

## API

`PersistentHashMap<K,V>` exposes the following public methods


```java
    public int count ();
```
returns the number of items in the PersistentHashMap

***

```java
    public PersistentHashMap<K,V> assoc (K key, T value);
```
returns a new PersistentHashMap that associates the given index with the given value

***

```java
    public PersistentHashMap<K,V> dissoc (K key);
```
returns a new PersistentHashMap without the given key

***

```java
    public T get(K key);
```
returns the value associated with the given key

## Implementation

Java uses 32 bit integers for hashes. This means we could just use the Persistent Vector implementation with very few modifications for a hash map, and it would work
correctly. However, the problem is it would take too much space. Each leaf would always have a 32 item array even if it only contains one value. For a vector this
is acceptable since it only ever has one non-empty leaf, but for the hash map, we have change the design. We need to make sure it only uses as much space as it needs,
i.e. there are no empty array items. This can be accomplished with one additional integer field in each node, and some clever bit manipulation.

![alt text](https://dl.dropboxusercontent.com/u/56014373/PHM%20-%20New%20Page.jpeg "persistent hash map")

PersistentHashMap is composed of two types of nodes: InternalNode and LeafNode

**InternalNode** can have both LeafNodes and InternalNodes as its children. Just like a node in Persistent Vector, it uses a 5 bit chunk
of the hash code to index them. The child nodes are strored in an array that is only as long as the number of children. To do this it has
an additional field, that stores the position of each child in a 32 bit bitmap. For details, see the InternalNode implementation in PersistentHashMap.java.

**LeafNode** holds the actual value. In case of hash collision, leaf nodes form a persistent linked list that stores all values with the same hash.

