# Project Management

## Description

This project helps in managing projects efficiently and effectively. It aims to provide tools and guidelines to facilitate the management lifecycle of both large and small projects.

## Features

- Task organization
- Collaboration tools
- Resource allocation

## Logic

    ┌──────────────────────┬───────┬─────────────────────────────────┐
    │ File                 │ Lines │ Purpose                         │
    ├──────────────────────┼───────┼─────────────────────────────────┤
    │ IUser.java           │ 20    │ User interface contract         │
    │ User.java            │ 208   │ Base user class with validation │
    │ Member.java          │ 68    │ Member role implementation      │
    │ Owner.java           │ 116   │ Owner role (full permissions)   │
    │ Project.java         │ 163   │ Project entity                  │
    │ Task.java            │ 189   │ Task entity                     │
    │ ProjectManager.java  │ 102   │ Project management              │
    │ userManager.java     │ 65    │ User management                 │
    │ DatabaseManager.java │ 546   │ Database operations(mysql)      │
    ├──────────────────────┼───────┼─────────────────────────────────┤
    │ Total                │ 1425  │ 9 files                         │
    └──────────────────────┴───────┴─────────────────────────────────┘

## UI

    ┌───────────────────────┬───────┬───────────────────────────────────────┐
    │ File                  │ Lines │ Purpose                               │
    ├───────────────────────┼───────┼───────────────────────────────────────┤
    │ AppFrame.java         │  50   │ Main application frame                │
    │ DashboardPanel.java   │  80   │ Dashboard container (uses components) │
    │ Sidebar.java          │  180  │ Navigation sidebar                    │
    │ DashboardContent.java │  80   │ Dashboard overview stats              │
    │ ProjectsContent.java  │  120  │ Projects management                   │
    │ TasksContent.java     │  160  │ Tasks management                      │
    │ ProfileContent.java   │  70   │ User profile view                     │
    │ SettingsContent.java  │  60   │ Settings panel                        │
    │ LoginPanel.java       │  130  │ Login form                            │
    │ RegisterDialog.java   │  180  │ Registration dialog                   │
    ├───────────────────────┼───────┼───────────────────────────────────────┤
    │ Total                 │ 1110  │ 10 files                              │
    └───────────────────────┴───────┴───────────────────────────────────────┘

## Getting Started

Follow the instructions below to set up the project on your local machine.

1. Clone the repository.
2. Install required dependencies.
3. Run the application.
4. Maybe you will learn something.

## MIT License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
