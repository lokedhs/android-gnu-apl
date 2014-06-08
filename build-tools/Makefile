OBJS = android.o eval_expression.o utils.o java_ostream.o

GNU_APL_SRC = $(HOME)/src/apl-android

UNAME = $(shell uname)
ifeq ($(UNAME),Darwin)
JAVA_HOME = /Library/Java/JavaVirtualMachines/jdk1.7.0_60.jdk/Contents/Home
JAVA_INCLUDES = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/darwin
SHARED_FLAGS = -dynamiclib -Wl,-undefined,dynamic_lookup
LIBRARY_EXT = dylib
APL_LIBRARIES = -lreadline -ldl -lncurses -lpthread -lm -lblas -llapack -lcurses
else
JAVA_HOME = /usr/local/jdk1.7.0_45
JAVA_INCLUDES = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux
SHARED_FLAGS = -shared
LIBRARY_EXT = so
APL_LIBRARIES = -lreadline -ldl -lncurses -lpthread -lrt -lm -lgomp -lblas -llapack -lcurses -lnsl
endif

LIBNAME = libapl.$(LIBRARY_EXT)

CXXFLAGS = -g -Wall -fPIC $(JAVA_INCLUDES) -I$(GNU_APL_SRC)/src

all: $(OBJS)
	$(CXX) -g -fPIC -rdynamic $(SHARED_FLAGS) -o $(LIBNAME) $(GNU_APL_SRC)/src/apl-*.o $(OBJS) $(APL_LIBRARIES)

clean:
	rm -f $(OBJS) $(LIBNAME)
