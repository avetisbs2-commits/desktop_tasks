# User Registration Desktop App - Student Tasks

This module is a beginner Java Swing desktop application for practicing Java basics and `ArrayList<User>`.

The app already contains the user interface, the `User` class, and button connections. Your task is to complete the missing business logic in `src/UserRegistrationFrame.java`.

## Run This Module

From this folder:

```bash
javac src/*.java
java -cp src Main
```

## Rules

Use only one collection for registered users:

```java
private final ArrayList<User> users = new ArrayList<>();
```

Do not use `HashMap`, `HashSet`, `LinkedList`, `TreeMap`, Stream API, databases, file storage, or Spring.

## Tasks

1. Complete `registerUser()`.
2. Complete `emailExists(String email)`.
3. Complete `login()`.
4. Complete `findUserByEmail(String email)`.
5. Complete `getUserInfo(User user)`.
6. Complete `showAllUsers()`.
7. Complete `findOldestUser()`.
8. Complete `calculateAverageAge()`.
9. Complete `removeUserByEmail(String email)`.

## Practice Topics

- `ArrayList<User>`
- `add()`
- `get()`
- `size()`
- `remove()`
- loops
- `if` statements
- string comparison
- searching
- basic calculations
