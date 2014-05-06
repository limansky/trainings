#include <future>
#include <iostream>
#include <fstream>
#include <vector>
#include <cstring>

using namespace std;

using pattern = vector<char>;

void scanfile(ifstream& s, const pattern& pat, promise<int>& p) {
    p.set_value(-1);
}

int main(int argc, char* argv[]) {
    if (argc < 2) {
        cout << "Usage: bingrep <filename> <hex bytes*>\n";
        return -1;
    }
    
    ifstream f(argv[0], ios::binary);
    if (!f) {
        cout << "Invalid file\n";
        return -2;
    }

    pattern pat;
    for (int i = 1; i < argc; ++i) {
        char* byte = argv[i];
        if (strlen(byte) != 2) {
            cout << "Invalid byte: " << byte << "\n";
            return -3;
        }

        pat.push_back(stoul(string(byte), nullptr, 16));
    }

    promise<int> p;
    thread worker(&scanfile, ref(f), pat, ref(p));

    auto result = p.get_future();
    chrono::milliseconds span(500);
    while (result.wait_for(span) == future_status::timeout) cout << '.';

    int pos = result.get();

    if (pos == -1)
        cout << "\nPattern not found\n";
    else
        cout << "\nPattern found at " << pos << "\n";

    return 0;
}
