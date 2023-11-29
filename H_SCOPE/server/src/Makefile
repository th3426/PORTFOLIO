TARGET = server

CC = gcc
LD = gcc
CFLAGS = -c -g

INC = -I../include
LIB = -L../lib -lEdgeCrypto -lHcrypto
DEFS = -D_LINUX
LD_CFLAGS = -Wl,-rpath,'../lib'

SRCS = hSolution_Server.c test_code.c
OBJS = hSolution_Server.o test_code.o
.c.o : 
	$(CC) $(CFLAGS) $(INC) $(DEFS) $<

all : $(TARGET)
$(TARGET):	$(OBJS)
	$(CC) $(LD_CFLAGS) -o $@ $(OBJS) $(LIB)

test:
	@./$(TARGET) 

clean:
	rm -f $(TARGET) $(OBJS)
