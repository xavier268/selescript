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
class demoRunner {

    public void run(String fileName) throws IOException {
        try {
            System.out.println();
            System.out.println("================================================");
            System.out.println("=   Starting " + fileName);
            System.out.println("================================================");
            CommandLine.main("-s", fileName);
        } finally {
            System.out.println("================================================");
            System.out.println("=   Finished " + fileName);
            System.out.println("================================================");
            System.out.println();
        }
    }
    
}
