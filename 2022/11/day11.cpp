#include <iostream>
#include <fstream>
#include <string>
#include <chrono>
#include <sstream>

#include "Monkey.h"

using namespace std;

vector<Monkey*> parse(string filename);

int main() {

    auto start = chrono::high_resolution_clock::now();

    // part 1
    vector<Monkey*> monkeys = parse("input");
    for (int i = 0; i < 20; i++) {
        for (Monkey* monkey : monkeys) {
            monkey->takeTurn();
        }
    }
    cout << "Part 1: Monkey business enacted: " << Monkey::getMonkeyBusiness() << endl;

     // part 2
    vector<Monkey*> monkeys2 = parse("input");
    for (int i = 0; i < 10000; i++) {
        for (Monkey* monkey : monkeys2) {
            monkey->takeTurn2();
        }
    }
    cout << "Part 2: Monkey business enacted: " << Monkey::getMonkeyBusiness() << endl;

    auto end = chrono::high_resolution_clock::now();
    chrono::duration<double, milli> duration = end - start;
    cout << "Completed in " << duration.count() << " ms" << endl;
}

vector<Monkey*> parse(string filename) {

fstream file;

    file.open(filename);
    string line;
    string token;
    int temp;

    while (getline(file, line)) {

        // information required to construct monkey
        int id;
        vector<long long> items;
        string operation;
        string operand;
        int divisor;
        int trueMonkeyID;
        int falseMonkeyID;

        // Monkey #
        stringstream l1(line);
        l1 >> token;
        l1 >> token;
        id = stoi(token);
 
        // starting items...
        getline(file, line);
        stringstream l2(line);
        l2 >> token;
        l2 >> token;

        while(l2 >> token) {
            if (token != ",") {
                int temp = stoi(token);
                items.push_back(temp);
            }
        }

        // operation
        getline(file, line);
        stringstream l3(line);
        l3 >> token;
        l3 >> token;
        l3 >> token;
        l3 >> token;
        l3 >> token;
        operation = token;
        l3 >> token;
        operand = token;

        // test
        getline(file, line);
        stringstream l4(line);
        l4 >> token;
        l4 >> token;
        l4 >> token;
        l4 >> token;
        divisor = stoi(token);

        // true monkey
        getline(file, line);
        stringstream l5(line);
        l5 >> token;
        l5 >> token;
        l5 >> token;
        l5 >> token;
        l5 >> token;
        l5 >> token;
        trueMonkeyID = stoi(token);

        // false monkey
        getline(file, line);
        stringstream l6(line);
        l6 >> token;
        l6 >> token;
        l6 >> token;
        l6 >> token;
        l6 >> token;
        l6 >> token;
        falseMonkeyID = stoi(token);

        // empty line after each monkey
        getline(file, line);

        Monkey::createMonkey(id, operation, operand, divisor, falseMonkeyID, trueMonkeyID, items);
    }
    file.close();
    return Monkey::getMonkeyList();
 }

