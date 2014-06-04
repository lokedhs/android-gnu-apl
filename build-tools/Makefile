OBJS = android.o

GNU_APL_SRC = /home/emartenson/src/apl-android
JAVA_HOME = /usr/local/jdk1.7.0_45

CXXFLAGS = -g -Wall -fPIC -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux

all: $(OBJS)
	$(CC) -g -fPIC -o libapl.so $(GNU_APL_SRC)/src/apl-*.o $(OBJS)
