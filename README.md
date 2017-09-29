# Introduction
The application is written in Java for Data Structures and Algorithms module in year 2 of Software Development(2017) course at Galway Mayo Institute of Technology, Galway Campus.


# How to run


The cipher can be run with the following command:

```
java –cp ./porta.jar ie.gmit.sw.Runner
```



# How to use:


Option 1 to add a keyword. 
* This keyword will be stripped to a-z and converted into upper case letter.

Option 2 to add the destination file name. 
* This is where the file is going to be saved.





	

Option 3 encrypt URL, any given URL can be encrypted. 
* If the URL is not valid or not available, the application will warn the user. (sample: www.koronakiraly.hu/wap.txt)
	
Option 4 encrypt file; any file can be encrypted. 
* If the file is not available or readable, the application will warn the user.
	
Option 5 encrypt text; the application will print out the encrypted text on the screen.

## Description

Three different things can be encrypted:
	

If the keyword and the destination file was not set yet, the application will warn the user about it.



Encryption process

* The application uses only one loop for the whole encryption therefore the running time is O(n). This loop is needed to go through every character. 

* To encrypt the character the algorithm calculates the ASCII code from the keyword's given character and from the actual character the loop is on.



The encrypter


If text is encrypted:

* The encrypter will go through the characters one by one. Convert it to upper case and strip if it is not a letter.

* If an upper-case letter is found, the above-mentioned algorithm is used.



If it is a URL or a file:

	The encrypter opens the file/URL buffers in 1048576 bytes at a time and loop the content byte by byte.
        If the byte is a lover case or uppercase character or a space, it converts the byte.
 	Once the encryoter encrypted a byte, it writes into a file straight away. (There is a buffered writer with the size of 1048576)






