#include <iostream>
#include <string>
#include <fstream>
#include <regex>
#include <chrono>

using namespace std;

string generateReString(int length);
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

string generateReString(int length) {
    stringstream re;
    for (int i = 0; i < length; i++) {
        if (i != 0) {
            re << "(?!";
        }
        for (int j = i; j > 0; j--) {
            re << "\\" << j;
            if (j != 1) {
                re << "|";
            }
        }
        if (i != 0) {
            re << ")";
        }
        re << "(.)";
    }
    cout << "Generated regular expression: " << re.str() << endl;
    return re.str();
}

void findMarker(int length, string& line) {
    regex re(generateReString(length));
    smatch matches;

    regex_search(line, matches, re);
    string marker = matches[0];
    int position = matches.position(0) + 14;

    cout << "First marker of length " << length << " is '" << marker << "' and appears after " << position << " characters" << endl;
}

