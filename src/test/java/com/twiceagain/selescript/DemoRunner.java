/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript;

import java.io.IOException;

/**
 *
 * @author xavier
 */
class DemoRunner {

    public void run(String fileName) throws IOException {
        try {
            System.out.println();
            System.out.println("================================================");
            System.out.println("=   Starting " + fileName);
            System.out.println("================================================");
            CommandLine.main("-s", fileName, "--col", "testcol", "--db", "testdb");
        } finally {
            System.out.println("================================================");
            System.out.println("=   Finished " + fileName);
            System.out.println("================================================");
            System.out.println();
        }
    }

    public void runDebug(String fileName) throws IOException {
        try {
            System.out.println();
            System.out.println("================================================");
            System.out.println("=   Starting " + fileName + " in debug mode");
            System.out.println("================================================");
            CommandLine.main("-s", fileName, "-d", "--col", "testcol", "--db", "testdb");
        } finally {
            System.out.println("================================================");
            System.out.println("=   Finished " + fileName + " in debug mode");
            System.out.println("================================================");
            System.out.println();
        }
    }

    public void runWithInput(String fileName, String inputFileName) throws IOException {
        try {
            System.out.println();
            System.out.println("================================================");
            System.out.println("=   Starting " + fileName + " with input " + inputFileName);
            System.out.println("================================================");
            CommandLine.main("-d", "-s", fileName, "-d", "--col", "testcol", "-i", inputFileName);
        } finally {
            System.out.println("================================================");
            System.out.println("=   Finished " + fileName + " with input " + inputFileName);
            System.out.println("================================================");
            System.out.println();
        }
    }

}
