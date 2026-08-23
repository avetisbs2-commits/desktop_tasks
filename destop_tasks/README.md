# Desktop Tasks

This project contains separate Java Swing desktop apps.

Each app is an independent IntelliJ IDEA module and can be started separately.

## Project Structure

```text
destop_tasks/
├── README.md
├── user-registration/
│   ├── README.md
│   ├── user-registration.iml
│   └── src/
├── food-order-management/
│   ├── README.md
│   ├── food-order-management.iml
│   └── src/
└── category-manager/
    ├── README.md
    ├── category-manager.iml
    └── src/
```

## Run/Debug Configurations

You can also create separate run configurations.

Open:

```text
Run -> Edit Configurations...
```

Click `+`, then choose `Application`.

## Configuration 1: User Registration Desktop App

Use these values:

- Name: `User Registration Desktop App`
- Main class: `Main`
- Module: `user-registration`
- JRE: project default JDK
- Program arguments: leave empty
- VM options: leave empty
- Working directory:

```text
/Users/tigranho/Projects/test/destop_tasks/user-registration
```

Build and run settings:

- Before launch: `Build`
- Activate tool window: checked

## Configuration 2: Food Order Management Desktop App

Use these values:

- Name: `Food Order Management Desktop App`
- Main class: `Main`
- Module: `food-order-management`
- JRE: project default JDK
- Program arguments: leave empty
- VM options: leave empty
- Working directory:

```text
/Users/tigranho/Projects/test/destop_tasks/food-order-management
```

Build and run settings:

- Before launch: `Build`
- Activate tool window: checked

## Configuration 3: Category Manager Desktop App

Use these values:

- Name: `Category Manager Desktop App`
- Main class: `Main`
- Module: `category-manager`
- JRE: project default JDK
- Program arguments: leave empty
- VM options: leave empty
- Working directory:

```text
/Users/tigranho/Projects/test/destop_tasks/category-manager
```

Build and run settings:

- Before launch: `Build`
- Activate tool window: checked

If `Build` is missing under `Before launch`:

1. Click `+` in the `Before launch` section.
2. Choose `Build`.
3. Click `OK`.

## Start The Apps From IntelliJ

After creating the configurations:

1. Open the Run/Debug configuration dropdown near the top-right of IntelliJ IDEA.
2. Select one app:

```text
User Registration Desktop App
Food Order Management Desktop App
Category Manager Desktop App
```

3. Click the green `Run` button.

Use `Debug` instead of `Run` if you want to practice breakpoints.

## Notes

- Each module has its own `README.md` with student tasks.
- Start each desktop app from its own `Main.java`.
- The apps are separate and do not share code.
