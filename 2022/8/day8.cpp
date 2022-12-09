#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <chrono>

using namespace std;


struct Tree {
    int height;
    bool visited;
    bool visible;
    int row;
    int col;
};

Tree grow(int row, int col, int height);
void plant(Tree& tree, vector<Tree>& line);
void expandForest(vector<Tree>& line, vector<vector<Tree>>& forest);
int canSpotTree(Tree& tree, vector<vector<Tree>>& forest);
int lookForTrees(vector<vector<Tree>>& forest);
int scenicScore(vector<vector<Tree>>& forest, Tree& tree);

int unvisitedTrees;


int main() {

    auto start = chrono::high_resolution_clock::now();

    ifstream file;
    file.open("input");
    string inputLine;
    int col;
    int row = 0;
    int height;

    vector<vector<Tree>> forest;

    // peek at first line to get length of each row of trees
    getline(file, inputLine);
    int length = inputLine.length();
    file.seekg(0);

    // forestation!
    while (getline(file, inputLine)) {
        vector<Tree> line(0);
        for (col = 0; col < length; col++) {
            height = stoi(inputLine.substr(col, 1));
            Tree tree = grow(row, col, height);
            plant(tree, line);
        }
        expandForest(line, forest);
        row++;
    }


    // Part 1
    int p1 = lookForTrees(forest);

    // Part 2
    int p2 = 0;
    int temp;
    for (vector<Tree>& treeRow: forest) {
        for (Tree& tree : treeRow) {
            temp = scenicScore(forest, tree);
            if (temp > p2) {
                p2 = temp;
            }
        }
    }

    auto end = chrono::high_resolution_clock::now();
    chrono::duration<double, milli> duration = end - start;

    cout << "Part 1: The number of visible trees was: " << p1 << endl;
    cout << "Part 2: The greatest scenic score was " << p2 << endl;
    cout << "Completed in " << duration.count() << " ms" << endl;

}



Tree grow(int row, int col, int height) {
    Tree t;
    t.height = height;
    t.row = row;
    t.col = col;
    t.visited = false;
    t.visible = false;

    return t;
}

void plant(Tree& tree, vector<Tree>& line) {
    line.push_back(tree);
}

void expandForest(vector<Tree>& line, vector<vector<Tree>>& forest) {
    forest.push_back(line);
}

int lookForTrees(vector<vector<Tree>>& forest) {
    int treesSpotted = 0;
    
    unvisitedTrees = forest.size() * forest.at(0).size();

    while (unvisitedTrees > 0) {
        for (vector<Tree>& treeRow: forest) {
            for (Tree& tree : treeRow) {
                if (!tree.visited) {
                    treesSpotted += canSpotTree(tree, forest);
                }
            }
        }
       
    }
    return treesSpotted;
}

int canSpotTree(Tree& tree, vector<vector<Tree>>& forest) {
    int row = tree.row;
    int col = tree.col;

    int width = forest.at(0).size();
    int depth = forest.size();


    // tree on outside of forest
    if (row == 0 || col == 0 || col == width - 1 || row == depth - 1) {
        tree.visible = true;
        tree.visited = true;
        unvisitedTrees--;
        return 1;
    }

    // check up
    for (int i = row - 1; i >= 0; i--) {
        if (forest.at(i).at(col).height >= tree.height) {
            break;
        }
        if (i == 0) {
            tree.visited = true;
            unvisitedTrees--;
            tree.visible = true;
            return 1;
        }
    }
    // check down
    for (int i = row + 1; i < depth; i++) {
        if (forest.at(i).at(col).height >= tree.height) {
            break;
        }
        if (i == depth - 1) {
            tree.visited = true;
            unvisitedTrees--;
            tree.visible = true;
            return 1;
        }
    }
    // check right
    for (int i = col + 1; i < width; i++) {
        if (forest.at(row).at(i).height >= tree.height) {
            break;
        }
        if (i == width - 1) {
            tree.visited = true;
            unvisitedTrees--;
            tree.visible = true;
            return 1;
        }
    }
    // check left
    for (int i = col - 1; i >= 0; i--) {
        if (forest.at(row).at(i).height >= tree.height) {
            break;
        }
        if (i == 0) {
            tree.visited = true;
            unvisitedTrees--;
            tree.visible = true;
            return 1;
        }
    }
    tree.visited = true;
    unvisitedTrees--;
    tree.visible = false;
    return 0;
}

int scenicScore(vector<vector<Tree>>& forest, Tree& tree) {
    int row = tree.row;
    int col = tree.col;

    int width = forest.at(0).size();
    int depth = forest.size();

    if (row == 0 || col == 0 || col == width - 1 || row == depth - 1) {
        return 0;
    }

    int scenicScore = 1;

    // up
    for (int i = row - 1; i >= 0; i--) {
        if (forest.at(i).at(col).height >= tree.height) {
            scenicScore *= row - i;
            break;
        }
         if (i == 0) {
            scenicScore *= row;
        }

    }
    // down
    for (int i = row + 1; i < depth; i++) {
        if (forest.at(i).at(col).height >= tree.height) {
            scenicScore *= i - row;
            break;
        }
        if (i == depth - 1) {
            scenicScore *= depth- 1 - row;
        }

    }
    // right
    for (int i = col + 1; i < width; i++) {
        if (forest.at(row).at(i).height >= tree.height) {
            scenicScore *= i - col;
            break;
        }
        if (i == width - 1) {
            scenicScore *= width - 1 - col;
        }

    }
    // left
    for (int i = col - 1; i >= 0; i--) {
        if (forest.at(row).at(i).height >= tree.height) {
            scenicScore *= col - i;
            break;
        }
        if (i == 0) {
            scenicScore *= col;
        }

    }
    return scenicScore;
}

    
    
    
   

