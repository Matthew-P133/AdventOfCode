#include <fstream>
#include <iostream>
#include <string>
#include <regex>
#include <chrono>

using namespace std;

bool completeOverlap(int aFrom, int aTo, int bFrom, int bTo);
bool partialOverlap(int aFrom, int aTo, int bFrom, int bTo);

int main() {

    auto start = chrono::high_resolution_clock::now();
    ifstream input;
    input.open("input");

    smatch range;
    regex regexp("(\\d+)-(\\d+),(\\d+)-(\\d+)");

    int aFrom;
    int aTo;
    int bFrom;
    int bTo;

    int completeOverlaps = 0;
    int partialOverlaps = 0;

    string line;

    while (input >> line) {

        regex_match(line, range, regexp);

        aFrom = stoi(range[1]);
        aTo = stoi(range[2]);
        bFrom = stoi(range[3]);
        bTo = stoi(range[4]);

        if (completeOverlap(aFrom, aTo, bFrom, bTo)) {
            completeOverlaps++;
            partialOverlaps++;
        } else if (partialOverlap(aFrom, aTo, bFrom, bTo)) {
            partialOverlaps++;
        } 
    }

    input.close();

    auto end = chrono::high_resolution_clock::now();
    chrono::duration<double, std::milli> duration = end - start;

    cout << "Part 1: The number of overlaps was " << completeOverlaps << endl;
    cout << "Part 2: The partial overlaps was " << partialOverlaps << endl;
    cout << "Completed in " << duration.count() << " ms" << endl;
}

bool completeOverlap(int aFrom, int aTo, int bFrom, int bTo) {
    if (aFrom <= bFrom && aTo >= bTo) {
        return true;
    }
    if (bFrom <= aFrom && bTo >= aTo) {
        return true;
    }
    return false;
}

bool partialOverlap(int aFrom, int aTo, int bFrom, int bTo) {
    
    if (aTo >= bTo && aFrom <= bTo) {
        return true;
    }
    if (bTo >= aTo && bFrom <= aTo) {
        return true;
    }
    return false;
}


 

