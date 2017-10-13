/******************************************************************************
filename   tabReplacer.cpp
author      Max Wright
UW email    maxdw@uw.edu
completion  10/12/2017

Brief Description:
  This program takes in entered files from the command line and rewrites each
  file with four spaces in place of a tab character.
******************************************************************************/

#include <iostream>
#include <fstream>
#include <ostream>
#include <stdio.h>

using namespace std;

void correctTabs(char* argv[]) {
        /* This interger exists to keep track of which file entered from the 
           command line this function will be editing first. */
    int fileIterator = 1;  
    
        // While there still is a file to be read.
    while(argv[fileIterator] != NULL) {
            //Attempt to read the file.
        ifstream read(argv[fileIterator]);
            //If the file exists...
        if(read.is_open()) {
            ofstream write("ATemporaryFileForTabReplacer.maxFile");
            string temp = "";
            /* Define the string in this scope to avoid having the output file be 
               one line greater than original file. */
            string out = "";
            while(std::getline(read, temp)) {
                    /* Holds the position of which character is trying to be read 
                       in the last string brought in from the file being read. */
                int stringPos = 0; 
                while(temp[stringPos] != '\0') {
                    if(temp[stringPos] == '\t') {
                        out += "    ";
                    } else {
                        out += temp[stringPos];
                    }
                    stringPos++;
                    
                }
                write << out;
                /* Make is so the next line starts on a new line if another 
                   line needs created. */
                out = "\n";
            }
            read.close();
            write.close();
            
            //Overwrite file with good code with correct file name.
            ifstream readII("ATemporaryFileForTabReplacer.maxFile");
            ofstream writeII(argv[fileIterator]);
            temp = "";
            /* Define the string in this scope to avoid having the output file be 
               one line greater than original file. */
            out = "";
            while(std::getline(readII, temp)) {
                writeII << out << temp;
                /* Make is so the next line starts on a new line if another 
                   line needs created. */
                out = "\n";
            }
            readII.close();
            writeII.close();
            remove("ATemporaryFileForTabReplacer.maxFile");
            
        } else {
                /* If the file does not exists, output said information to the
                   command line */
            cout << "No such file: " << argv[fileIterator] << endl;
        }
        fileIterator++;
    }
}

int main(int argc, char* argv[]) {
    /* Check to make sure that the number of items past into the command line 
        is correct. */
    if(argc == 1) {
        //If not, send correcting message.
        cout << "Usage: " << argv[0] << " File" << endl;
        cout << "Usage: " << argv[0] << " File File etc." << endl;
        return 1;
    }
    
    if(argc > 1) {
       correctTabs(argv); 
    }
    
    return 0;
}