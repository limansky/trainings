.PHONY: scala futures more akka all clean

all: scala futures more akka

scala:
	$(MAKE) -C 1_scala_intro

futures:
	$(MAKE) -C 2_futures

more:
	$(MAKE) -C 3_more_scala

akka:
	$(MAKE) -C 4_akka

clean:
	$(MAKE) -C 1_scala_intro clean
	$(MAKE) -C 2_futures clean
	$(MAKE) -C 3_more_scala clean
	$(MAKE) -C 4_akka clean
