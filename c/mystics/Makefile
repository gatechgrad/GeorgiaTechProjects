COMPILER = gcc
SOURCE_FILES = ms.c
OBJECT_FILES = ms.o
OUTPUT_FILE = ms

$(OUTPUT_FILE): $(OBJECT_FILES)
	$(COMPILER) $(OBJECT_FILES) -o $(OUTPUT_FILE) -lalleg

$(OBJECT_FILES): $(SOURCE_FILES)
	$(COMPILER) -c $(SOURCE_FILES) -lalleg

