#include <iostream>
#include <fstream>
#include <string>
#include <chrono>

using namespace std;

// control width and depth of cave
const int ROWS = 200;
const int COLS = 1500;

enum substance {AIR, ROCK, SAND};

struct coord {
    int row;
    int column;
};

void parse(string line, int (&cave)[ROWS][COLS]);
void createRockFormation(coord c1, coord c2, int (&cave)[ROWS][COLS]);
void printCave(int (&cave)[ROWS][COLS]);
coord extractCoord(string &line);
bool sandGetsStuck(int (&cave)[ROWS][COLS]);
coord moveSand(coord c, int (&cave)[ROWS][COLS]);

int main() {

    auto start = chrono::high_resolution_clock::now();

    int cave[ROWS][COLS] = {};
    int sand = 0;
    int deepestRock = 0;
    int sandPart2 = 1;

    ifstream file;
    file.open("input");
    string line;

    // parse file and set up cave system
    while (getline(file, line)) {
        parse(line, cave);
    }
    file.close();

    // part 1: see how much sand gets stuck in the cave
    while(sandGetsStuck(cave)) {
        sand += 1;
    }

    // remove part 1 sand from cave and find deepest rock formation
    for (int row = 0; row < ROWS; row++) {
        for (int col = 0; col < COLS; col++) {
            if (cave[row][col] == SAND) {
                cave[row][col] = AIR;
            }
            if (cave[row][col] == ROCK && row > deepestRock) {
                deepestRock = row;
            }
        }
    }

    // add floor to the cave
    for (int col = 0; col < COLS; col++) {
        cave[deepestRock + 2][col] = ROCK;
    }

    // part 2: see how much sand gets stuck in the cave
    while(sandGetsStuck(cave)) {
        sandPart2 += 1;
    }
    
   cout << "Part 1: " << sand << " bits of sand got stuck in the cave." << endl;
   cout << "Part 2: " << sandPart2 << " bits of sand got stuck before the sand source got blocked." << endl;
  
    auto end = chrono::high_resolution_clock::now();
    chrono::duration<double, milli> duration = end - start;
    cout << "Completed in " << duration.count() << " ms" << endl;
}

// parse a line of input file, creating the specified rock formations
void parse(string line, int (&cave)[ROWS][COLS]) {
    coord c1 = extractCoord(line);
    coord c2;
    do {
        c2 = extractCoord(line);
        createRockFormation(c1, c2, cave);
        c1 = c2;
    } while(line.length() != 0);

}

// create a rock formation between two points in the cave
void createRockFormation(coord c1, coord c2, int (&cave)[ROWS][COLS]) {
    // horizontal
    if (c1.row == c2.row) {
        int row = c1.row;
        coord left = c1.column < c2.column ? c1 : c2;
        coord right = c1.column > c2.column ? c1 : c2;
        for (int i = left.column; i <= right.column; i++) {
            cave[row][i] = ROCK;
        }
    }
    // vertical
    if (c1.column == c2.column) {
        int col = c1.column;
        coord up = c1.row < c2.row ? c1 : c2;
        coord down = c1.row < c2.row ? c2 : c1;
        for (int i = up.row; i <= down.row; i++) {
            cave[i][col] = ROCK;
        }
    }
}

// extract next coordinate from line of input
coord extractCoord(string &line) {
    int nextArrowToken = line.find('-');
    string token;
    int row;
    int col;
    if (nextArrowToken != string::npos) {
        int endNextToken = nextArrowToken - 1;
        token = line.substr(0, endNextToken);
        line = line.substr(nextArrowToken + 3);
    } else {
        token = line;
        line = "";
    }
    col = stoi(token);
    token = token.substr(token.find(',')+1);
    row = stoi(token);
    coord c;
    c.row = row;
    c.column = col;
    return c;
}

// displays relevant part of cave on console
void printCave(int (&cave)[ROWS][COLS]) {
    for (int row = 0; row < ROWS; row++) {
        for (int col = 420; col < COLS; col++) {
            int point = cave[row][col];
            if (point == 0) {
                cout << ".";
            } else if (point == 1) {
                cout << "#";
            } else {
                cout << "o";
            }
        }
        cout << endl;
    }
}

// drop a piece of sand into cave and see if it gets stuck
bool sandGetsStuck(int (&cave)[ROWS][COLS]) {
    coord prev;
    coord curr;

    prev.column = -1;
    prev.row = -1;

    curr.column = 500;
    curr.row = 0;

    // while sand hasn't reached bottom of cave (before it becomes abyss)
    while (curr.row < ROWS - 1) {
        prev = curr;
        // try to let sand move
        curr = moveSand(curr, cave);
        // sand is blocking sand source
        if (curr.row == 0 && curr.column == 500) {
            return false;
        }
        // sand hasn't moved (i.e. is stuck)
        if (curr.row == prev.row && curr.column == prev.column) {
            return true;
        }
    }
    return false;
}

// let a piece of sand flow one position, updating cave and returning new poisiton of sand
coord moveSand(coord c, int (&cave)[ROWS][COLS]) {
    coord newCoord = c;
    // try down
    if (cave[c.row + 1][c.column] == AIR) {
        cave[c.row + 1][c.column] = SAND;
        cave[c.row][c.column] = AIR;
        newCoord.row = c.row + 1;
        newCoord.column = c.column;
    } else {
        // try position to left and down
        if (cave[c.row + 1][c.column-1] == AIR && ((c.column - 1) >= 0)) {
            cave[c.row + 1][c.column-1] = SAND;
            cave[c.row][c.column] = AIR;
            newCoord.row = c.row + 1;
            newCoord.column = c.column - 1;
        } // try right and down
        else if (cave[c.row + 1][c.column+1] == AIR && ((c.column + 1) < COLS)) {
            cave[c.row + 1][c.column+1] = SAND;
            cave[c.row][c.column] = AIR;
            newCoord.row = c.row + 1;
            newCoord.column = c.column + 1;
        }
    }
    // sand is going to fall into abyss
    if (newCoord.row == ROWS - 1) {
        cave[newCoord.row][newCoord.column] = AIR;
        cave[c.row][c.column] = AIR;
    }
    return newCoord;
}