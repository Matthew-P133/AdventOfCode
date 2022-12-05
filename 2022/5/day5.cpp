#include <iostream>
#include <string>
#include <fstream>
#include <regex>
#include <chrono>

using namespace std;

void executeInstruction(vector<vector<string>>& stacks, vector<string>& instructions);
void move(vector<vector<string>>& stacks, int quantity, int from, int to);
void executeInstructionP2(vector<vector<string>>& stacks, vector<string>& instructions);
void moveP2(vector<vector<string>>& stacks, int quantity, int from, int to);

void inspectTops(vector<vector<string>>& stacks);

int main() {

    auto start = chrono::high_resolution_clock::now();

    regex re(".(.).(?:\\s|$)");
    regex reNums("([0-9]+)");

    ifstream file;
    file.open("input");

    const int STACKS = 9;
    vector<vector<string>> stacks(STACKS + 1);
    vector<vector<string>> stacksPart2(STACKS + 1);
    int stack;

    string line;

    // Parse the starting piles
    while (getline(file, line)) {

        // end of the first part of input
        if (line.at(1) == '1') {
            getline(file, line);
            break;
        }
    
        vector<string> tokens(sregex_token_iterator(line.begin(), line.end(), re, 1), {});

        stack = 1;
        for (string s : tokens) {
            if (s != " ") {
                stacks.at(stack).insert(stacks.at(stack).begin(), s);
                stacksPart2.at(stack).insert(stacksPart2.at(stack).begin(), s);
            }
            stack++;
        }
    }

    // parse and execute instructions
    while(getline(file, line)) {
        vector<string> instructions(sregex_token_iterator(line.begin(), line.end(), reNums, 1), {});
        executeInstruction(stacks, instructions);  
        executeInstructionP2(stacksPart2, instructions);

        if (file.eof()) {
            break;
        }
    }

    file.close();

    auto end = chrono::high_resolution_clock::now();
    chrono::duration<double, std::milli> duration = end - start;

    cout << "Part 1: Stack tops: ";
    inspectTops(stacks);
    cout << endl;
    cout << "Part 2: Stack tops: ";
    inspectTops(stacksPart2);
    cout << endl;
    cout << "Completed in " << duration.count() << " ms" << endl;
    return 0;
}

void executeInstruction(vector<vector<string>>& stacks, vector<string>& instructions) {
    int quantity = stoi(instructions[0]);
    int from = stoi(instructions[1]);
    int to = stoi(instructions[2]);
    move(stacks, quantity, from, to);
}

void move(vector<vector<string>>& stacks, int quantity, int from, int to) {
    for (int i = 0; i < quantity; i++) {
        string item = stacks[from].back();
        stacks[from].pop_back();
        stacks[to].push_back(item);
    }
}

void executeInstructionP2(vector<vector<string>>& stacks, vector<string>& instructions) {
    int quantity = stoi(instructions[0]);
    int from = stoi(instructions[1]);
    int to = stoi(instructions[2]);
    moveP2(stacks, quantity, from, to);
}

void moveP2(vector<vector<string>>& stacks, int quantity, int from, int to) {
    vector<string> temp(0);

    for (int i = 0; i < quantity; i++) {
        string item = stacks[from].back();
        stacks[from].pop_back();
        temp.insert(temp.begin(), item);
    }
    for (string item : temp) {
        stacks[to].push_back(item);
    }
}

void inspectTops(vector<vector<string>>& stacks) {
    
    for (vector<string> stack : stacks) {
        if (!stack.empty()) {
            cout << stack.back();
        }
    }
}