#include <iostream>
#include <fstream>
#include <string>
#include <chrono>
#include <sstream>
#include <vector>
#include <algorithm>

using namespace std;

int comparePackets(string a, string b);
string trim(string s);
int findMatchingBracket(string s);
string findNextToken(string& s);
int compare(string a, string b);

int main() {

    auto start = chrono::high_resolution_clock::now();

    ifstream file;
    file.open("input");
    string lineA;
    string lineB;

    // part 1 variables
    int index = 1;
    int sum = 0;

    // part 2 variables
    vector<string> packets;
    string divider1 = "[[2]]";
    string divider2 = "[[6]]";
    packets.push_back(divider1);
    packets.push_back(divider2);

    while (getline(file, lineA)) {
        if (lineA == "") {
            continue;
        }
        getline(file, lineB);

        // check if pairs are equals
        if (comparePackets(lineA, lineB)) { 
            sum += index;
        }

        // add packets to vector for part 2
        packets.push_back(lineA);
        packets.push_back(lineB);
        index++;
    }
    file.close();

    // sort packets and calculate decoder key for part 2
    sort(packets.begin(), packets.end(), compare);
    int divider1Position = find(packets.begin(), packets.end(), divider1) - packets.begin() + 1;
    int divider2Position = find(packets.begin(), packets.end(), divider2) - packets.begin() + 1;

    cout << "Part 1: The sum of indices of pairs in the right order was: " << sum << endl;
    cout << "Part 2: The decoder key is: " << divider1Position * divider2Position <<  endl;
  
    auto end = chrono::high_resolution_clock::now();
    chrono::duration<double, milli> duration = end - start;
    cout << "Completed in " << duration.count() << " ms" << endl;
}

// checks packets for order
// returns
//          1 if a comes first 
//          0 if b comes first
//          -1 if order indistinguishable
int comparePackets(string a, string b) {

    // trim enclosing matching brackets or leading comma
    a = trim(a);
    b = trim(b);

    string aToken;
    string bToken;

    // iterate over tokens and compare them (recursively if they are not simple values)
    while (true) {

        aToken = findNextToken(a);
        bToken = findNextToken(b);

        // list b has run out before a
        if (bToken.length() == 0 && aToken.length() != 0) {
            return 0;
        }
 
        // both have run out at same time
        if (aToken.length() == 0 && bToken.length() == 0) {
            return -1;
        }

        // a has run out before b
        if (aToken.length() == 0) {
            return 1;
        }

        // both items are simple values
        if (!(aToken.at(0) == '[') && aToken.find(',') == string::npos && !(bToken.at(0) == '[') && bToken.find(',') == string::npos) {
            if ((stoi(aToken) < stoi(bToken))) {
                return 1;
            } else if (stoi(aToken) == stoi(bToken)) {
                continue;
            } else {
                return 0;
            }
        } 

        // both lists
        if (aToken.at(0) == '[' && bToken.at(0) == '[') {
            int result = comparePackets(aToken, bToken);
            if (result == 1 || result == 0) {
                return result;
            }
            continue;
        }

        // a is list; b value
        else if (aToken.at(0) == '[') {
            string temp = "[" + bToken + "]";
            int result = comparePackets(aToken, temp);
            if (result == 1 || result == 0) {
                return result;
            }
            continue;
        }

        // a value; b list
        else if (bToken.at(0) == '[') {
            string temp = "[" + aToken + "]";
            int result = comparePackets(temp, bToken);
            if (result == 1 || result == 0) {
                return result;
            }
            continue;
        }
        
    }
    // reached end of items therefore in order
    return 1;
}

string trim(string s) {
    if (s.length() == 0) {
        return s;
    }
    // remove leading comma if present
    if (s.at(0) == ',') {
        s = s.substr(1);
    }
    // remove enclosing brackets (if they match)
    if (s.at(0) == '[' && findMatchingBracket(s) == s.length() - 1) {
        s = s.substr(1, s.length() - 2);
    }
    return s;
}

// return position of bracket matching one prefixing string
int findMatchingBracket(string s) {
    int open = 0;
    for (int i = 0; i < s.length(); i++) {
        if (s.at(i) == '[') {
            open++;
        }
        if (s.at(i) == ']') {
            open--;
            if (open == 0) {
                return i;
            }
        }
    }
    return -1;
}

// returns next token (either item or list)
string findNextToken(string& s) {
    string token;
    int cursor;
    if (s.length() == 0) {
        return "";
    }
    if (s.at(0) == '[') {
        token = s.substr(0, findMatchingBracket(s) + 1);
        s = s.substr(findMatchingBracket(s) + 1);

        if (!(s.length() == 0) && s.at(0) == ',') {
            s = s.substr(1);
        }
    } else {
        cursor = s.find(",", 0);
        if (cursor == string::npos) {
            token = s;
            s = "";
            return token;
        }
        token = s.substr(0, cursor);
        s = s.substr(cursor + 1);
    }
    return token;
}

// wrapper for use of comparePacket in sort of vector 
 int compare(string a, string b) {
    int result = comparePackets(a, b);
    if (result == 0) {
        return 0;
    }
    if (result == 1) {
        return -1;
    } 
    return 0;
}