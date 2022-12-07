#include <iostream>
#include <sstream>
#include <string>
#include <fstream>
#include <regex>
#include <chrono>
#include <vector>
#include "components.h"


using namespace std;



int main() {

    auto start = chrono::high_resolution_clock::now();

    ifstream file;
    file.open("input");

    string line;
    string token;

    Directory* currDir = nullptr;
    Directory* topDir = nullptr;

    // parse filesystem
    while (getline(file, line)) {
        stringstream ln(line);
        ln >> token;
        if (token == "$") {
            ln >> token;
            if (token == "cd") {
                ln >> token;
                if (currDir == nullptr) {
                    currDir =  new Directory(nullptr, token);
                    topDir = &(*currDir);
                }
                else if (token == "..") {
                    currDir = currDir->getParent();
                }
                else if (currDir->contains(token)) {
                    currDir = currDir->contains(token);
                }
                else {
                    Directory* newDir = new Directory(currDir, token);
                    currDir->add(newDir);
                    currDir = newDir;
                }
            }
            if (token == "ls") {

            }
        } else {
            if (token == "dir") {
                ln >> token;
                Directory* newDir = new Directory(currDir, token);
                currDir->add(newDir);
            } else {
                int size = stoi(token);
                ln >> token;
                File* f = new File(size, token);
                currDir->add(f);
            }
        }
    }
    file.close();

    // part one
    int partOne = topDir->sumOfDirsSmallerThan(100000);

    // part two
    int diskUsed = topDir->getSize();
    int diskSize = 70000000;
    int sizeRequired = 30000000;
    int needToFree = sizeRequired - (diskSize - diskUsed);
    int partTwo = topDir->smallestDirAtLeast(needToFree, diskUsed);

    auto end = chrono::high_resolution_clock::now();
    chrono::duration<double, milli> duration = end - start;

    cout << "Part 1: The sum of sizes of directories of size >= 100000 was " << partOne << endl;
    cout << "Part 2: The size of the smallest directory deletion of which would allow update to proceed is " << partTwo << endl;
    cout << "Completed in " << duration.count() << " ms" << endl;

    return 0;
}