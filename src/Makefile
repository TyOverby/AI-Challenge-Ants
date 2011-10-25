JAVAC=javac
JAVAC_ARGS=

.PHONY: all clean

all: $(CLASSES)
	echo $(SOURCES)
	$(JAVAC) $(JAVAC_ARGS) `find ./ -name "*.java"`
	jar cvfm MyBot.jar Manifest.txt `find ./ -name "*.class"`

clean:
	-rm -Rf *.class
	-rm -Rf *.jar
