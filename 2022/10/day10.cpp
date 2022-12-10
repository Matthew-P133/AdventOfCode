#include <iostream>
#include <fstream>
#include <string>
#include <chrono>
#include <sstream>

using namespace std;

void printScreen(string screen);
void processCycle(int& p1, stringstream& p2, int& regsiterVal, int cycles);

int main() {

    auto start = chrono::high_resolution_clock::now();

    ifstream file;
    file.open("input");

    string line;
    string instruction;
    string token;
    int argument;

    int cycles = 0;
    int registerVal = 1;
    int p1 = 0;
    stringstream p2;

    while (getline(file, line)) {
        stringstream ss(line);
        ss >> instruction;

        if (instruction == "noop") {
            // increment cycle, keep track of p1 answer, and draw pixel
            cycles++;
            processCycle(p1, p2, registerVal, cycles);
        }
        if (instruction == "addx") {
            // increment cycle, keep track of p1 answer, and draw pixell
            cycles++;
            processCycle(p1, p2, registerVal, cycles);

            // increment cycle, keep track of p1 answer, and draw pixel
            cycles++;
            processCycle(p1, p2, registerVal, cycles);

            // update register
            ss >> token;
            argument = stoi(token);
            registerVal += argument;  
        }
    }
    file.close();

    cout << "Part 1: Sum of signal strengths: " << p1 << endl;
    cout << "Part 2: Fixed the CRT!" << endl;
    printScreen(p2.str());

    auto end = chrono::high_resolution_clock::now();
    chrono::duration<double, milli> duration = end - start;
    cout << "Completed in " << duration.count() << " ms" << endl;
}

void printScreen(string screen) {
    for (int i = 0; i < screen.length(); i++) {
        if (i % 40 == 0) {
            cout << endl;
        }
        cout << screen.at(i);
    }
    cout << endl;
}

void processCycle(int& p1, stringstream& p2, int& registerVal, int cycles) {
    if ((cycles - 20) % 40 == 0) {
        p1 += cycles * registerVal;
    }
    if (abs((cycles% 40 -1)-registerVal) <= 1 && (cycles % 40 - 1) != -1) {
        p2 << "#";
    } else {
        p2 << ".";
    }
}