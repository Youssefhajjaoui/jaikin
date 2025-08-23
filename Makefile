clean:
	@rm -rf build

run:
	@javac app/*.java Chaikin.java -d build
	@java -cp build Chaikin