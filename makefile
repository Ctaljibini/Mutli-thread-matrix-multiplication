JAVAC=javac
JAVA=java
SRC_DIR=src
OUT_DIR=out
MAIN_CLASS=Main
JFLAGS=-d $(OUT_DIR)

.PHONY: all clean run

all: $(OUT_DIR) $(OUT_DIR)/$(MAIN_CLASS).class

$(OUT_DIR):
	mkdir -p $(OUT_DIR)

$(OUT_DIR)/$(MAIN_CLASS).class: $(SRC_DIR)/$(MAIN_CLASS).java
	$(JAVAC) $(JFLAGS) $<

run: all
	$(JAVA) -cp $(OUT_DIR) $(MAIN_CLASS)

clean:
	rm -rf $(OUT_DIR)
