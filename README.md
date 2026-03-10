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

Module 03:
1. SOLID Principles Applied
In this module, I refactored the Car feature to follow SOLID principles. The principles applied are:
a. Single Responsibility Principle (SRP)
Each class now has one clear responsibility:
- CarController handles HTTP requests related to Car.
- CarService / CarServiceImpl handles business logic.
- CarRepository handles data persistence abstraction.
- InMemoryCarRepository handles the concrete storage implementation.
Each layer is responsible only for its own concern, making the system easier to understand and maintain.
b. Dependency Inversion Principle (DIP)
High-level modules no longer depend on low-level implementations.
CarController depends on CarService (interface), not CarServiceImpl.
CarServiceImpl depends on CarRepository (interface), not InMemoryCarRepository.
This ensures that high-level logic depends on abstractions, not concrete implementations. If the repository implementation changes, the controller and service layers remain unchanged. 
c. Open-Closed Principle (OCP)
The system is now open for extension but closed for modification.
Because CarRepository is an interface, new implementations can be added (e.g., DatabaseCarRepository, JpaCarRepository) without modifying existing service or controller code.
New functionality is introduced by extending behavior rather than modifying stable code.

2. Advantages of Applying SOLID
a. Easier Testing
Since the controller and service depend on interfaces, dependencies can be mocked during testing. This improves testability and allows isolated unit testing of each layer.
b. Better Maintainability
Each class has a focused responsibility. If a bug appears in data handling, we inspect the repository. If the issue is related to request handling, we inspect the controller.
This separation reduces debugging complexity and improves maintainability. 
c. Improved Flexibility
If the project later requires database persistence instead of in-memory storage, a new repository implementation can be created without modifying the service or controller layers.
This makes the system adaptable to future requirements.
d. Cleaner Architecture
The dependency structure becomes:
Controller → Service (interface) → Repository (interface) → Implementation
This layered architecture improves clarity and scalability.

3. Disadvantages If SOLID Is Not Applied
If SOLID principles were not applied:
a. Tight Coupling
If the controller directly depends on concrete service implementations, and services depend on concrete repositories, changes in one layer would affect others. This increases maintenance cost and risk of regression bugs.
b. Difficult Extension
Without abstraction at the repository level, changing storage implementation would require modifying existing service code. This violates the Open-Closed Principle.
c. Harder Testing
Without interfaces, dependencies cannot be easily replaced or mocked. This makes unit testing more difficult and less reliable.
d. Reduced Code Clarity
If responsibilities are mixed between layers (e.g., controller containing business logic), the system becomes harder to read, scale, and maintain.

Module 4 Reflection

 1. Reflection on the usefulness of the TDD workflow
The Test-Driven Development (TDD) workflow used in this exercise was helpful for structuring the development process. By writing the tests first (RED), then implementing the minimal code to pass the tests (GREEN), and finally improving the code structure (REFACTOR), it became easier to focus on the requirements step by step. The tests clearly defined what behavior the code should have before the implementation was written. This also helped detect errors early because the tests immediately showed whether the implementation worked as expected.

However, one difficulty when following TDD in this exercise was that some tests did not compile at first because the related classes had not been created yet. While this is part of the TDD process, it can feel confusing at the beginning. In future implementations, I would try to write smaller and more focused tests, and ensure that the skeleton of the classes exists first so the compilation errors are easier to manage. Overall, the TDD flow helped improve the reliability of the implementation and made the development process more systematic.

 2. Reflection on the F.I.R.S.T. principles of the tests

The unit tests created in this tutorial generally follow the F.I.R.S.T. principles.
- Fast: The tests run quickly because they operate on in-memory data structures instead of external systems like databases.
- Independent: Each test sets up its own data using the `@BeforeEach` method, so the tests do not depend on the results of other tests.
- Repeatable: The tests can be executed multiple times and produce the same results because they do not rely on external state.
- Self-validating: Each test uses assertions such as `assertEquals`, `assertThrows`, and `assertNull` to automatically verify correctness.
- Timely: The tests were written before implementing the actual functionality, which follows the core idea of TDD.

One improvement that could be made is making the tests even more focused on single behaviors, so that each test verifies only one specific condition. This would make failures easier to diagnose. In future test development, I would also try to improve naming clarity and ensure that each test case represents a clear scenario.

Bonus 2 Reflection

I reviewed my teammate's implementation of the payment feature. The functionality works correctly, but the addPayment method contained complex conditional logic that handled multiple responsibilities at once, including payment creation, validation, and status assignment.

The main code smell identified was complex conditional logic that reduced readability and maintainability. I refactored the code by extracting the status decision logic into a helper method and separating COD validation into its own method. This keeps the behavior the same while improving code structure and readability.

The refactoring makes the service easier to extend if new payment methods are added in the future.