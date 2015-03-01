SUBDIRS := 1_scala_intro 2_futures 3_more_scala 4_akka 5_reflection 6_testing 7_mongoquery

all: $(SUBDIRS)

.PHONY: $(SUBDIRS)

$(SUBDIRS):
	make -C $@


clean:
	$(MAKE) -C 1_scala_intro clean
	$(MAKE) -C 2_futures clean
	$(MAKE) -C 3_more_scala clean
	$(MAKE) -C 4_akka clean
	$(MAKE) -C 5_reflection clean
	$(MAKE) -C 6_testing clean
	$(MAKE) -C 7_mongoquery clean
