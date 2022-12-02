#include <fstream>
#include <iostream>
#include <string>
#include <chrono>

using namespace std;

/*
 Opp  int(opp) - 65       Pl      int(Pl) - 88        W/D/L   Part 2 outcome    Pl Part2
  A     0                  X          0               D             L               Z       
  A     0                  Y          1               W             D               X
  A     0                  Z          2               L             W               Y
  B     1                  X          0               L             L               X
  B     1                  Y          1               D             D               Y
  B     1                  Z          2               W             W               Z
  C     2                  X          0               W             L               Y
  C     2                  Y          1               L             D               Z
  C     2                  Z          2               D             W               X
*/

int score(char opponent, char player);
char parse(char opponent, char player);

int main() {

    auto start = chrono::high_resolution_clock::now();
    ifstream input;
    input.open("input");

    int sum = 0;
    int sumP2 = 0;
    char opponent;
    char player;

    while (input >> opponent >> player) {
        sum += score(opponent, player);
        sumP2 += score(opponent, parse(opponent, player));
    }

    input.close();

    auto end = chrono::high_resolution_clock::now();
    chrono::duration<double, std::milli> duration = end - start;

    cout << "Part 1: The player's score was " << sum << endl;
    cout << "Part 2: The player's score was " << sumP2 << endl;
    cout << "Completed in " << duration.count() << " ms" << endl;
}

int score(char opponent, char player) {

    int score = 0;

    int op = (int) opponent - 65;
    int pl = (int) player - 88;

    score += pl + 1;

    int diff = pl - op;
    diff = diff >= 0 ? diff : 3 + diff;
    if (diff == 0) {score += 3;}
    if (diff == 1) {score += 6;}

    return score;
}

char parse(char opponent, char player) {
    
    int op = (int) opponent - 65;   
    int pl = (int) player - 88;     

    int shift = op - 1;             

    pl += shift;            
    pl = pl >= 0 ? pl : 3 + pl;
    pl = pl % 3;

    player = (char) (pl + 88);

    return player;

}

