Reflection 1 Clean code was implemented with clear naming conventions that explained the intention of the code and made comments unnecessary (such as productId and productName).
Single responsibility was implemented through one method per MVC layer action.  
Encapsulation was implemented with private data storage within the repository, as well as through the use of secret form fields and internal data mapping between the web and the controller.
Security was increased by hiding the secret Id data field from the user so they cannot delete or change it.
I fixed the initial bug of the application by allowing unique id generation within the creation method and not in the initial build so products are correctly represented at all times.

Reflection 2 The use of unit tests gave me a huge confidence boost in the stability of my application.
It helps I can ascertain logically if something should fail rather than by executing my program and find out it failed when I run it.
Having said that, although high coverage is a good indicator of a well run program,  it is not infallible as there are no assurances that all edge cases are covered nor that the program was not technically interpreted incorrectly.
To improve the quality of my code I would like to implement a central base test class that executes the setup code and extract the UI away from the testing logic.