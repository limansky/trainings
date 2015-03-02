SUBDIRS := 1_scala_intro 2_futures 3_more_scala 4_akka 5_reflection 6_testing 7_mongoquery

all: $(SUBDIRS)

.PHONY: $(SUBDIRS)

$(SUBDIRS):
	$(MAKE) -C $@


clean:
	for dir in $(SUBDIRS); do \
		$(MAKE) -C $$dir clean; \
	done
