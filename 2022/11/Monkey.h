#ifndef MONKEY_H
#define MONKEY_H

#include <vector>

using namespace std;

class Monkey {
    public:

        static void createMonkey(int id, string operation, string operand, int divisor, int falseMonkeyID, int trueMonkeyID, vector<long long> items) {
            monkeys.at(id) = new Monkey(id, operation, operand, divisor, falseMonkeyID, trueMonkeyID, items);
        }

        Monkey(int id, string operation, string operand, int divisor, int falseMonkeyID, int trueMonkeyID, vector<long long> items) {
            this->id = id;
            this->operation = operation;
            this->operand = operand;
            this->divisor = divisor;
            this->falseMonkeyID = falseMonkeyID;
            this->trueMonkeyID = trueMonkeyID;
            this->items = items;
            this->numInspections = 0;

            monkeys.at(id) = this;
        }

        static Monkey* getMonkeyByID(int id) {
            return monkeys.at(id);
        }

        void takeTurn() {
            if (items.empty()) {
                return;
            }
            long long updatedItem;
            for (long long item : items) {
                updatedItem = inspect(item);
                if (test(updatedItem)) {
                    throwItem(trueMonkeyID, updatedItem);
                } else {
                    throwItem(falseMonkeyID, updatedItem);
                }
            }
            items.clear();
        }

        void takeTurn2() {
            if (items.empty()) {
                return;
            }
            long long updatedItem;
            for (long long item : items) {
                updatedItem = inspect2(item);
                if (test(updatedItem)) {
                    throwItem(trueMonkeyID, updatedItem);
                } else {
                    throwItem(falseMonkeyID, updatedItem);
                }
            }
            items.clear();
        }

        static vector<Monkey*> getMonkeyList() {
            return monkeys;
        }

        static long long getMonkeyBusiness() {
            int maxInspections = 0;
            int secondMaxInspections = 0;
            for (Monkey* monkey : monkeys) {
                if (monkey->numInspections > maxInspections) {
                    secondMaxInspections = maxInspections;
                    maxInspections = monkey->numInspections;
                } else if (monkey->numInspections > secondMaxInspections) {
                    secondMaxInspections = monkey->numInspections;
                }
            }
            return (long) (long) 1 * maxInspections * secondMaxInspections;
        }

        void print() {
            cout << "Monkey " << id << " looked at " << numInspections << " items. and has: " << endl;
                  for (long long item : items) {
            cout << item << " ";
        }
        cout << endl << endl;
        }

        static vector<Monkey*> monkeys;


    private:
        int id;
        int numInspections;
        string operation;
        string operand;
        int divisor;
        int falseMonkeyID;
        int trueMonkeyID;
        vector<long long> items;
        

        void throwItem(int receiverID, long long item) {
            Monkey* receivingMonkey = getMonkeyByID(receiverID);
            receivingMonkey->catchItem(item);
        }

        void catchItem(long long item) {
            items.push_back(item);
        }

        long long inspect(long long item) {
            numInspections++;

            long long copy = item;

            long long op;
            if (operand == "old") {
                op = item;
            } else {
                op = stoi(operand);
            }
            if (operation == "*") {
                item *= op;
                item /= 3;
     
            }
            else if (operation == "+") {
                item += op;
                item = item / 3;
            }
            return item;
        }

        long long inspect2(long long item) {
            numInspections++;

            long long copy = item;

            long long op;
            if (operand == "old") {
                op = item;
            } else {
                op = stoi(operand);
            }
            if (operation == "*") {
                item *= op;
            }
            else if (operation == "+") {
                item += op;
            }
            return item % (7*17*11*13*19*2*5*3);
        }

        bool test(long long item) {
            return (item % divisor == 0);
        }
};

vector<Monkey*> Monkey::monkeys = vector<Monkey*>(8);

#endif

