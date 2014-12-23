/**
 * \file list.hpp
 * Dynamic list.
 */

#ifndef LIST_HPP
#define LIST_HPP

#include <vector>

/**
 * Dynamic list.
 */
template <class T> class List {
    public:
        /**
         * Constructor for list.
         */
        List();
        /**
         * Add a member to this list.
         * O(1).
         * @param t Member to add.
         */
        void add(const T &t);
        /**
         * Get a member from this list.
         * O(1).
         * @param i Index of member.
         */
        T get(int i);
        /**
         * Remove a member from this list.
         * O(n).
         * @param i Index of member.
         */
        T remove(int i);
        /**
         * Get size of this list.
         */
        int size();
        /**
         * Destructor for list.
         */
        ~List();
    public:
        std::vector<T> *arr;
};

#include <vector>

template <class T> List<T>::List() {
    this->arr = new std::vector<T>();
}

template <class T> void List<T>::add(const T &t) {
    this->arr->push_back(t);
}

template <class T> T List<T>::get(int i) {
    return this->arr->at(i);
}

template <class T> T List<T>::remove(int i) {
    T ret = this->arr->at(i);
    this->arr->erase(this->arr->begin() + i);
    return ret;
}

template <class T> int List<T>::size() {
    return this->arr->size();
}

template <class T> List<T>::~List() {
    delete this->arr;
}

#endif
