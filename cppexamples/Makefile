all: fetcher bingrep

fetcher: fetcher.cpp
	g++ fetcher.cpp -o fetcher -std=c++11 -lpthread -lcurl -Wall

bingrep: bingrep.cpp
	g++ bingrep.cpp -o bingrep -std=c++11 -lpthread -Wall

clean:
	rm fetcher bingrep
