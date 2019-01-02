compile: bin
	find src | grep .java > sources.txt
	javac -d bin -cp biuoop-1.4.jar @sources.txt
	
jar:
	jar cfm ass6game.jar manifest.mf -C bin . -C resources .
	
run:
	java -cp biuoop-1.4.jar:bin:resources Ass6Game

check:
	java -jar checkstyle-5.7-all.jar -c biuoop.xml src/*.java

bin:
	mkdir bin