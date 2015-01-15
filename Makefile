.PHONY: scala futures more akka all clean

all: scala futures more akka reflection testing

scala:
	$(MAKE) -C 1_scala_intro

futures:
	$(MAKE) -C 2_futures

more:
	$(MAKE) -C 3_more_scala

akka:
	$(MAKE) -C 4_akka

reflection:
	$(MAKE) -C 5_reflection

testing:
	$(MAKE) -C 6_testing

clean:
	$(MAKE) -C 1_scala_intro clean
	$(MAKE) -C 2_futures clean
	$(MAKE) -C 3_more_scala clean
	$(MAKE) -C 4_akka clean
	$(MAKE) -C 5_reflection clean
	$(MAKE) -C 6_testing clean
