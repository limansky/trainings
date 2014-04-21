.PHONY: scala futures more all clean

all: scala futures more

scala:
	$(MAKE) -C 1_scala_intro

futures:
	$(MAKE) -C 2_futures

more:
	$(MAKE) -C 3_more_scala

clean:
	$(MAKE) -C 1_scala_intro clean
	$(MAKE) -C 2_futures clean
	$(MAKE) -C 3_more_scala clean
