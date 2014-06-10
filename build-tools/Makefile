OBJS = android.o eval_expression.o utils.o java_ostream.o

GNU_APL_SRC = $(HOME)/src/apl-android

UNAME = $(shell uname)
ifeq ($(UNAME),Darwin)
JAVA_HOME = /Library/Java/JavaVirtualMachines/jdk1.7.0_60.jdk/Contents/Home
JAVA_INCLUDES = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/darwin
else
JAVA_HOME = /usr/local/jdk1.7.0_45
JAVA_INCLUDES = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux
endif

CROSSBUILD = $(shell sh -c "cat $(GNU_APL_SRC)/config.status | grep androideabi >/dev/null && echo androideabi")
ifeq ($(CROSSBUILD),androideabi)
CC=arm-linux-androideabi-gcc
CXX=arm-linux-androideabi-g++
LIBRARY_EXT = so
SHARED_FLAGS = -shared
APL_LIBRARIES = -ldl -lm -lgomp -llog
else
ifeq ($(UNAME),Darwin)
LIBRARY_EXT = dylib
SHARED_FLAGS = -dynamiclib -Wl,-undefined,dynamic_lookup
APL_LIBRARIES = -lreadline -ldl -lncurses -lpthread -lm -lblas -llapack -lcurses
else
LIBRARY_EXT = so
SHARED_FLAGS = -shared
APL_LIBRARIES = -lreadline -ldl -lncurses -lpthread -lrt -lm -lgomp -lblas -llapack -lcurses -lnsl
endif
endif

LIBNAME = libapl.$(LIBRARY_EXT)

CXXFLAGS = -g -Wall -fPIC $(JAVA_INCLUDES) -I$(GNU_APL_SRC)/src

all:	$(LIBNAME)

$(LIBNAME): $(OBJS)
	$(CXX) -g -fPIC -rdynamic $(SHARED_FLAGS) -o $(LIBNAME) $(GNU_APL_SRC)/src/apl-*.o $(OBJS) $(APL_LIBRARIES)

copylib: $(LIBNAME)
	cp $(LIBNAME) ../client/libs/armeabi/$(LIBNAME)

clean:
	rm -f $(OBJS) $(LIBNAME)
