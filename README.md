Module 1:
Reflection 1 Clean code was implemented with clear naming conventions that explained the intention of the code and made comments unnecessary (such as productId and productName).
Single responsibility was implemented through one method per MVC layer action.  
Encapsulation was implemented with private data storage within the repository, as well as through the use of secret form fields and internal data mapping between the web and the controller.
Security was increased by hiding the secret Id data field from the user so they cannot delete or change it.
I fixed the initial bug of the application by allowing unique id generation within the creation method and not in the initial build so products are correctly represented at all times.

Reflection 2 The use of unit tests gave me a huge confidence boost in the stability of my application.
It helps I can ascertain logically if something should fail rather than by executing my program and find out it failed when I run it.
Having said that, although high coverage is a good indicator of a well run program,  it is not infallible as there are no assurances that all edge cases are covered nor that the program was not technically interpreted incorrectly.
To improve the quality of my code I would like to implement a central base test class that executes the setup code and extract the UI away from the testing logic.

Module 2:
Reflection 1
I fixed several SonarCloud-reported accessibility/code-smell issues, mainly in the Thymeleaf HTML templates: adding a lang (and/or xml:lang) attribute to the <html> element, ensuring form inputs have proper accessible labels (e.g., using <label for="..."> with matching id on inputs).
My strategy was to prioritize issues that were clearly actionable, low-risk, and aligned with best practices (especially accessibility), apply the smallest change that satisfies the rule without altering application behavior, then rerun the CI + SonarCloud analysis to confirm the issue disappears and does not introduce new findings.

Reflection 2
Yes, the current setup meets the definition of Continuous Integration because every push and pull request automatically triggers workflows that build the project, run all test suites, and perform code quality analysis using SonarCloud. This ensures that new changes are continuously integrated and validated, and any errors or quality issues are detected early.
It also meets the definition of Continuous Deployment because once changes are merged into the deployment branch, the application is automatically deployed to the PaaS (Render) without manual intervention.
This means the latest stable version of the application is consistently delivered to a running environment, fulfilling the core principles of CI/CD.