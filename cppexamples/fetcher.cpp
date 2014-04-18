#include <future>
#include <iostream>
#include <vector>
#include <curl/curl.h>

using namespace std;

using CurlWriter = size_t(*)(char*, size_t, size_t, string*);

string getPage(const char* url) {
    CURL* curl = curl_easy_init();
    string result;
    if (curl) {
        curl_easy_setopt(curl, CURLOPT_URL, url);
        curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, 1);
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, static_cast<CurlWriter>([](char* buf, size_t s, size_t n, string* r) {
            r->append(buf);
            return s * n;
        }));
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, &result);
        curl_easy_perform(curl);
        curl_easy_cleanup(curl);
    }
    return result;
}

int main(int argc, char* argv[]) {
    
    vector<pair<const char*, future<string>>> results;

    for (int i = 0; i < argc; ++i)
        results.push_back(make_pair(argv[i], async(getPage, argv[i])));

    for (auto &r : results)
        cout << r.first << ": " << r.second.get().substr(0, 200) << "\n";

    return 0;
}
