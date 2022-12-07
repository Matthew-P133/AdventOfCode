#ifndef COMPONENTS_H
#define COMPONENTS_H

#include <vector>

using namespace std;

class Component {
    public:
        Component() {}
        //virtual ~Component() = 0;
        virtual int getSize() const = 0;
        virtual string getName() const = 0;
        virtual void print(string prefix) const = 0;
        virtual int sumOfDirsSmallerThan(int maxSize) const = 0;
        virtual int smallestDirAtLeast(int needToFree, int currSmallest) const = 0;
    private:
};

class Directory : public Component {
    public:
        Directory(Directory* parent, string name) {
            this->parent = parent;
            this->name = name;
        }
        // ~Directory() {

        // }
        int getSize() const {
            int size = 0;
            for (Component* c : contents) {
                size += c->getSize();
            }
            return size;
        }
        virtual string getName() const {
            return name;
        }
        void add(Component* c) {
            contents.push_back(c);
        }
        Directory* contains(string nme) {
            if (!contents.empty()) {
                for (Component* c : contents) {
                    if (c->getName() == nme) {
                        return (Directory*) c;
                    } 
                }
            }
            return nullptr;
        }
        Directory* getParent() const {
            return parent;
        }

        void print(string prefix) const {
            cout << prefix << "dir " << name << " size: " << getSize() << endl;
            prefix += "\t";
            for (Component* c : contents) {
                c->print(prefix);
            }
        }
        int sumOfDirsSmallerThan(int maxSize) const {
            int sum = 0;
            int size = getSize();
            if (size <= 100000) {
                sum += size;
            }
            for (Component* c : contents) {
                sum += c->sumOfDirsSmallerThan(maxSize);
            }
            return sum;
        }
        int smallestDirAtLeast(int needToFree, int currSmallest) const {
            int size = getSize();
            if (size >= needToFree && size < currSmallest) {
                currSmallest = size;
            }
            for (Component* c : contents) {
                size = c->smallestDirAtLeast(needToFree, currSmallest);
                if (size >= needToFree && size < currSmallest) {
                    currSmallest = size;
                }
            }
            return currSmallest;
        }
    private:
        vector<Component*> contents;
        Directory* parent;
        string name;
};

class File : public Component {
    public:
        File(int size, string name) {
            this->size = size;
            this->name = name;
        }
        int getSize() const {
            return size;
        }
        virtual string getName() const {
            return name;
        }
        virtual void print(string prefix) const {
            cout << prefix << "file " << name << " size: " << getSize() << endl;
        }
        int sumOfDirsSmallerThan(int maxSize) const {
            return 0;
        }
        int smallestDirAtLeast(int needToFree, int currSmallest) const {
            return 9999999;
        }
    private:
        int size;
        string name;
};

#endif