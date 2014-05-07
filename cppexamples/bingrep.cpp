#include <future>
#include <iostream>
#include <fstream>
#include <vector>
#include <cstring>
#include <exception>
#include <algorithm>

using namespace std;

static const size_t BUFFER_SIZE = 1024 * 1024;

using pattern = vector<char>;

void scanfile(const char* filename, const pattern& pat, promise<int>& p) {
    ifstream f(filename, ios::binary);
    int begin = 0;
    vector<char> buf(BUFFER_SIZE);
    try {
        while (f) {
            f.read(buf.data(), BUFFER_SIZE);
            auto end = buf.begin() + f.gcount();
            auto pos = search(buf.begin(), end, pat.begin(), pat.end());
            if (pos != end) {
                p.set_value(begin + (pos - buf.begin()));
                return;
            }
            if (f) {
                begin += f.gcount() - pat.size();
                f.seekg(-pat.size(), ios_base::cur);
            }
        }
    } catch (exception&) {
        p.set_exception(current_exception());
    }
    p.set_value(-1);
}

int main(int argc, char* argv[]) {
    if (argc < 2) {
        cout << "Usage: bingrep <filename> <hex bytes*>\n";
        return -1;
    }

    pattern pat;
    for (int i = 2; i < argc; ++i) {
        char* byte = argv[i];
        if (strlen(byte) != 2) {
            cout << "Invalid byte: " << byte << "\n";
            return -3;
        }

        pat.push_back(stoul(byte, nullptr, 16));
    }

    promise<int> p;
    thread worker(&scanfile, argv[1], pat, ref(p));

    auto result = p.get_future();
    chrono::milliseconds span(100);
    cout << "Searching: ";
    while (result.wait_for(span) == future_status::timeout) cout << '.' << flush;

    int pos = result.get();

    if (pos == -1)
        cout << "\nPattern not found\n";
    else
        cout << "\nPattern found at " << pos << "\n";

    worker.join();
    return 0;
}
