# Persistent Vector

Persistent vector is a built-in data structure of the clojure programming language.
The philosophy of clojure is to never use mutable objects. Mutability in idiomatic clojure
is restricted to reference pointers. To accomplish this goal a language needs an *immutable*
and *efficient* collection type that implements a set of operations similar to those of ArrayList in Java.

Persistent vector does just that. It cannot be mutated. When conj is called on a persistent vector.
it returns a new vector that is equal to the old vector with the new item added to its last index.
To do this efficiently a traditional dynamic array data structure is not sufficient.
To prevent excessive copying of data the old and the newly created vector share memory.
This is why persistent vector should never be used with mutable values.

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

## implementation


