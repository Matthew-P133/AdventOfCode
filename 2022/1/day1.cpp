#include <fstream>
#include <iostream>
#include <string>
#include <sstream>
#include <chrono>

using namespace std;

const int NUMBER_ELVES = 3;

void updateTopElves(int elfCalories, int topElves[]);
void insert(int elfCalories, int i, int topElves[]);

int main() {

    auto start = chrono::high_resolution_clock::now();

    ifstream input;
    input.open("input");

    int topElves[NUMBER_ELVES];

    int elfCalories = 0;
    int calories;

    string line;

    while (getline(input, line)) {
        if (line == "") {
            updateTopElves(elfCalories, topElves);
            elfCalories = 0;
        }
        else {
            stringstream(line) >> calories;
            elfCalories += calories;
        } 
    }
    input.close();

    int sumOfTopElves = 0;

    for (int cals: topElves) {
        sumOfTopElves += cals;
    }

    auto end = chrono::high_resolution_clock::now();
    chrono::duration<double, std::milli> duration = end - start;

    cout << "Part 1: The most calories a single elf had was " << topElves[0] << "." << endl;
    cout << "Part 2: The " << NUMBER_ELVES << " elves with most calories had " << sumOfTopElves << " calories between them." << endl;
    cout << "Completed in " << duration.count() << " ms" << endl;
}

void updateTopElves(int elfCalories, int topElves[]) {
    for (int i = 0; i < NUMBER_ELVES; i++) {
        if (elfCalories > topElves[i]) {
            insert(elfCalories, i, topElves);
            break;
        } 
    }
}

void insert(int elfCalories, int i, int topElves[]) {
    for (; i < NUMBER_ELVES; i++) {
        int temp = topElves[i];
        topElves[i] = elfCalories;
        elfCalories = temp;
    }
}