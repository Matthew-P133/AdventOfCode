#include <iostream>
#include <fstream>
#include <string>
#include <chrono>
#include <sstream>
#include <queue>

using namespace std;

const int LINES = 41;
const int LENGTH = 114;

struct Coord {
    char height;
    int row;
    int col;
    bool visited;
    int distance;
};

class Compare {
    public:
        bool operator() (Coord* c, Coord* other) {
            return (c->distance >= other->distance);
        }
};

Coord* findStart(Coord* heightmap[LINES][LENGTH]);
Coord* findEnd(Coord* heightmap[LINES][LENGTH]);
int ascend(Coord* heightmap[LINES][LENGTH], Coord* startPos);

int main() {

    auto start = chrono::high_resolution_clock::now();

    ifstream file;
    file.open("input");
    string line;
    
    // parse heightmap into array
    Coord* heightmap[LINES][LENGTH];
    int row = 0;
    for (; row < LINES; row++) {
        getline(file, line);
        for (int col = 0; col < LENGTH; col++) {
            Coord* c = new Coord;
            c->height = line.at(col);
            c->row = row;
            c->col = col;
            c->visited = false;
            c->distance = -1;
            heightmap[row][col] = c;
        }
    }
    file.close();

    //Part 1: find shortest path to end w/ Djikstra's algorithm
    Coord* startPos = findStart(heightmap);
    int p1 = ascend(heightmap, startPos);

    //Part 2: find shortest path to end from an 'a'
    int p2 = 9999999;
    int tempDistance;
    for (int row = 0; row < LINES; row++) {
        for (int col = 0; col < LENGTH; col++) {
            if (heightmap[row][col]->height == 'a') {
                tempDistance = ascend(heightmap, heightmap[row][col]);
                p2 = tempDistance < p2 && tempDistance != -1 ? tempDistance : p2;
            }
        }
    }

    cout << "Part 1: The shortest path was: " << p1 << endl;
    cout << "Part 2: The shortest path was: " << p2 <<  endl;
  
    auto end = chrono::high_resolution_clock::now();
    chrono::duration<double, milli> duration = end - start;
    cout << "Completed in " << duration.count() << " ms" << endl;
}


Coord* findStart(Coord* heightmap[LINES][LENGTH]) {
    for (int r = 0; r < LINES; r++) {
        for (int c = 0; c < LENGTH; c++) {
            if (heightmap[r][c]->height == 'S') {
                return heightmap[r][c];
            }
        }
    }
    return nullptr;
}

Coord* findEnd(Coord* heightmap[LINES][LENGTH]) {
    for (int r = 0; r < LINES; r++) {
        for (int c = 0; c < LENGTH; c++) {
            if (heightmap[r][c]->height == 'E') {
                return heightmap[r][c];
            }
        }
    }
    return nullptr;
}

int ascend(Coord* heightmap[LINES][LENGTH], Coord* startPos) {
    startPos->distance = 0;
    startPos->height = 'a';
    priority_queue<Coord*, vector<Coord*>, Compare> q = {};
    q.push(startPos);

    int dist;
    Coord* curr;
    Coord* next;

    // Djikstra's algorithm (as adapted for elvish mountain climbing rules)
    while (!q.empty()) {
        curr = q.top();
        q.pop();
        if (curr->visited) {
           continue;
        }
        heightmap[curr->row][curr->col]->visited = true;
        curr->visited = true;

        // loop over up, down, left, right
        for (int row = curr->row-1; row <= curr->row+1; row++) {
            for (int col = curr->col-1; col <= curr->col+1; col++) {  
                if (!(col < 0 || col > LENGTH - 1) && !(row < 0 || row > LINES - 1) && !(row == curr->row && col == curr->col) && !(abs(row - curr->row) == 1 && abs(col - curr->col) == 1)) {
                    next = heightmap[row][col];
                    // (provided we can step to it) have we found a new shortest route to next?
                    if (heightmap[curr->row][curr->col]->distance + 1 < heightmap[row][col]->distance || heightmap[row][col]->distance == -1) {
                        dist = heightmap[curr->row][curr->col]->distance + 1;
                        // can we actually step to next?
                        if (curr->height - next->height >= -1 || next->height == 'E') {
                            if (next->height == 'E' && !(curr->height == 'z' || curr->height == 'y')) {
                                continue;
                            }
                            heightmap[row][col]->distance = dist;
                            q.push(heightmap[row][col]);
                        }
                    }
                }
            }
        }
    }
    int steps = findEnd(heightmap)->distance;    

    // clean up grid ready for further calls
    for (int i = 0; i < LINES; i++) {
        for (int j = 0; j < LENGTH; j++) {
            heightmap[i][j]->distance = -1;
            heightmap[i][j]->visited = false;
        }
     }

    return steps;
}

