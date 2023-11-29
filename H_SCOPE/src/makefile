LIBRARY = libH_app.so

EXE = EXE

CC = gcc

CFLAGS_for_LIBRARY = -fPIC -c -O2
LD_FLAGS_for_LIBRARY = -shared

CFLAGS_for_EXE = -O2 -c
LD_FLAGS_for_EXE = -Wl,-rpath,'../lib'

INC = -I../include

#LIB_for_LIBRARY  = -L../lib/ -lEdgeCrypto -lhexEncDec
LIB_for_EXE = -L../lib -lH_app

SRC_for_LIBRARY = hSolution.c
OBJ_for_LIBRARY = hSolution.o

SRC_for_EXE = sample.c
OBJ_for_EXE = sample.o

all : $(LIBRARY) $(EXE)

####################################################################

$(OBJ_for_LIBRARY) : $(SRC_for_LIBRARY)
	@echo "┼────────────────────────────────────────┼"
	@echo "| make obj for lib                       |"
	$(CC) $(CFLAGS_for_LIBRARY) $(INC) $(SRC_for_LIBRARY)
	@echo "| success                                |"

$(LIBRARY) : $(OBJ_for_LIBRARY)
	@echo "| make lib                               |"
	$(CC) $(LD_FLAGS_for_LIBRARY) -o $(LIBRARY) $(OBJ_for_LIBRARY) 
	@echo "| success                                |"
	@mv $(LIBRARY) ../lib/
	@echo "┼────────────────────────────────────────┼"

####################################################################

$(OBJ_for_EXE) : $(SRC_for_EXE)
	@echo "┼────────────────────────────────────────┼"
	@echo "| make obj for exe                       |"
	$(CC) $(CFLAGS_for_EXE) $(SRC_for_EXE) $(INC)
	@echo "| success                                |"

$(EXE): $(OBJ_for_EXE)
	@echo "| make exe                               |"
	$(CC) $(LD_FLAGS_for_EXE) -o $(EXE) $(OBJ_for_EXE) $(LIB_for_EXE)
	@echo "| success                                |"
	@echo "┼────────────────────────────────────────┼"

####################################################################

test:
	@./$(EXE) 

install:
	adb install -r ../target_apk/re_2048.apk

uninstall:
	adb uninstall com.tpcstld.twozerogame

LD_LIBRARY_PATH:
	@echo "┼────────────────────────────────────────┼"
	@echo "| edit LD_LIBRARY_PATH                    |"
	export LD_LIBRARY_PATH=../lib:$$LD_LIBRARY_PATH
	@echo "┼────────────────────────────────────────┼"

rebuild: clean all

clean:
	@echo "┼────────────────────────────────────────┼"
	@echo "| start clean project                    |"
	@rm -f ../lib/$(LIBRARY) $(OBJ_for_LIBRARY) $(EXE) $(OBJ_for_EXE)
	@rm -rf app_data.txt ../target_apk/de_2048 ../target_apk/re_2048*
	@echo "| success                                |"
	@echo "┼────────────────────────────────────────┼"
