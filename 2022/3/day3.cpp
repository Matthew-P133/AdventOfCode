#include <fstream>
#include <iostream>
#include <string>
#include <chrono>
using namespace std;

int charToPriority(char item);

int main() {

    auto start = chrono::high_resolution_clock::now();
    ifstream input;
    input.open("input");

    string line;
    int length;
    int priority;
    int threeElfItems[53][3] = {};

    int duplicateItemPrioritySum = 0;
    int badgeItemPrioritySum = 0;
    
    int counter = 0;
    while (input >> line) {

        length = line.length();

        /*
                Part 1
        */

        int items[53] = {};

        // first half of bag
        for (int i = 0; i < length / 2; i++) {
            priority = charToPriority(line.at(i));
            items[priority]++;
        }

        // second half of bag
        for (int i = length / 2; i < length; i++) {
            priority = charToPriority(line.at(i));
            if (items[priority]) {
                duplicateItemPrioritySum += priority;
                break;
            }
        }

        /*
                Part 2
        */

        // start of group of three bags
        if (counter % 3 == 0) {
            for (int i = 0; i < 53; i ++) {
                for (int j = 0; j < 3; j++) {
                    threeElfItems[i][j] = 0;
                }
            }
        }
        for (int i = 0; i < length; i++) {
            priority = charToPriority(line.at(i));
            threeElfItems[priority][counter % 3]++;
        }
        // end of group of three bags
        if (counter % 3 == 2) {
            for (int i = 1; i < 53; i++) {
                if (threeElfItems[i][0] > 0 && threeElfItems[i][1] > 0 && threeElfItems[i][2] > 0) {
                    badgeItemPrioritySum += i;
                    break;
                }
            }
        }
        counter++;
        
    }

    input.close();

    auto end = chrono::high_resolution_clock::now();
    chrono::duration<double, std::milli> duration = end - start;

    cout << "Part 1: The sum of priorities of mis-packed items was " << duplicateItemPrioritySum << endl;
    cout << "Part 2: The sum of the badge item priorities was " << badgeItemPrioritySum << endl;
    cout << "Completed in " << duration.count() << " ms" << endl;
}

 int charToPriority(char item) {
    return item >= 97 ? (int) item - 96 : (int) item - 38;
 }

 

