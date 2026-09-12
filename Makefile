.DEFAULT_GOAL := build

MAIN_SRC  := $(shell find src/main/java -name '*.java')
TEST_SRC  := $(shell find src/test/java -name '*.java')
RESOURCES := src/main/resources

OUT_MAIN := out/main
OUT_TEST := out/test

# Dependency jars, resolved from the local Gradle/Maven caches used by the
# rest of this project. Override on the command line if yours live elsewhere,
# e.g. `make build LOG4J_API=/path/to/log4j-api.jar`.
LOG4J_API      ?= $(shell find $(HOME)/.gradle/caches/modules-2 -iname 'log4j-api-2.17.1.jar' 2>/dev/null | grep -v sources | head -1)
LOG4J_CORE     ?= $(shell find $(HOME)/.gradle/caches/modules-2 -iname 'log4j-core-2.17.1.jar' 2>/dev/null | grep -v sources | head -1)
JUNIT_JAR      ?= $(shell find $(HOME)/.m2 -iname 'junit-4.13.2.jar' 2>/dev/null | head -1)
HAMCREST_JAR   ?= $(shell find $(HOME)/.m2 -iname 'hamcrest-core-1.3.jar' 2>/dev/null | head -1)

MAIN_CP := $(LOG4J_API):$(LOG4J_CORE)
TEST_CP := $(OUT_MAIN):$(OUT_TEST):$(MAIN_CP):$(JUNIT_JAR):$(HAMCREST_JAR)

# Runner/Validator log at WARN by default. Override with e.g. `make run LOG_LEVEL=debug`.
LOG_LEVEL ?= warn
JAVA_OPTS := -Dblox.log.level=$(LOG_LEVEL)

OUT_DIR ?= generated
MOVES   ?= $(OUT_DIR)/solution.txt

.PHONY: build test run validate clean check-deps

check-deps:
	@if [ -z "$(LOG4J_API)" ] || [ -z "$(LOG4J_CORE)" ]; then \
		echo "Could not find log4j-api/log4j-core 2.17.1 jars under ~/.gradle/caches." >&2; \
		echo "Pass them explicitly: make build LOG4J_API=... LOG4J_CORE=..." >&2; \
		exit 1; \
	fi
	@if [ -z "$(JUNIT_JAR)" ] || [ -z "$(HAMCREST_JAR)" ]; then \
		echo "Could not find junit-4.13.2/hamcrest-core-1.3 jars under ~/.m2." >&2; \
		echo "Pass them explicitly: make test JUNIT_JAR=... HAMCREST_JAR=..." >&2; \
		exit 1; \
	fi

build: check-deps
	@mkdir -p $(OUT_MAIN) $(OUT_TEST)
	javac -d $(OUT_MAIN) -cp "$(MAIN_CP)" $(MAIN_SRC)
	cp -r $(RESOURCES)/* $(OUT_MAIN)/
	javac -d $(OUT_TEST) -cp "$(TEST_CP)" $(TEST_SRC)

test: build
	java -cp "$(TEST_CP)" org.junit.runner.JUnitCore blox.AStarSearchTest blox.BlockTest blox.ScapeTest blox.SearchNodeTest

# Solves all 33 levels and writes $(OUT_DIR)/solution.txt (gitignored).
run: build
	java $(JAVA_OPTS) -cp "$(TEST_CP)" blox.Runner $(OUT_DIR)

# Validates a moves file against the real rules. Defaults to the Runner's output.
# Usage: make validate MOVES=path/to/moves.txt
validate: build
	java $(JAVA_OPTS) -cp "$(TEST_CP)" blox.Validator $(MOVES)

clean:
	rm -rf $(OUT_MAIN) $(OUT_TEST) $(OUT_DIR)
