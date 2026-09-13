.DEFAULT_GOAL := build

GRADLE := ./gradlew

# Runner/Validator log at WARN by default (suppresses the solver's own
# internal chatter); their own per-level progress/duration always logs at
# INFO. Override with e.g. `make run LOG_LEVEL=debug`.
LOG_LEVEL ?= warn

OUT_DIR ?= generated
MOVES   ?= $(OUT_DIR)/solution.txt

.PHONY: build test run validate clean

build:
	$(GRADLE) build -x test

test:
	$(GRADLE) test

# Solves all 33 levels and writes $(OUT_DIR)/solution.txt (gitignored).
run:
	$(GRADLE) run -PoutputDir=$(OUT_DIR) -PlogLevel=$(LOG_LEVEL)

# Validates a moves file against the real rules. Defaults to the Runner's output.
# Usage: make validate MOVES=path/to/moves.txt
validate:
	$(GRADLE) validate -Pmoves=$(MOVES) -PlogLevel=$(LOG_LEVEL)

clean:
	$(GRADLE) clean
	rm -rf $(OUT_DIR)
