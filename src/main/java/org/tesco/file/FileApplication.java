package org.tesco.file;

import org.apache.camel.main.Main;

public class FileApplication {

    public static void main(String[] args) throws Exception {
	Main main = new Main();
	main.addRouteBuilder(new FileRoute());
	main.run();
    }

}
