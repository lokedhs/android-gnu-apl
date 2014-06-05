OBJS = android.o

GNU_APL_SRC = $(HOME)/src/apl-android
JAVA_HOME = /usr/local/jdk1.7.0_45

CXXFLAGS = -g -Wall -fPIC -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux -I$(GNU_APL_SRC)/src

all: $(OBJS)
	$(CXX) -g -fPIC -rdynamic -shared -o libapl.so $(GNU_APL_SRC)/src/apl-*.o $(OBJS) -lreadline -ldl -lncurses -lpthread -lrt -lm -lgomp -lblas -llapack -lcurses -lnsl
