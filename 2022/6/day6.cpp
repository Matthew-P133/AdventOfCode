#include <iostream>
#include <string>
#include <fstream>
#include <chrono>
#include <set>

using namespace std;


void findMarker(int length, string& line);

int main() {
    auto start = chrono::high_resolution_clock::now();

    ifstream file;
    file.open("input");
    string line;
    file >> line;
    file.close();

    // part 1
    cout << "Part 1: " << endl;
    findMarker(4, line);

    // part 2
    cout << "Part 2: " << endl;
    findMarker(14, line);

    auto end = chrono::high_resolution_clock::now();
    chrono::duration<double, std::milli> duration = end - start;
    cout << "Completed in " << duration.count() << " ms" << endl;

    return 0;
}    


void findMarker(int length, string& line) {
    for (int counter = 0; counter < line.length() - length; counter++) {
        string marker = line.substr(counter, length);
        set<char> markerSet(marker.begin(), marker.end());
        if (markerSet.size() == length) {
             cout << "First marker of length " << length << " is '" << marker << "' and appears after " << counter + length << " characters" << endl;
             return;
        }
    }
}
