/** 
 *  @file    perlinNoiseMW.cpp
 *  @author  Max Wright
 *  @date    12/23/2017  
 *  @version 1.0 
 *  
 *  @brief Two methods of reversing c style strings.
 *
 *  @section DESCRIPTION
 *  
 *  This file defines two distinct methods of reversing c style strings.
 *
 * © 2017 Max Wright. All rights reserved. 
 * Free for use. Use at your own risk, Max Wright will not be liable for any
 * damages caused by this code.
 */
/******************************************************************************
   Function: string_reverse1

Description: This function will reverse the values in a string from head to 
             tail. The tail is what pointer is brought in. This function will
             manipulate the values of the string that is connected to the char
             that is brought in.

     Inputs: string - A pointer to the start of the reversing process.

    Outputs: N/A
******************************************************************************/
void string_reverse1(char *string) 
{ 
    /*
     * This char pointer will eventually point to the character before the
     * null terminator.
     */
    char * top = string;
    /*
     * This integer will count how many characters are in the array omitting
     * the null terminator.
     */ 
    int count = 0;
    while(*top != '\0') {
        ++count;
        ++top;
    }
    // Adjust so that top does not point to the null terminator of the string.
    --top; 
    /*
     * For half of the amount of important characters in the string, omitting
     * the middle character if the amount of characters in the string not 
     * including the null terminator are odd. 
     */     
    for(int i = 0; i < count / 2; ++i) {
        /* 
         * *string now represents the bottom of the important content of the 
         * string, and *top represents the top of the important content of the 
         * string.
         */
        /*
         * Perform XOR operations to avoid making copies of the characters.
         * This switches the values present in *string and *top.
         */
        *string = *string ^ *top;
        *top = *string ^ *top;
        *string = *string ^ *top;
        // Move the bottom pointer up one.
        ++string;
        // Move the top pointer down one.
        --top;
    }
}
/******************************************************************************
   Function: string_reverse2

Description: This function will reverse the values in a string from head to 
             tail into a new string.

     Inputs: string - A pointer to the start of the reversing process.

    Outputs: A pointer to the beggining of a new string that is the reverse of
             the string brought in.
******************************************************************************/ 
char * string_reverse2(const char *string) 
{ 
    /*
     * This integer will count how many characters are in the array omitting
     * the null terminator.
     */
    int count = 0;
    while(*string != '\0') {
        ++count;
        ++string;
    }
    /*
     * Adjust so that *string does not point to the null terminator of the 
     * string. *string is now at the last important value of the string.
     */
    --string;
    // Create a new string in C style of the same size as what was brought in.
    char * toReturn = new char[count + 1];
    for(int i = 0; i < count; ++i) {
        // Save the current value at string to the string to be returned.
        toReturn[i] = *string;
        // Move string down to the next significant value.
        --string;
    }
    // Add null terminator to the string to be returned.
    toReturn[count] = '\0';
    return toReturn;
} 