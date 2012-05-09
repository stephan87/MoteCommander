all: MCWindow.class

MCWindow.class: $(wildcard *.java) SerialMsg.java TableMsg.java SensorMsg.java
	javac -target 1.5 -source 1.5 *.java

SerialMsg.java:
	mig java -target=null $(CFLAGS) -java-classname=SerialMsg TestSerial.h CommandMsg -o $@
TableMsg.java:
	mig java -target=null $(CFLAGS) -java-classname=TableMsg TestSerial.h TableMsg -o $@
SensorMsg.java:
	mig java -target=null $(CFLAGS) -java-classname=SensorMsg TestSerial.h SensorMsg -o $@

clean:
	rm -f SerialMsg.java
	rm -f TableMsg.java
	rm -f SensorMsg.java
	rm -f *.class
