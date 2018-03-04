How to run:
Run the class 'Main.java'. Provide the name of the dictionary input file, and phone numbers input file as argument. 
Eg. -  Main.java F:/Dictionary.txt F:/PhoneNumbers.txt
Alternatively, you can provide the name of the input files inside the main method of Main.java and then run it.

Assumptions:
1. Same name can be used more than once. 
Eg. if dictionary has entry 'Pa', then search for '8585' will return 'Pa Pa' 
2. Input will be read from files. File locations will be provided to the application.

Design:
I have tried to keep different functions separate from each other. There is a NumberEncodingService that the client 
will call. The Service talks to a Controller. The Controller will create the dictionary, perform search over it, and
forward the output for display.
I have kept the Dictionary object separate from the Search Strategy. This allows searching over the same dictionary 
using different strategies, should such a need arise later.
I have used Factories to create Dictionary and Search Strategies, to decouple Controller from their actual implementations.
Currently, the Main class creates the objects we need. In case we decide to use an injection Framework, such as Spring, these 
dependencies can be separately injected. That will provide looser coupling.

Test Coverage is not 100%. I have tried to cover the major components - Dictionary, and SearchStrategy.
Use of IO limited the writing of Test Cases.


Packages:
I have created different packages, that represent the different modules of the system. Here are the packages:
main - This contains the Main class that runs the application. 
	client – This represents the whole application as a service. It is intended as the entity facing clients 
	of this application. It is not of much use right now. But if application expands later, to become available in other
	formats(say as a REST service), then it will be useful.
controller – This controls the flow. It creates the relevant data structure, matches phone numbers against the 
	data structure, and sends the output for further handling.
input - This contains objects to represent and handle the Dictionary. 
search - This has the search strategy objects, to conduct the search on the Dictionary. 
output - This contains the Output handler.

