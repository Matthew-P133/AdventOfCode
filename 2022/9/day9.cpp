#include <iostream>
#include <fstream>
#include <string>
#include <chrono>
#include <sstream>
#include <set>

using namespace std;

struct Pos {
    int x;
    int y;
    set<string> visitedPositions;
};

void visit(Pos& position, set<string>& visitedPositions);
void step(Pos& head, string direction);
void follow(Pos& head, Pos& tail);

int main() {

    auto start = chrono::high_resolution_clock::now();

    // set up rope
    Pos rope[10];
    Pos* head;
    Pos* tail;
    
    for (int i = 0; i < 10; i++) {
        Pos pos;
        pos.x = 0;
        pos.y = 0;
        rope[i] = pos;
        visit(pos, pos.visitedPositions);
    }

    ifstream file;
    file.open("input");
    string line;
    string direction;
    string stepsStr;
    int steps;

    while (getline(file, line)) {
        stringstream ss(line);
        ss >> direction;
        ss >> stepsStr;
        steps = stoi(stepsStr);

        for (int i = 0; i < steps; i++) {
            // move head
            head = &rope[0];    
            step(*head, direction);

            // each node follows the one in front
            for (int i = 0; i <= 8; i++) {
                head = &rope[i];
                tail = &rope[i+1];
                follow(*head, *tail);                
                visit(*tail, tail->visitedPositions);
            }
        } 
    }

    int p1 = rope[1].visitedPositions.size();
    int p2 = rope[9].visitedPositions.size();

    auto end = chrono::high_resolution_clock::now();
    chrono::duration<double, milli> duration = end - start;

    cout << "Part 1: Second node visited " << p1 << " locations" << endl;
    cout << "Part 2: Tail visited " << p2 << " locations" << endl;
    cout << "Completed in " << duration.count() << " ms" << endl;
}

void visit(Pos& position, set<string>& visitedPositions) {
    string posID = to_string(position.x) + "-" + to_string(position.y);
    visitedPositions.insert(posID);
    return;
}

void step(Pos& head, string direction) {
    if (direction == "L") {
        head.x -= 1;
    } else if (direction == "R") {
        head.x += 1;
    } else if (direction == "U") {
        head.y += 1;
    }else if (direction == "D") {
        head.y -= 1;
    }
}

void follow(Pos& head, Pos& tail) {
    // same column
    if (head.x == tail.x) {
         if (head.y - tail.y > 1) {
            tail.y += 1;
        }
        else if (head.y - tail.y < -1) {
            tail.y -= 1;
        }
        return;
    }

    // same row
    if (head.y == tail.y) {
        if (head.x - tail.x > 1) {
            tail.x += 1;
        }
        else if (head.x - tail.x < -1) {
            tail.x -= 1;
        }
        return;
    }

    // touching diagonally
    if (abs(head.x - tail.x) == 1 && abs(head.y - tail.y) == 1) {
        return;
    }

    // need to move diagonally
    if (!(abs(head.x - tail.x) == 1)) {
        if (head.x - tail.x > 1) {
            tail.x += 1;
        }
        else if (head.x - tail.x < -1) {
            tail.x -= 1;
        }
        if (head.y - tail.y > 0) {
            tail.y += 1;
        } else {
            tail.y -= 1;
        }
        return;
    }

    if (!(abs(head.y - tail.y) == 1)) {
        if (head.y - tail.y > 1) {
            tail.y += 1;
        }
        else if (head.y - tail.y < -1) {
            tail.y -= 1;
        }
        if (head.x - tail.x > 0) {
            tail.x += 1;
        } else {
            tail.x -= 1;
        }
        return;
    }
    return;
}